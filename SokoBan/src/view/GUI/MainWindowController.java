package view.GUI;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.data.Level;
import view.LevelGraphicDisplay;

public class MainWindowController extends Observable implements View, Initializable {
	// Data Members
	@FXML
	private LevelGraphicDisplay levelGraphicDisplay;
	@FXML
	private Text steps;
	@FXML
	private Text timer;

	private RecordsTableWindowController recordsTableWindowController;
	private Double counter = new Double(0); // time count
	private StringProperty countString; // timer string	
	private boolean countFlag = true; // responsible for timer stoping
	private boolean timerFlag = false; // timer is on
	private Timer timerThread = new Timer();; // timer	
	private MediaPlayer mediaPlayer = null; // media player
	private boolean levelCompleted = false; // levelCompleted flag

	// initialization
	@Override
	public void initialize(java.net.URL location, ResourceBundle resources) {

		// start the background music
		startMusic();

		// draw the welcome image
		levelGraphicDisplay.welcomeDraw();

		// time initialize
		countString = new SimpleStringProperty("Timer : 0");
		timer.textProperty().bind(countString);
		timer.setVisible(true);
		

		// key function initialization
		levelGraphicDisplay.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> levelGraphicDisplay.requestFocus());
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				levelGraphicDisplay.setOnKeyPressed(new EventHandler<KeyEvent>() {

					@Override
					public void handle(KeyEvent event) {
						switch (event.getCode()) {
						case UP:
							if (levelCompleted == false) {
								levelGraphicDisplay.setCurrentPlayerPosition("up");
								setChanged();
								notifyObservers("move up");
							}
							break;
						case DOWN:
							if (levelCompleted == false) {
								levelGraphicDisplay.setCurrentPlayerPosition("down");
								setChanged();
								notifyObservers("move down");
							}
							break;
						case LEFT:
							if (levelCompleted == false) {
								levelGraphicDisplay.setCurrentPlayerPosition("left");
								setChanged();
								notifyObservers("move left");
							}
							break;
						case RIGHT:
							if (levelCompleted == false) {
								levelGraphicDisplay.setCurrentPlayerPosition("right");
								setChanged();
								notifyObservers("move right");
							}
							break;
						case ESCAPE:
							close();
							break;
						case O:
							openFile();
							break;
						case S:
							saveFile();
						default:
							break;
						}
						
						levelGraphicDisplay.requestFocus();
					}
				});
			
			}
		});
		
		
	}

	// Record Table Window display/hide
	public void displayRecordsTable() {
		recordsTableWindowController.displayRecordsTable();
	}

	public void hideRecordsTable() {
		recordsTableWindowController.hideRecordsTable();
	}

	// Methods
	@Override
	public void Display(Level lvl) {
		if (lvl == null || levelCompleted == true)
			return;
		
		setStepsText("Steps: " + Integer.toString(lvl.getSteps())); // update the steps string	
		
		if (lvl.isLevelFinishedFlag() == true) {
			lvl.setFinishTime(counter.doubleValue());
			countFlag = false;
			levelCompleted = true;
		}
			
		Platform.runLater(new Runnable() {			
			@Override
			public void run() {
					levelGraphicDisplay.redraw(lvl);							
			}
		});
		
		if(levelCompleted){
			try {Thread.sleep(1000);} catch (Exception e) {}
			finishAct(lvl);
		}
		
	}

	public void resetTimer(){ // reset the game timer	
		if(timerFlag){
			counter = 0.0; // reset timer
			countFlag = true; // return the counting
		}
		else{	
			timerThread.scheduleAtFixedRate(new TimerTask() {

				@Override
				public void run() {
					counter += 0.1;

					counter = BigDecimal.valueOf(counter).setScale(3, RoundingMode.HALF_UP).doubleValue();
					if(countFlag)
						countString.set("Timer: " + counter);
				}
			}, 0, 100);
			timerFlag = true;
		}
	}	

	private void startMusic() // start the background music
	{
		// Music initialization
		String musicFile = "./resources/song.mp3";
		Media sound = new Media(new File(musicFile).toURI().toString());
		mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();

	}

	public void saveFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Save SokoBan's Level File");
		fc.setInitialDirectory(new File("./Levels"));
		File chosen = fc.showSaveDialog(null);
		if (chosen != null) {
			setChanged();
			notifyObservers("save " + chosen.getPath());
		}
	}

	public void openFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Open SokoBan's Level File");
		fc.setInitialDirectory(new File("./Levels"));
		File chosen = fc.showOpenDialog(null);
		if (chosen != null) {
			System.out.println(chosen.getPath()); // print the path
			resetTimer();
			levelCompleted = false;
			setChanged();
			notifyObservers("load Levels/" + chosen.getName());
		}	
	}

	public void finishAct(Level lvl) {
		levelGraphicDisplay.finishDraw();

		Platform.runLater(new Runnable() {

			@Override
			public void run() {

				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Record List");
				alert.setHeaderText(null);
				alert.setContentText("Would you like to enroll the recrod list?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					recordsTableWindowController.addArecord(lvl.getID(), lvl.getSteps(), lvl.getFinishTime());
				} else {
					return;
				}
			}
		});

	}

	public void close() {
		setChanged();
		notifyObservers("exit");
	}

	public void safeExit() {
		mediaPlayer.stop();
		recordsTableWindowController.close();
		if (timerThread != null)
			timerThread.cancel();
		Platform.exit();
	}

	private void setStepsText(String text) {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				steps.setText(text);
				
			}
		});
		
	}

	public void setRecordsTableWindowController(RecordsTableWindowController recordsTableWindowController) {
		this.recordsTableWindowController = recordsTableWindowController;
	}

	public void solveLevel() {
		setChanged();
		notifyObservers("solve");
	}

	public void quickLevelSolve() {
		setChanged();
		notifyObservers("quicksolve");
	}

}
