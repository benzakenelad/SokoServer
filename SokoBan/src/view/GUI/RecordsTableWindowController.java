package view.GUI;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import ORM.Record;
import ORM.User;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class RecordsTableWindowController implements Initializable {

	// Data Members
	@FXML
	private TableView<Record> recordsTable;
	@FXML
	private TextField nameFilterField;
	@FXML
	private TextField levelFilterField;

	private ObservableList<Record> data; // Record List Data
	private SessionFactory factory; // Session factory
	private Stage recordsTableStage; // Current stage holder

	// Initialization
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Data members Initialization
		data = FXCollections.observableArrayList();
		factory = new Configuration().configure().buildSessionFactory();
		recordsTable.setEditable(true);

		
		// Record table columns Initialization
		TableColumn<Record, String> column1 = new TableColumn<>("Level");
		column1.setCellValueFactory(new PropertyValueFactory<>("levelID"));
		column1.setMinWidth(100);
		column1.setMaxWidth(100);

		TableColumn<Record, String> column2 = new TableColumn<>("Full Name");
		column2.setCellValueFactory(new PropertyValueFactory<>("user"));
		column2.setMinWidth(150);
		column2.setMaxWidth(250);

		TableColumn<Record, String> column3 = new TableColumn<>("Steps");
		column3.setCellValueFactory(new PropertyValueFactory<>("steps"));
		column3.setMinWidth(75);
		column3.setMaxWidth(75);

		TableColumn<Record, String> column4 = new TableColumn<>("Finish Time");
		column4.setCellValueFactory(new PropertyValueFactory<>("time"));
		column4.setMinWidth(90);
		column4.setMaxWidth(90);

		// Listen for text changes in the name filter text field
		nameFilterField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				updateNameFilteredData(newValue);
			}
		});

		// Listen for text changes in the level filter text field
		levelFilterField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				updateLevelFilteredData(newValue);
			}
		});

		// Columns set up
		recordsTable.getColumns().addAll(column1, column2, column3, column4);

		// Data set up
		recordsTable.setItems(data);

		// Mouse click event handler
		recordsTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				Record record = recordsTable.getSelectionModel().getSelectedItem();
				if (record != null)
					displayUserRecord(record);
			}
		});

	}

	// Full Name filter table 
	public void updateNameFilteredData(String name) {
		Session session = factory.openSession();
		@SuppressWarnings("unchecked")
		Query<Record> query = session.createQuery("from Record");
		List<Record> list = query.list();
		int len = list.size();
		int i = 0;
		while (i < len) {
			if (list.get(i).getUser().toString().toLowerCase().startsWith(name.toLowerCase()) == false) {
				list.remove(i);
				len--;
			} else
				i++;
		}
		data.clear();
		data.addAll(list);
		session.close();
	}

	// Level ID filter table 
	public void updateLevelFilteredData(String levelID) {
		Session session = factory.openSession();
		
		@SuppressWarnings("unchecked")
		Query<Record> query = session.createQuery("from Record");
		List<Record> list = query.list();
		
		int len = list.size();
		int i = 0;

		while (i < len) {
			if (list.get(i).getLevelID().toLowerCase().startsWith(levelID.toLowerCase()) == false) 
			{
				list.remove(i);
				len--;
			} else
				i++;
		}
		// New data set up
		data.clear();
		data.addAll(list);
		session.close();
	}

	// new Record addition
	public void addArecord(String levelID, int steps, double time) {
		// 
		Platform.runLater(new Runnable() { 

			@Override
			public void run() {

				// Text Input Dialog creation
				TextInputDialog dialog = new TextInputDialog("User Name");
				dialog.setTitle("Registration");
				dialog.setHeaderText(null);
				dialog.setContentText("Please enter your User Name");

				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				String userName = null;
				if(result.isPresent())
					userName = result.get();
				else
					return;		
				
				Session session = factory.openSession();
				@SuppressWarnings("unchecked")
				Query<User> query = session.createQuery("from User");
				List<User> list = query.list();
				User user = null;

				for (User u : list)
					if (userName.compareTo(u.getUserName()) == 0) {
						user = u;
						break;
					}

				// in case userName is not exist in record list 
				if (user == null) {
					// Create the custom dialog.
					Dialog<Pair<String, String>> dialog2 = new Dialog<>();
					dialog2.setTitle("Sign Up");
					dialog2.setHeaderText("User Name is not exist, Sigh up please");

					// Set the button types.
					ButtonType loginButtonType = new ButtonType("Sign Up", ButtonData.OK_DONE);
					dialog2.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

					// Create the First Name and Last Name labels and fields.
					GridPane grid = new GridPane();
					grid.setHgap(10);
					grid.setVgap(10);
					grid.setPadding(new Insets(20, 150, 10, 10));

					TextField firstName = new TextField();
					firstName.setPromptText("First Name");
					TextField lastName = new TextField();
					lastName.setPromptText("Last Name");

					grid.add(new Label("First Name:"), 0, 0);
					grid.add(firstName, 1, 0);
					grid.add(new Label("Last Name:"), 0, 1);
					grid.add(lastName, 1, 1);

					dialog2.getDialogPane().setContent(grid);

					// Convert the result to a FirstName-LastName-pair when the
					// Sign up button is clicked.
					dialog2.setResultConverter(dialogButton -> {
						if (dialogButton == loginButtonType) {
							return new Pair<>(firstName.getText(), lastName.getText());
						}
						return null;
					});

					Optional<Pair<String, String>> result2 = dialog2.showAndWait();

					Pair<String, String> pair = result2.get();

					// New User initialization
					user = new User(userName);
					user.setFirstName(pair.getKey());
					user.setLastName(pair.getValue());
					
					// Saving the new user in record list
					session.save(user);
				}

				Record newRecord = new Record(user, levelID, steps, time);

				Transaction tx = session.beginTransaction();

				// Save the new record in record list
				session.save(newRecord);
				tx.commit();

				session.close();

				// Confirmation message
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText(null);
				alert.setContentText("Your record is successfully saved!");
				alert.showAndWait();
			}
		});

	}

	// Load the updated record list
	public void displayRecordsTable() {
		data.clear();
		recordsTableStage.show();
		Session session = factory.openSession();

		@SuppressWarnings("unchecked")
		Query<Record> query = session.createQuery("from Record");
		List<Record> list = query.list();

		data.addAll(list);

		session.close();
	}

	public void refreshList() {
		updateNameFilteredData("");
	}

	public void hideRecordsTable() {
		recordsTableStage.close();
		data.clear();
	}

	public void displayUserRecord(Record record) {
		List<Record> list = record.getUser().getRecordList();
		data.clear();
		data.addAll(list);
	}

	public void close() {
		factory.close();
	}

	public Stage getRecordsTableStage() {
		return recordsTableStage;
	}

	public void setRecordsTableStage(Stage recordsTableStage) {
		this.recordsTableStage = recordsTableStage;
	}

}
