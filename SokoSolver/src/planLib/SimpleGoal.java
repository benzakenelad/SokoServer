package planLib;

/**
 * </p>Represent a not complex planning problem goal </p>
 * @author Elad Ben Zaken
 *
 */
public class SimpleGoal {
	private Predicate predicate;

	public Predicate getPredicate() {
		return predicate;
	}
	
	public SimpleGoal(Predicate p) {
		this.predicate = p;
	}	
	
	@Override
	public String toString() {
		return super.toString();
	}
}
