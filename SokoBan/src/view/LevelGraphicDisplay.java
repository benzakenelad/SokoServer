package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.data.Level;

public class LevelGraphicDisplay extends Canvas
{
	// Images
	private Image wall = null;
	private Image currentPlayerPosition = null;
	private Image upPositionPlayer = null;
	private Image downPositionPlayer = null;
	private Image rightPositionPlayer = null;
	private Image leftPositionPlayer = null;
	private Image floor = null;
	private Image box = null;
	private Image target = null;
	private Image boxontarget = null;
	private Image congratulations = null;
	private Image welcome = null;
	private boolean initializedFlag = false;

	// Properties
	private StringProperty upPlayerFileName;
	private StringProperty downPlayerFileName;
	private StringProperty rightPlayerFileName;
	private StringProperty leftPlayerFileName;
	private StringProperty wallFileName;
    private StringProperty floorFileName;
    private StringProperty boxFileName;
	private StringProperty targetFileName;
	private StringProperty boxOnTargetFileName;
	private StringProperty congratulationsFileName;
	private StringProperty welcomeFileName;

	// C'TOR
	public LevelGraphicDisplay() {
		upPlayerFileName = new SimpleStringProperty();
		downPlayerFileName = new SimpleStringProperty();
		rightPlayerFileName = new SimpleStringProperty();
		leftPlayerFileName = new SimpleStringProperty();
		upPlayerFileName = new SimpleStringProperty();
		downPlayerFileName = new SimpleStringProperty();
		rightPlayerFileName = new SimpleStringProperty();
		leftPlayerFileName = new SimpleStringProperty();
		wallFileName = new SimpleStringProperty();
		floorFileName = new SimpleStringProperty();
		boxFileName = new SimpleStringProperty();
		targetFileName = new SimpleStringProperty();
		boxFileName = new SimpleStringProperty();
		boxOnTargetFileName = new SimpleStringProperty();
		congratulationsFileName = new SimpleStringProperty();
		welcomeFileName = new SimpleStringProperty();	
	}
	
	public void finishDraw()
	{		
		GraphicsContext gc = getGraphicsContext2D();
		if(congratulations != null)
			gc.drawImage(congratulations, 0, 0, getWidth(), getHeight());
	}
	
	public void welcomeDraw()
	{
		if(!initializedFlag){
			initalizeImages();
			initializedFlag = true;
		}
		GraphicsContext gc = getGraphicsContext2D();
		if(welcome != null)
			gc.drawImage(welcome, 0, 0, getWidth(), getHeight());
	}
	
	public void redraw(Level lvl)
	{
		if(lvl == null)
			return;
		
		char[][] levelData = lvl.getLevelByChar2DArray();
		double itemWidth = getWidth() / lvl.getLevelMaxWidth();
		double itemHeight = getHeight() / lvl.getLevelMaxHeight();
		
		GraphicsContext gc = getGraphicsContext2D();
			
		for(int i = 0; i < levelData.length; i++)
			for(int j = 0; j < levelData[i].length; j++)
			{
				switch (levelData[i][j]) {
				case '#':
					gc.drawImage(wall, j * itemWidth , i * itemHeight , itemWidth, itemHeight);
					break;
				case ' ':
					gc.drawImage(floor, j * itemWidth , i * itemHeight , itemWidth, itemHeight);
					break;
				case '@':
					gc.drawImage(box, j * itemWidth , i * itemHeight , itemWidth, itemHeight);
					break;
				case 'A':
					gc.drawImage(floor, j * itemWidth , i * itemHeight , itemWidth, itemHeight);
					gc.drawImage(getCurrentPositionPlayer(), j * itemWidth , i * itemHeight , itemWidth, itemHeight);
					break;
				case 'o':
					gc.drawImage(target, j * itemWidth , i * itemHeight , itemWidth, itemHeight);
					break;
				case 'B':
					gc.drawImage(floor, j * itemWidth , i * itemHeight , itemWidth, itemHeight);
					gc.drawImage(getCurrentPositionPlayer(), j * itemWidth , i * itemHeight , itemWidth, itemHeight);
					break;
				case '$':
					gc.drawImage(boxontarget, j * itemWidth , i * itemHeight , itemWidth, itemHeight);
					break;
				default:
					
					break;
				}	
			}	
	}
	
	private void initalizeImages(){
		try {
			welcome = new Image(new FileInputStream(welcomeFileName.get()));	
			upPositionPlayer = new Image(new FileInputStream(upPlayerFileName.get()));
			downPositionPlayer = new Image(new FileInputStream(downPlayerFileName.get()));
			rightPositionPlayer = new Image(new FileInputStream(rightPlayerFileName.get()));
			leftPositionPlayer = new Image(new FileInputStream(leftPlayerFileName.get()));
			wall = new Image(new FileInputStream(wallFileName.get()));
			floor = new Image(new FileInputStream(floorFileName.get()));
			box = new Image(new FileInputStream(boxFileName.get()));
			target = new Image(new FileInputStream(targetFileName.get()));
			boxontarget = new Image(new FileInputStream(boxOnTargetFileName.get()));
			congratulations = new Image(new FileInputStream(congratulationsFileName.get()));
			currentPlayerPosition = downPositionPlayer;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void clear()
	{
		this.getGraphicsContext2D().clearRect(0, 0, getWidth(), getHeight());
	}
	
	public Image getCurrentPositionPlayer(){
		return currentPlayerPosition;
	}
	
	public  void setCurrentPlayerPosition(String direction){
		switch (direction) {
		case "up":
			currentPlayerPosition = upPositionPlayer;
			break;
		case "down":
			currentPlayerPosition = downPositionPlayer;
			break;
		case "left":
			currentPlayerPosition = leftPositionPlayer;
			break;
		case "right":
			currentPlayerPosition = rightPositionPlayer;
			break;
		default:
			break;
		}
	}
		
	public String getWallFileName() {
		return wallFileName.get();
	}

	public void setWallFileName(String wallFileName) {
		this.wallFileName.set(wallFileName);
	}

	public String getFloorFileName() {
		return floorFileName.get();
	}

	public void setFloorFileName(String floorFileName) {
		this.floorFileName.set(floorFileName);
	}

	public String getBoxFileName() {
		return boxFileName.get();
	}

	public void setBoxFileName(String boxFileName) {
		this.boxFileName.set(boxFileName);
	}

	public String getTargetFileName() {
		return targetFileName.get();
	}

	public void setTargetFileName(String targetFileName) {
		this.targetFileName.set(targetFileName);
	}

	public String getBoxOnTargetFileName() {
		return boxOnTargetFileName.get();
	}

	public void setBoxOnTargetFileName(String boxOnTargetFileName) {
		this.boxOnTargetFileName.set(boxOnTargetFileName);
	}

	public String getCongratulationsFileName() {
		return congratulationsFileName.get();
	}

	public void setCongratulationsFileName(String congratulationsFileName) {
		this.congratulationsFileName.set(congratulationsFileName);
	}

	public String getWelcomeFileName() {
		return welcomeFileName.get();
	}

	public void setWelcomeFileName(String welcomeFileName) {
		this.welcomeFileName.set(welcomeFileName);
	}
	
	public String getUpPlayerFileName() {
		return upPlayerFileName.get();
	}

	public void setUpPlayerFileName(String upPlayerFileName) {
		this.upPlayerFileName.set(upPlayerFileName);
	}
	
	public String getDownPlayerFileName() {
		return downPlayerFileName.get();
	}

	public void setDownPlayerFileName(String downPlayerFileName) {
		this.downPlayerFileName.set(downPlayerFileName);
	}
	
	public String getRightPlayerFileName() {
		return rightPlayerFileName.get();
	}

	public void setRightPlayerFileName(String rightPlayerFileName) {
		this.rightPlayerFileName.set(rightPlayerFileName);
	}
	
	public String getLeftPlayerFileName() {
		return upPlayerFileName.get();
	}

	public void setLeftPlayerFileName(String leftPlayerFileName) {
		this.leftPlayerFileName.set(leftPlayerFileName);
	}
	


}
