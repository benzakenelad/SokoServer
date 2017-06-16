package planLib;

import java.util.ArrayList;
import java.util.List;

/**
 * </p>Represent a complex planning problem goal </p>
 * @author Elad Ben Zaken
 *
 */
public class Goal {
	private List<Predicate> predicates = new ArrayList<Predicate>();

	public List<Predicate> getPredicates() {

		return predicates;
	}

	public List<SimpleGoal> splitToSubGoals() {
		List<SimpleGoal> subgoals = new ArrayList<>();
		for (Predicate p : predicates) {
			subgoals.add(new SimpleGoal(p));
		}
		return subgoals;
	}

}
