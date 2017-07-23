package model.data;

import java.io.Serializable;
import java.util.ArrayList;

import model.data.gameObjects.Box;
import model.data.gameObjects.Character;
import model.data.gameObjects.GameObject;
import model.data.gameObjects.Position;
import model.data.gameObjects.Target;

public class Level implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// data members
	private String ID; // level's ID
	private GameObject[][] levelData = null; // store all game objects
	private Character player = null; // the level's character
	private ArrayList<Target> targetsArray = new ArrayList<Target>(); // target holder
	private ArrayList<Box> boxesArray = new ArrayList<Box>(); // boxes holder
	private int levelNumber = 0; // level number
	private int levelMaxWidth = 0; // the level max Width
	private int levelMaxHeight = 0; // the level height (number of lines)
	private int boxCount = 0; // box counter
	private int targetCount = 0; // target counter
	private int wallCount = 0; // wall counter
	private int emptyCellCount = 0; // empty cells counter
	private int numOfBoxesOnTargets = 0; // number of boxes on targets
	private int steps = 0; // player steps counter
	private double finishTime = 0; // finish time
	private boolean levelFinishedFlag = false;
	
	public Level() {}

	// getters & setters

	public void setID(String ID){
		this.ID = ID;
	}
	
	public String getID() {
		return ID;
	}

	public int getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}

	public int getLevelMaxWidth() {
		return levelMaxWidth;
	}

	public void setLevelMaxWidth(int levelMaxWidth) {
		this.levelMaxWidth = levelMaxWidth;
	}

	public int getLevelMaxHeight() {
		return levelMaxHeight;
	}

	public void setLevelMaxHeight(int levelMaxHeight) {
		this.levelMaxHeight = levelMaxHeight;
	}

	public int getBoxCount() {
		return boxCount;
	}

	public void setBoxCount(int boxCount) {
		this.boxCount = boxCount;
	}

	public int getTargetCount() {
		return targetCount;
	}

	public void setTargetCount(int targetCount) {
		this.targetCount = targetCount;
	}

	public int getWallCount() {
		return wallCount;
	}

	public void setWallCount(int wallCount) {
		this.wallCount = wallCount;
	}

	public int getEmptyCellCount() {
		return emptyCellCount;
	}

	public void setEmptyCellCount(int emptyCellCount) {
		this.emptyCellCount = emptyCellCount;
	}

	public GameObject[][] getLevelData() {
		return levelData;
	}

	public void setLevelData(GameObject[][] levelData) {
		this.levelData = levelData;
	}

	public Character getPlayer() {
		return player;
	}

	public void setPlayer(Character player) {
		this.player = player;
	}

	public int getNumOfBoxesOnTargets() {
		return numOfBoxesOnTargets;
	}

	public void setNumOfBoxesOnTargets(int numOfBoxesOnTargets) {
		this.numOfBoxesOnTargets = numOfBoxesOnTargets;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public boolean isLevelFinishedFlag() {
		return levelFinishedFlag;
	}

	public void setLevelFinishedFlag(boolean levelFinishedFlag) {
		this.levelFinishedFlag = levelFinishedFlag;
	}

	public double getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(double finishTime) {
		this.finishTime = finishTime;
	}

	public ArrayList<Box> getBoxesArray() {
		return boxesArray;
	}

	public void setBoxesArray(ArrayList<Box> boxesArray) {
		this.boxesArray = boxesArray;
	}

	public ArrayList<Target> getTargetsArray() {
		return targetsArray;
	}

	public void sesTargetsArray(ArrayList<Target> targetsArray) {
		this.targetsArray = targetsArray;
	}

	// methods

	public GameObject getGameObjectByPosition(Position position) throws Exception {
		if (position != null) {
			int row = position.getRow();
			int col = position.getCol();
			return levelData[row][col];
		}
		return null;
	}

	public void moveObjectToPosition(GameObject gameObj, Position dest) throws Exception {
		if (gameObj == null)
			throw new Exception("Null game object.");
		if (dest == null)
			throw new Exception("Null position.");

		int row = dest.getRow();
		int col = dest.getCol();

		if (levelData[row][col] != null) // there is an object on this position
			throw new Exception("Exception : Try to move an object on an excisting object.");

		gameObj.setPos(dest);
		levelData[row][col] = gameObj;
	}

	public void makeSlotNullByPosition(Position position) {
		if (position != null) {
			int row = position.getRow(), col = position.getCol();
			levelData[row][col] = null;
		}
	}

	public void levelCompletionCheck() {
		for (Target target : targetsArray)
			if (target.gotBoxOnMe() == false) {
				levelFinishedFlag = false;
				return;
			}
		levelFinishedFlag = true;
	}

	public void movePlayerOnTarget(Character player, Target target) throws Exception {
		if (target.gotGameObjectOnMe() == false) {
			target.setOnMe(player);
			player.setPos(target.getPos());
		}
	}

	public char[][] getLevelByChar2DArray() {
		char[][] data = new char[this.levelMaxHeight][];
		for (int i = 0; i < this.levelMaxHeight; i++)
			data[i] = new char[levelData[i].length];

		for (int i = 0; i < this.levelMaxHeight; i++)
			for (int j = 0; j < data[i].length; j++)
				if(levelData[i][j] != null)
					data[i][j] = this.levelData[i][j].toString().charAt(0);
				else
					data[i][j] = ' ';
		return data;
	}

	@Override
	public Level clone() {
		Level clone = new Level();
		try {
			clone.targetsArray = new ArrayList<Target>();
			clone.boxesArray = new ArrayList<Box>();
			clone.levelData = new GameObject[levelData.length][];
			for (int i = 0; i < levelData.length; i++) {
				clone.levelData[i] = new GameObject[levelData[i].length];
				for (int j = 0; j < levelData[i].length; j++)
					if (levelData[i][j] != null)
						clone.levelData[i][j] = levelData[i][j].clone();
					else
						clone.levelData[i][j] = null;
			}

			ArrayList<Target> tempTargetsArr = new ArrayList<Target>();
			ArrayList<Box> tempBoxesArr = new ArrayList<Box>();

			for (int i = 0; i < clone.levelData.length; i++)
				for (int j = 0; j < clone.levelData[i].length; j++) {
					if (clone.levelData[i][j] == null)
						continue;
					if (clone.levelData[i][j] instanceof Target) {
						tempTargetsArr.add((Target) clone.levelData[i][j]);
						Target t = (Target) clone.levelData[i][j];
						if (t.gotGameObjectOnMe()) {
							if (t.getOnMe() instanceof Character)
								clone.player = (Character) t.getOnMe();
							else if (t.getOnMe() instanceof Box)
								tempBoxesArr.add((Box) t.getOnMe());
						}
					} else if (clone.levelData[i][j] instanceof Character) {
						clone.player = (Character) clone.levelData[i][j];
					} else if (clone.levelData[i][j] instanceof Box)
						tempBoxesArr.add((Box) clone.levelData[i][j]);
				}

			for (int i = 0; i < boxesArray.size(); i++)
				for (int j = 0; j < boxesArray.size(); j++)
					if (boxesArray.get(i).getPos().equals(tempBoxesArr.get(j).getPos()))
						clone.boxesArray.add(tempBoxesArr.get(j));
			for (int i = 0; i < targetsArray.size(); i++)
				for (int j = 0; j < targetsArray.size(); j++)
					if (targetsArray.get(i).getPos().equals(tempTargetsArr.get(j).getPos()))
						clone.targetsArray.add(tempTargetsArr.get(j));

			if (ID != null)
				clone.ID = new String(ID);
			else
				clone.ID = null;

			clone.levelNumber = levelNumber;
			clone.levelMaxHeight = levelMaxHeight;
			clone.levelMaxWidth = levelMaxWidth;
			clone.boxCount = boxCount;
			clone.targetCount = targetCount;
			clone.wallCount = wallCount;
			clone.emptyCellCount = emptyCellCount;
			clone.numOfBoxesOnTargets = numOfBoxesOnTargets;
			clone.steps = steps;
			clone.finishTime = finishTime;
			clone.levelFinishedFlag = levelFinishedFlag;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return clone;
	}
	
	public ArrayList<String> getLevelByArrayListOfStrings() {
		ArrayList<String> levelDataTXT = new ArrayList<String>();
		StringBuilder sb = new StringBuilder("");

		for (int i = 0; i < levelMaxHeight; i++) {
			int length = levelData[i].length;
			for (int j = 0; j < length; j++) {
				if (levelData[i][j] != null)
					sb.append(levelData[i][j].toString());
				else
					sb.append(" ");
			}
			levelDataTXT.add(new String(sb.toString()));
			sb.setLength(0);
		}
		return levelDataTXT;
	}

}
