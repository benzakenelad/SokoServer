package planLib.planners;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import planLib.PlanAction;
import planLib.Goal;
import planLib.HeuristicFunc;
import planLib.Plannable;
import planLib.Planner;
import planLib.Predicate;
import planLib.SimpleGoal;
import planLib.State;

/**
 * <p> Generic STRIPS algorithm </p>
 * @see <a href="https://en.wikipedia.org/wiki/STRIPS">STRIPS</a>
 * @author Elad Ben Zaken
 *
 */
public class Strips implements Planner {
	// Data members
	private static final int limit = 2048;
	private Stack<Object> stack = null;
	private Plannable plannable = null;
	private State knowledgeBase = null;
	private List<PlanAction> plan;
	private HeuristicFunc heuristic = null;

	@Override
	public List<PlanAction> computePlan(Plannable plannable, HeuristicFunc heuristic) {
		this.heuristic = heuristic;
		plan = new ArrayList<PlanAction>();
		this.plannable = plannable;
		knowledgeBase = plannable.getStartState();
		stack = new Stack<Object>();
		Goal goal = plannable.getGoal();
		stack.push(goal);

		while (!stack.isEmpty()) {

			if (stack.size() > limit)
				return null;

			Object obj = stack.peek();

			if (obj instanceof Goal) {
				Goal g = (Goal) obj;
				handleGoal(g);

			} else if (obj instanceof SimpleGoal) {
				SimpleGoal sg = (SimpleGoal) obj;
				if (!handleSimpleGoal(sg)) // can not handle that goal
					return null;

			} else if (obj instanceof PlanAction) {
				PlanAction action = (PlanAction) obj;
				handleAction(action);

			} else
				throw new IllegalArgumentException("Invalid object type");
		}
		return plan;
	}

	private void handleGoal(Goal goal) {
		if (knowledgeBase.satisfyingTheGoal(goal))
			stack.pop();
		else {
			List<SimpleGoal> subgoals = goal.splitToSubGoals();
			for (SimpleGoal s : subgoals)
				stack.push(s);
		}
	}

	private boolean handleSimpleGoal(SimpleGoal sg) {
		Predicate p = sg.getPredicate();
		stack.pop();
		boolean flag = true; // found an assignment
		if (!knowledgeBase.isSatisfied(p)) {
			flag = false;
			for (PlanAction a : plannable.getAllActions()) {
				Map<String, Object> assignment = a.findAssignment(p, plannable.getObjects(), heuristic);
				if (assignment != null) {
					PlanAction newAction = a.instantiate(assignment);
					stack.push(newAction);
					for (Predicate cond : newAction.getPreconditions())
						stack.push(new SimpleGoal(cond));
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	private void handleAction(PlanAction action) { // double check if the
													// preconditions are
													// satisfied
		List<Predicate> didntSatisfied = new ArrayList<Predicate>();
		List<Predicate> preconditions = action.getPreconditions();

		for (Predicate p : preconditions)
			if (!knowledgeBase.isSatisfied(p))
				didntSatisfied.add(p);

		if (didntSatisfied.isEmpty()) {
			stack.pop();
			knowledgeBase.update(action);
			plan.add(action);
		} else {
			for (Predicate p : didntSatisfied)
				stack.push(new SimpleGoal(p));
		}
	}
}
