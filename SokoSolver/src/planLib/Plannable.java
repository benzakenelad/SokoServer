package planLib;
import java.util.List;

public interface Plannable {
	List<PlanAction> getAllActions();
	State getStartState();
	Goal getGoal();
	void setGoal(Goal goal);
	List<Object> getObjects();
}
