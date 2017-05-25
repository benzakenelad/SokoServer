package planLib;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import planLib.PlanAction;
import planLib.Predicate;

public class State {
	private Set<Predicate> predicates = new HashSet<>();

	public Set<Predicate> getPredicates() {
		return predicates;
	}
	
	public void addPredicate(Predicate p) {
		predicates.add(p);
	}
	
	public void deletePredicate(Predicate p) {
		predicates.remove(p);
	}
	
	public boolean isSatisfied(Predicate p) {
		return predicates.contains(p);
	}
	
	public boolean satisfyingTheGoal(Goal goal){
		for(Predicate p: goal.getPredicates())
			if(!isSatisfied(p))
				return false;
		return true;
	}
	
	public void update(PlanAction action){
		List<Predicate> deleteList = action.getDeleteList();
		List<Predicate> addList = action.getAddList();
		
		for (Predicate p : addList) 
			this.addPredicate(p);
		
		for (Predicate p : deleteList) 
			this.deletePredicate(p);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Predicate p: predicates){
			sb.append(p);
			sb.append("  ");
		}
		return sb.toString();
	}
}
