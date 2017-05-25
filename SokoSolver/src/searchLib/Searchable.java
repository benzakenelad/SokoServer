package searchLib;

import java.util.HashMap;

public interface Searchable <T> {
	public State<T> getInitialState();
	public State<T> getGoalState();
	public HashMap<Action,State<T>> getAllPossibleStates(State<T> s);
}
