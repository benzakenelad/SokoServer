package solver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import model.data.Level;
import model.data.Move1Step;
import model.data.gameObjects.Box;
import model.data.gameObjects.Target;
import model.levelLoaders.MyObjectLevelLoader;
import model.levelSavers.MyObjectLevelSaver;
import model.policy.MySokobanPolicy;
import planLib.PlanAction;
import planLib.planners.Strips;
import searchLib.Action;
import solver.Plannable.PlannableSokobanLevel;
import solver.Plannable.SokobanPlannableLevelCreator;
import solver.Searchables.SearchableBoxPath;
import solver.Searchables.SearchablePlayerPath;
import solver.moveBox.MoveBox1Step;
import solver.moveBox.MoveBoxToTarget;
/**
 * <h6> solveSokobanLevel </h6> Solving SokoBan game Level.
 * @author Elad Ben Zaken
 *
 */
public class solveSokobanLevel {
	
	/**
	 * <h6> optimalSolve </h6> Solving SokoBan Level (optimal solving), Returns an ArrayList with the Actions who solves the Level.
	 * @param lvl - SokoBan Level to solve.
	 * @param times - As much as the value of times is bigger then the solution is more optimized.
	 * @return ArrayList with the solving actions, if there is no solution returns null.
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public List<Action> optimalSolve(Level lvl, int times) throws FileNotFoundException, Exception {
		MyObjectLevelSaver saver = new MyObjectLevelSaver();

		saver.saveLevel(new FileOutputStream("temp.obj"), lvl);

		List<Action> finalActions = null;
		boolean firstTimeSolved = true;
		for (int i = 0; i < times; i++) {
			Level tempLvl = new MyObjectLevelLoader().loadLevel(new FileInputStream("temp.obj"));
			List<Action> actions = null;
			solveSokobanLevel solver = new solveSokobanLevel();
			actions = solver.solve(tempLvl);
			if (firstTimeSolved)
				if (actions != null) {
					finalActions = actions;
					firstTimeSolved = false;
				}
			if (actions != null) {
				if (finalActions.size() > actions.size())
					finalActions = actions;
			}
		}
		return finalActions;
	}
	
	/**
	 * <h6> noneOptimalSolve </h6> Solving SokoBan Level (none optimal solving), Returns an ArrayList with the Actions who solves the Level.
	 * @param lvl - SokoBan Level to solve.
	 * @return ArrayList with the solving actions, if there is no solution returns null.
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public List<Action> noneOptimalSolve(Level lvl) throws FileNotFoundException, Exception {
		MyObjectLevelSaver saver = new MyObjectLevelSaver();
		
		saver.saveLevel(new FileOutputStream("temp.obj"), lvl);

		List<Action> finalActions = null;
		while (true) {
			Level tempLvl = new MyObjectLevelLoader().loadLevel(new FileInputStream("temp.obj"));
			
			solveSokobanLevel solver = new solveSokobanLevel();
			finalActions = solver.solve(tempLvl);
			if (finalActions != null)
				break;
		}
		return finalActions;
	}

	private List<Action> solve(Level lvl) {
		PlannableSokobanLevel plannable = new SokobanPlannableLevelCreator().action(lvl);
		Strips planner = new Strips();
		List<PlanAction> actions = planner.computePlan(plannable);
		int[][] classification = translatePlanActions(actions);

		MoveBoxToTarget move = new MoveBoxToTarget(new SearchableBoxPath(),
				new MoveBox1Step(new SearchablePlayerPath(), new MySokobanPolicy(), new Move1Step()));

		ArrayList<Box> boxesArr = lvl.getBoxesArray();
		ArrayList<Target> targetsArr = lvl.getTargetsArray();
		int len = classification.length;

		List<Action> movmentActions = new ArrayList<Action>();

		for (int i = 0; i < len; i++) {
			List<Action> tempList = null;
			try {
				tempList = move.move(lvl, boxesArr.get(classification[i][0]), targetsArr.get(classification[i][1]));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (tempList == null)
				return null;
			else
				movmentActions.addAll(tempList);
		}

		return movmentActions;
	}

	private int[][] translatePlanActions(List<PlanAction> actions) {
		int size = actions.size();
		int[][] arr = new int[size][];

		for (int i = 0; i < size; i++) {
			PlanAction action = actions.get(i);
			int[] line = new int[2];
			String[] parse = action.toString().split(" ");
			String num1 = parse[1].split(",")[0];
			String num2 = parse[2].split(",")[0];
			line[0] = Integer.parseInt(num1);
			line[1] = Integer.parseInt(num2);
			arr[i] = line;
		}
		return arr;
	}
}
