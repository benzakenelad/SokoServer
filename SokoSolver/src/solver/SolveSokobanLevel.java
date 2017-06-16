package solver;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import model.data.Level;
import model.data.Move1Step;
import model.data.gameObjects.Box;
import model.data.gameObjects.Target;
import model.policy.MySokobanPolicy;
import planLib.PlanAction;
import planLib.planners.Strips;
import searchLib.Action;
import solver.Plannable.PlannableSokobanLevel;
import solver.Plannable.SokobanHeuristic;
import solver.Plannable.SokobanPlannableLevelCreator;
import solver.Searchables.SearchableBoxPath;
import solver.Searchables.SearchablePlayerPath;
import solver.moveBox.MoveBox1Step;
import solver.moveBox.MoveBoxToTarget;

/**
 * <h6>solveSokobanLevel</h6> Solving SokoBan game Level.
 * 
 * @author Elad Ben Zaken
 *
 */
public class SolveSokobanLevel {

	/**
	 * <h6>optimalSolve</h6> Solving SokoBan Level t times, Returns an ArrayList
	 * with the smallest amount of Actions who solves the Level.
	 * 
	 * @param lvl
	 *            - SokoBan Level to solve.
	 * @param times
	 *            - As much as the value of times is bigger then the solution is
	 *            more optimized.
	 * @return ArrayList with the solving actions, if there is no solution
	 *         returns null.
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public List<Action> optimalSolve(Level lvl, int t) throws FileNotFoundException, Exception {
		List<Action> finalActions = null;
		boolean firstTimeSolved = true;

		for (int i = 0; i < t; i++) {
			List<Action> actions = null;
			while (true) {
				Level tempLvl = lvl.clone();
				actions = this.solve(tempLvl);
				if(actions != null)
					break;
			}

			if (firstTimeSolved) {
				finalActions = actions;
				firstTimeSolved = false;
			}
			if (finalActions.size() > actions.size())
				finalActions = actions;

		}
		return finalActions; 
	}

	/**
	 * <h6>noneOptimalSolve</h6> Solving SokoBan Level (none optimal solving),
	 * Returns an ArrayList with the Actions who solves the Level.
	 * 
	 * @param lvl
	 *            - SokoBan Level to solve.
	 * @return ArrayList with the solving actions, if there is no solution
	 *         returns null.
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public List<Action> noneOptimalSolve(Level lvl) throws FileNotFoundException, Exception {
		List<Action> finalActions = null;
		while (true) {
			Level tempLvl = lvl.clone();
			finalActions = this.solve(tempLvl);
			if (finalActions != null)
				break;
		}
		return finalActions;
	}

	private List<Action> solve(Level lvl) {
		PlannableSokobanLevel plannable = SokobanPlannableLevelCreator.action(lvl);
		Strips planner = new Strips();
		List<PlanAction> actions = planner.computePlan(plannable, new SokobanHeuristic(lvl));
		
		if (actions == null)
			return null;
		
		MoveBox1Step moveBox = new MoveBox1Step(new SearchablePlayerPath(), new MySokobanPolicy(), new Move1Step());
		MoveBoxToTarget move = new MoveBoxToTarget(new SearchableBoxPath(), moveBox);
		ArrayList<Box> boxesArr = lvl.getBoxesArray();
		ArrayList<Target> targetsArr = lvl.getTargetsArray();
		int[][] classification = translatePlanActions(actions);
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

	private int[][] translatePlanActions(List<PlanAction> actions) { // TODO
		int len = actions.size();
		int[][] arr = new int[len][2];

		for (int i = 0; i < len; i++) {
			List<String> args = actions.get(i).getArgs();
			arr[i][0] = Integer.parseInt(args.get(0).split(" ")[1]);
			arr[i][1] = Integer.parseInt(args.get(1).split(" ")[1]);
		}
		return arr;
	}
}
