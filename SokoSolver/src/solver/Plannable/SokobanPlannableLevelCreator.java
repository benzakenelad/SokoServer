package solver.Plannable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import model.data.Level;
import model.data.gameObjects.Box;
import model.data.gameObjects.Target;
import planLib.PlanAction;
import planLib.Predicate;

public class SokobanPlannableLevelCreator {
	public PlannableSokobanLevel action(Level lvl){
		ArrayList<Box> boxesList = lvl.getBoxesArray();
		ArrayList<Target> targetsList = lvl.getTargetsArray();
		
		PlannableSokobanLevel plannable = new PlannableSokobanLevel();
		
		PlanAction action = new PlanAction(); // Initialize move action
		action.setName("Move");
		action.getArgs().add("x");
		action.getArgs().add("y");
		action.getPreconditions().add(new Predicate("OnFloor", "x"));
		action.getPreconditions().add(new Predicate("Clear", "y"));
		action.getAddList().add(new Predicate("On", "y"));
		action.getDeleteList().add(new Predicate("OnFloor", "x"));
		action.getDeleteList().add(new Predicate("Clear", "y"));
		
		List<Object> illegalValuesForX = new ArrayList<Object>();
		List<Object> illegalValuesForY = new ArrayList<Object>();
		
		int boxArraySize = boxesList.size(); // set the boxes object list
		for(int i = 0; i < boxArraySize; i++){
			if(!boxesList.get(i).isOnTarget()){
				plannable.getObjects().add(new String("Box " + i));
				plannable.getStartState().addPredicate(new Predicate("OnFloor", "Box " + i));
				illegalValuesForY.add("Box " + i);
			}
		}
		int  targetArraySize = targetsList.size(); // set the targets object list + goal state
		for(int i = 0; i < targetArraySize; i++){
			if(!targetsList.get(i).gotBoxOnMe()){
				plannable.getObjects().add(new String("Target " + i));
				plannable.getGoal().getPredicates().add(new Predicate("On", "Target " + i));
				plannable.getStartState().addPredicate(new Predicate("Clear", "Target " + i));
				illegalValuesForX.add("Target " + i);
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
