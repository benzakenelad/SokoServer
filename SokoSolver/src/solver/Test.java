package solver;

import java.io.FileInputStream;
import java.util.List;

import model.data.Level;
import model.levelLoaders.MyTextLevelLoader;
import searchLib.Action;

public class Test {
	public static void main(String[] args) throws Exception {

		
		// TEST
		
		List<Action> actions;

		Level lvl = new MyTextLevelLoader().loadLevel(new FileInputStream("Level3.txt"));

		solveSokobanLevel solver = new solveSokobanLevel();
		
		actions = solver.optimalSolve(lvl,500);

		for (Action action : actions) {
			System.out.println(action.getName());
		}
		
	}
}

