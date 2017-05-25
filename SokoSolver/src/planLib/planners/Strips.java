package planLib.planners;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import planLib.PlanAction;
import planLib.Goal;
import planLib.Plannable;
import planLib.Planner;
import planLib.Predicate;
import planLib.SimpleGoal;
import planLib.State;

public class Strips implements Planner {

	@Override
	public List<PlanAction> computePlan(Plannable plannable) {
		List<PlanAction> plan = new ArrayList<PlanAction>();

		State knowledgeBase = plannable.getStartState();
		Stack<Object> stack = new Stack<Object>();
		Goal goal = plannable.getGoal();
		stack.push(goal);

		while (!stack.isEmpty()) {

			if (stack.size() > 2048)
				return null;

			Object obj = stack.pop();

			if (obj instanceof Goal) {

				Goal g = (Goal) obj;
				stack.push(g);
				if (knowledgeBase.satisfyingTheGoal(g))
					break;
				List<SimpleGoal> subgoals = g.splitToSubGoals();
				for (SimpleGoal s : subgoals) {
					stack.push(s);
				}
			} else if (obj instanceof SimpleGoal) {
				SimpleGoal sg = (SimpleGoal) obj;
				simpleGoalAct(plannable, knowledgeBase, stack, sg);
			} else if (obj instanceof PlanAction) {
				PlanAction action = (PlanAction) obj;
				
				if (isReadyToExecute(action, knowledgeBase, stack)) {
					knowledgeBase.update(action);
					plan.add(action);
				}

			} else
				throw new IllegalArgumentException("Invalid object type");

		}

		return plan;
	}

	private boolean isReadyToExecute(PlanAction action, State knowledgeBase, Stack<Object> stack) {
		boolean flag = true;

		List<Predicate> didntSatisfied = new ArrayList<Predicate>();
		List<Predicate> preconditions = action.getPreconditions();

		for (Predicate p : preconditions)
			if (!knowledgeBase.isSatisfied(p))
				didntSatisfied.add(p);

		if (!didntSatisfied.isEmpty()) {
			flag = false;
			stack.push(action);
			for (Predicate p : didntSatisfied)
				stack.push(new SimpleGoal(p));
		}
		return flag;
	}

	private void simpleGoalAct(Plannable plannable, State knowledgeBase, Stack<Object> stack, SimpleGoal sg) {
		Predicate p = sg.getPredicate();
		if (!knowledgeBase.isSatisfied(p)) {
			for (PlanAction a : plannable.getAllActions()) {
				Map<String, Object> assignment = a.findAssignment(p, plannable.getObjects());
				if (assignment != null) {
					PlanAction newAction = a.instantiate(assignment);
					stack.push(newAction);
					for (Predicate cond : newAction.getPreconditions()) {
						stack.push(new SimpleGoal(cond));
					}
				}
			}
		}
	}

}
