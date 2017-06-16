package view.GUI;
	
import controller.SokobanController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.SokobanModel;


public class Main extends Application {
	private static String note;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			// Main Window Loading
			FXMLLoader mainWindowloader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
			BorderPane mainWindowroot = (BorderPane)mainWindowloader.load();
			
			// Records Table Window Loading
			FXMLLoader recordsTableWindowloader = new FXMLLoader(getClass().getResource("RecordsTableWindow.fxml"));
			BorderPane recordsTableWindowroot = (BorderPane)recordsTableWindowloader.load();


			// Controllers catching
			MainWindowController view = (MainWindowController)mainWindowloader.getController();
			RecordsTableWindowController recordsTableWindowController = (RecordsTableWindowController)recordsTableWindowloader.getController();
			
			
			// Connections initialization
			SokobanModel model = new SokobanModel();
			SokobanController controller = new SokobanController(model, view, getNote());
			view.addObserver(controller);
			model.addObserver(controller);
			view.setRecordsTableWindowController(recordsTableWindowController);


			// Main Window
			Scene mainWindowScene = new Scene(mainWindowroot); // Scene scene = new Scene(root,900,800); 
			mainWindowScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(mainWindowScene);
			primaryStage.show();

			
			// Record Table Window
			Stage recordsTableWindowStage = new Stage();
			
			Scene recordsTableWindowScene = new Scene(recordsTableWindowroot); // Scene scene = new Scene(root,900,800); 
			recordsTableWindowScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			recordsTableWindowStage.setScene(recordsTableWindowScene);
			
			recordsTableWindowController.setRecordsTableStage(recordsTableWindowStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		String noteForController = new String("");
			
		if(args.length >= 2 && args[0].compareTo("-server") == 0)			
			noteForController = args[0] + " " + args[1];
		
		setNote(noteForController);
		
		launch(args); // application launch		
	}

	public static String getNote() {
		return note;
	}

	public static void setNote(String note) {
		Main.note = note;
	}
	
}
