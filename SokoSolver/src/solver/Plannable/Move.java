package solver.Plannable;

import java.util.ArrayList;
import java.util.List;

import planLib.PlanAction;
import planLib.Predicate;

public class Move extends PlanAction {
	public Move() {		
		name = "Move";	
		args.add("x");
		args.add("y");
		preconditions.add(new Predicate("OnFloor", "x"));
		preconditions.add(new Predicate("Clear", "y"));
		addList.add(new Predicate("On", "y"));
		deleteList.add(new Predicate("OnFloor", "x"));
		deleteList.add(new Predicate("Clear", "y"));
		
		List<Object> values1 = new ArrayList<Object>();
		values1.add("T1");
		values1.add("T2");
		values1.add("T3");
		values1.add("T4");
		illegalAssignments.put("x", values1);	
		
		List<Object> values2 = new ArrayList<Object>();
		values2.add("B1");
		values2.add("B2");
		values2.add("B3");
		values2.add("B4");
		values2.add("B5");
		values2.add("B6");
		illegalAssignments.put("y", values2);
	}
}
