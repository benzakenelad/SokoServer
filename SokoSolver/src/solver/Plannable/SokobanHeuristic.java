/**
 * 
 */
package solver.Plannable;

import java.util.ArrayList;
import java.util.List;

import model.data.Level;
import model.data.Move1Step;
import model.data.gameObjects.Box;
import model.data.gameObjects.Target;
import model.policy.MySokobanPolicy;
import planLib.HeuristicFunc;
import searchLib.Action;
import solver.Searchables.SearchableBoxPath;
import solver.Searchables.SearchablePlayerPath;
import solver.moveBox.MoveBox1Step;
import solver.moveBox.MoveBoxToTarget;

/**
 * @author elad
 *
 */
public class SokobanHeuristic implements HeuristicFunc {

	private Level lvl = null;
	MoveBoxToTarget move = null;

	public SokobanHeuristic(Level lvl) {
		this.lvl = lvl.clone();
		move = new MoveBoxToTarget(new SearchableBoxPath(),
				new MoveBox1Step(new SearchablePlayerPath(), new MySokobanPolicy(), new Move1Step()));;
	}

	@Override
	public boolean heuristic(Object... objects) {
		if (objects[0] == null || objects[1] == null)
			return false;
		String str1, str2;
		if (objects[0] instanceof String && objects[1] instanceof String) {
			str1 = (String) (objects[0]);
			str2 = (String) (objects[1]);
		} else{
			System.out.println("HEURISTIC FUCNTION ERROR");
			return false;
		}
		
		Level tempLvl = this.lvl.clone();
		
		
		int boxInd = Integer.parseInt(str1.split(" ")[1]);
		int targetInd = Integer.parseInt(str2.split(" ")[1]);
		ArrayList<Box> boxesArr = tempLvl.getBoxesArray();
		ArrayList<Target> targetsArr = tempLvl.getTargetsArray();
		Box box = boxesArr.get(boxInd);
		Target target = targetsArr.get(targetInd);	

		List<Action> list = null;
		try {
			list = move.move(tempLvl, box, target);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (list != null){
			this.lvl = tempLvl;
			return true;
		}
		else
			return false;
	}

}
