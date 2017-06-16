package solver.Plannable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import model.data.Level;
import model.data.gameObjects.Box;
import model.data.gameObjects.Target;
import planLib.Predicate;

public class SokobanPlannableLevelCreator {
	public static PlannableSokobanLevel action(Level lvl){
		ArrayList<Box> boxesList = lvl.getBoxesArray();
		ArrayList<Target> targetsList = lvl.getTargetsArray();
		
		PlannableSokobanLevel plannable = new PlannableSokobanLevel();
		
		MoveAction action = new MoveAction(); // Initialize move action
		
		List<Object> illegalValuesForX = new ArrayList<Object>();
		List<Object> illegalValuesForY = new ArrayList<Object>();
		
		int boxArraySize = boxesList.size(); // set the boxes object list
		for(int i = 0; i < boxArraySize; i++){
			if(!boxesList.get(i).isOnTarget()){
				plannable.getObjects().add(new String("B " + i));
				plannable.getStartState().addPredicate(new Predicate("OnFloor", "B " + i));
				illegalValuesForY.add("B " + i);
			}
		}
		int  targetArraySize = targetsList.size(); // set the targets object list + goal state
		for(int i = 0; i < targetArraySize; i++){
			if(!targetsList.get(i).gotBoxOnMe()){
				plannable.getObjects().add(new String("T " + i));
				plannable.getGoal().getPredicates().add(new Predicate("On", "T " + i));
				plannable.getStartState().addPredicate(new Predicate("Clear", "T " + i));
				illegalValuesForX.add("T " + i);
			}
		}
		
		action.getIllegalAssignments().put("x", illegalValuesForX);
		action.getIllegalAssignments().put("y", illegalValuesForY);
		
		plannable.getAllActions().add(action);
		
		long seed = System.nanoTime();
		Collections.shuffle(plannable.getGoal().getPredicates(), new Random(seed));
		
		return plannable;
	}
}
