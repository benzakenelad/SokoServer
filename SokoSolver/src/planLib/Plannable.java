package planLib;
import java.util.List;

/**
 * <p> Plannable interface is comfortable with generic planing algorithm </p>
 * @author Elad Ben Zaken
 *
 */
public interface Plannable {
	List<PlanAction> getAllActions();
	State getStartState();
	Goal getGoal();
	void setGoal(Goal goal);
	List<Object> getObjects();
}
