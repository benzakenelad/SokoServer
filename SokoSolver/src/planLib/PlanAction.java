package planLib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * <p> A generic planning algorithm actions </p>
 * @author Elad Ben Zaken
 *
 */
public class PlanAction {
	// Data members
	protected String name;
	protected List<String> args = new ArrayList<>();
	protected List<Predicate> preconditions = new ArrayList<>();
	protected List<Predicate> addList = new ArrayList<>(); // effects
	protected List<Predicate> deleteList = new ArrayList<>();

	protected Map<String, List<Object>> illegalAssignments = new HashMap<>();

	// Methods
	private Map<String, Object> findMatch(Predicate p, Predicate effect) {
		if (!p.getName().equals(effect.getName()))
			return null;

		Map<String, Object> assignment = new HashMap<>();

		for (int i = 0; i < effect.getArgs().length; i++) {
			String variable = (String) effect.getArgs()[i];
			List<Object> list = illegalAssignments.get(variable);
			if (list != null)
				if (list.contains(p.getArgs()[i]))
					return null;
			assignment.put(variable, p.getArgs()[i]);
		}
		return assignment;
	}

	public Map<String, Object> findAssignment(Predicate p, List<Object> objects, HeuristicFunc heuristic) {
		for (Predicate effect : addList) {
			Map<String, Object> assignment = findMatch(p, effect);
			if (assignment != null) {
				if(!addMissingVariables(assignment, objects, heuristic))
					continue;
				else
					return assignment;
			}
		}
		return null;
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

				int idx = rand.nextInt(legalObjects.size());
				Object obj = legalObjects.get(idx);

				assignment.put(arg, legalObjects.get(idx));
				unusedObjects.remove(obj);
				// addition
				objects.remove(obj);
			}
		}
		return true;
	}

	public PlanAction instantiate(Map<String, Object> assignment) {
		PlanAction newAction = new PlanAction();
		newAction.name = this.name;

		for (Predicate p : this.preconditions) {
			newAction.preconditions.add(p.instantiate(assignment));
		}
		for (Predicate p : this.addList) {
			newAction.addList.add(p.instantiate(assignment));
		}
		for (Predicate p : this.deleteList) {
			newAction.deleteList.add(p.instantiate(assignment));
		}
		for (String arg : args) {
			newAction.args.add(assignment.get(arg).toString());
		}
		return newAction;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append(name);
		sb.append("(");
		for (String s : args) {
			sb.append(s);
			sb.append(",");
		}
		sb.append(")");
		return sb.toString();
	}

	// Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getArgs() {
		return args;
	}

	public List<Predicate> getPreconditions() {
		return preconditions;
	}

	public List<Predicate> getAddList() {
		return addList;
	}

	public List<Predicate> getDeleteList() {
		return deleteList;
	}

	public Map<String, List<Object>> getIllegalAssignments() {
		return illegalAssignments;
	}

}
