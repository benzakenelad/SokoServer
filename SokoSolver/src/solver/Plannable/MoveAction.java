package solver.Plannable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import planLib.HeuristicFunc;
import planLib.PlanAction;
import planLib.Predicate;

public class MoveAction extends PlanAction {
	public MoveAction() {
		name = "Move";
		args.add("x");
		args.add("y");
		preconditions.add(new Predicate("OnFloor", "x"));
		preconditions.add(new Predicate("Clear", "y"));
		addList.add(new Predicate("On", "y")); // box cover the target
		deleteList.add(new Predicate("OnFloor", "x"));
		deleteList.add(new Predicate("Clear", "y"));
	}

	public boolean addMissingVariables(Map<String, Object> assignment, List<Object> objects, HeuristicFunc heuristic) {
		List<Object> unusedObjects = new ArrayList<Object>();
		unusedObjects.addAll(objects);
		unusedObjects.removeAll(assignment.values());
		Random rand = new Random();

		for (String arg : args) {
			if (!assignment.containsKey(arg)) {
				List<Object> legalObjects = new ArrayList<Object>();
				legalObjects.addAll(unusedObjects);
				List<Object> illegalAssignmentsForArg = illegalAssignments.get(arg);
				if (illegalAssignmentsForArg != null)
					legalObjects.removeAll(illegalAssignmentsForArg);

				while (true) {
					if (legalObjects.isEmpty())
						return false;
					int idx = rand.nextInt(legalObjects.size());
					Object obj = legalObjects.get(idx);
					if (heuristic != null)
						if (!heuristic.heuristic(obj, assignment.get("y"))) {
							legalObjects.remove(idx);
							continue;
						}
					assignment.put(arg, legalObjects.get(idx));
					unusedObjects.remove(obj);
					// addition
					objects.remove(obj);
					break;

				}
			}
		}
		return true;
	}
}
