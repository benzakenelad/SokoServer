package model.levelLoaders;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import model.data.Level;
import model.data.gameObjects.Box;
import model.data.gameObjects.Character;
import model.data.gameObjects.GameObject;
import model.data.gameObjects.Position;
import model.data.gameObjects.Target;
import model.data.gameObjects.Wall;

public class MyTextLevelLoader implements LevelLoader {

	@Override
	public Level loadLevel(InputStream in) throws Exception {

		ArrayList<String> levelDataTXT = new ArrayList<String>();
		Level lvl = new Level();

		BufferedReader input = new BufferedReader(new InputStreamReader(in));

		// read the level ID
		
		lvl.setID(input.readLine());
		
		if(lvl.getID().charAt(0) == '#'){
			levelDataTXT.add(lvl.getID());
			lvl.setID("");
		}
		// read from text file to ArrayList<String>
		String s = new String();
		while (s != null) {
			s = input.readLine();
			if (s != null)
				levelDataTXT.add(s);
		}

		// Temporary objects declaration
		GameObject go = null;
		int height = levelDataTXT.size();
		GameObject[][] levelData = new GameObject[height][];
		GameObject[] lineData = null;
		boolean flag = true;
		

		ArrayList<Target> targetsArr = lvl.getTargetsArray();
		ArrayList<Box> boxesArr = lvl.getBoxesArray();

		// levelData objects building
		for (int i = 0; i < height; i++) {
			int length = levelDataTXT.get(i).length();
			lineData = new GameObject[length];
			for (int j = 0; j < length; j++) {
				char ch = levelDataTXT.get(i).charAt(j);
				go = generateGameObject(ch, new Position(i, j));

				if (go == null)
					continue;
				
				if (go instanceof Target)
					targetsArr.add((Target) go);
				
				if (go instanceof Box)
					boxesArr.add((Box) go);
				
				lineData[j] = go;
				
				if(ch == '$'){
					boxesArr.add((Box) ((Target)go).getOnMe());
					lvl.setNumOfBoxesOnTargets(lvl.getNumOfBoxesOnTargets() + 1);
				}
				else if(ch == 'B' && flag){
					lvl.setPlayer((Character) ((Target)go).getOnMe());
					flag = false;
				}else if (ch == 'A' && flag){ // flag make sure we have only one character(player)
					lvl.setPlayer((Character) go);
					flag = false;
				}
		
			}
			levelData[i] = lineData;
			lineData = null;
		}

		// level details set
		lvl.setLevelData(levelData);
		levelDataFill(lvl, levelDataTXT);

		return lvl;
	}

	private void levelDataFill(Level lvl, ArrayList<String> arr) {
		int levelWidth = -1;
		int levelHeight = 0;
		int wallCount = 0;
		int emptyCellCount = 0;

		levelHeight = arr.size();

		for (int i = 0; i < levelHeight; i++)
			if (levelWidth < arr.get(i).length())
				levelWidth = arr.get(i).length();

		// counting objects
		for (int i = 0; i < levelHeight; i++) {
			int length = arr.get(i).length();
			for (int j = 0; j < length; j++) {
				char c = arr.get(i).charAt(j);
				switch (c) {
				case '#': // case its wall
					wallCount++;
					break;
				case ' ':
					emptyCellCount++;
					break;
				default:
					break;
				}
			}
		}

		// level data setting
		lvl.setLevelMaxHeight(levelHeight);
		lvl.setLevelMaxWidth(levelWidth);
		lvl.setEmptyCellCount(emptyCellCount);
		lvl.setTargetCount(lvl.getTargetsArray().size());
		lvl.setWallCount(wallCount);
		lvl.setBoxCount(lvl.getBoxesArray().size());

	}

	private GameObject generateGameObject(char c, Position pos)
	{
		switch (c) {
		case ' ':
			return null;
		case '#': // case its wall
			return new Wall(pos);
		case '@': // case its box on empty slot
			return new Box(pos);
		case 'o': // case its empty target
			return new Target(pos);
		case '$':
			Target t2 = new Target(pos);
			Box b = new Box(pos);
			t2.setOnMe(b);
			return t2;
		case 'A': // case its player on empty slot
			return new Character(pos);
		case 'B':
			Target t1 = new Target(pos);
			Character ch = new Character(pos);
			t1.setOnMe(ch);
			return t1;
		default:
			return null;
		}
	}

}
