package planLib;

/**
 * <p> Heuristic function interface is comfortable with generic planing algorithm </p>
 * @author Elad Ben Zaken
 *
 */
public interface HeuristicFunc {
	boolean heuristic(Object ... objects);
}
