package solver.Plannable;

import java.util.ArrayList;
import java.util.List;

import planLib.PlanAction;
import planLib.Goal;
import planLib.Plannable;
import planLib.State;

public class PlannableSokobanLevel implements Plannable {
	
	private List<PlanAction> actions = new ArrayList<PlanAction>();
	private State startState = new State();
	private Goal goal = new Goal();
	private List<Object> objects = new ArrayList<Object>();


	@Override
	public List<PlanAction> getAllActions() {
		return actions;
	}

	@Override
	public State getStartState() {
		return startState;
	}

	@Override
	public Goal getGoal() {
		return goal;
	}

	@Override
	public void setGoal(Goal goal) {
		this.goal = goal;

	}

	@Override
	public List<Object> getObjects() {
		return objects;
	}

}
