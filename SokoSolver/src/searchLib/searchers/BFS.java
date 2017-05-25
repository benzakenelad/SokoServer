package searchLib.searchers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import searchLib.Action;
import searchLib.Searchable;
import searchLib.Solution;
import searchLib.State;

public class BFS<T> extends CommonSearcher<T> {

	@Override
	public Solution search(Searchable<T> searchable) {
		HashSet<State<T>> visited = new HashSet<>(); // all the state that we already visit

		Queue<State<T>> queue = new LinkedList<State<T>>();

		State<T> state = searchable.getInitialState();
		queue.add(state);
		State<T> goalState = searchable.getGoalState();

		while (!queue.isEmpty()) {
			State<T> currState = queue.poll(); // Current state

			if (currState.equals(goalState))
				return this.backTrace(currState);

			if (!visited.contains(currState)) { // checks if its the goal state
				visited.add(currState);

				HashMap<Action, State<T>> hm = searchable.getAllPossibleStates(currState);

				for (Action a : hm.keySet()) { // for each possible state from current state
					State<T> s = hm.get(a);

					if (!visited.contains(s)) { // if it's on the visited list we can't approve the cost
					if (!queue.contains(s)) { // if the queue contains this state we can't approve the cost
							s.setCost(currState.getCost() + 1);
							s.setAction(a);
							s.setCameFrom(currState);
							queue.add(s);
						}
					}
				}

			}
				

		}

		return null;
	}

}
