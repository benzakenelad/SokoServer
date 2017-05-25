package planLib;

import java.util.List;

public interface Planner {
	List<PlanAction> computePlan(Plannable plannable);
}
