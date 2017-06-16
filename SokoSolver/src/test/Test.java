package test;

import java.io.FileInputStream;
import java.util.List;

import model.data.Level;
import model.levelLoaders.MyTextLevelLoader;
import searchLib.Action;
import solver.SolveSokobanLevel;

public class Test {
	public static void main(String[] args) throws Exception {

		// T - E - S - T //
			
		Level lvl = new MyTextLevelLoader().loadLevel(new FileInputStream("./TestLevels/level4.txt"));
		Level tempLvl = lvl.clone();
		
		tempLvl.getBoxCount();
		
		SolveSokobanLevel solver = new SolveSokobanLevel();
		
		List<Action> actions = solver.noneOptimalSolve(lvl);

		for (Action action : actions) {
			System.out.println(action.getName());
		}
		
	}
}

