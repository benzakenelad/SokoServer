package planLib;

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
		// TODO Auto-generated method stub
		return super.toString();
	}
}
