package planLib;

import java.util.List;

/**
 * <p> Generic planing algorithm interface - define the computePlan method </p> 
 * @author Elad Ben Zaken
 *
 */
public interface Planner {
	List<PlanAction> computePlan(Plannable plannable, HeuristicFunc heuristic);
}
