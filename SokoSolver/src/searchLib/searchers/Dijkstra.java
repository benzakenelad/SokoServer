package searchLib.searchers;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import searchLib.Action;
import searchLib.Searchable;
import searchLib.Solution;
import searchLib.State;

public class Dijkstra<T> extends CommonSearcher<T> {

	@Override
	public Solution search(Searchable<T> searchable) {
		HashSet<State<T>> visited = new HashSet<>(); // all the state that we already visit

		PriorityQueue<State<T>> queue = new PriorityQueue<>(new Comparator<State<T>>() { // priority queue that compare by cost
			@Override
			public int compare(State<T> o1, State<T> o2) {
				if (o1.getCost() == o2.getCost())
					return 0;
				if (o1.getCost() - o2.getCost() > 0)
					return 1;
				else
					return -1;
			}
		});

		State<T> initialState = searchable.getInitialState();
		queue.add(initialState);
		State<T> goalState = searchable.getGoalState();

		while (!queue.isEmpty()) {
			State<T> currState = queue.poll(); // Current state

			if (currState.equals(goalState)) // checks if its the goal state
				return this.backTrace(currState);

			if (!visited.contains(currState)) {
				visited.add(currState);

				HashMap<Action, State<T>> hm = searchable.getAllPossibleStates(currState);

				for (Action action : hm.keySet()) { // for each possible state from current state
					State<T> state = hm.get(action);

					if (!visited.contains(state)) { // if it's on the visited list we can't approve the cost
						if (!queue.contains(state)) { // in case we didn't visit that state before we can directly update and add the state to the priority queue
							state.setCost(currState.getCost() + state.getCost());
							state.setAction(action);
							state.setCameFrom(currState);
							queue.add(state);
						} else {  // in case we visited this state and he is not on the visited list, (maybe we can approve the cost)
							State<T> oldState = null;
							for(State<T> s: queue){ // find the old state
								if(s.equals(state)){
									oldState = s;
									break;
								}
							}
							if(currState.getCost() + state.getCost() < oldState.getCost()){  // compare the old state to the new state
								// in case we found a lower cost to the state, we update the state cost
								queue.remove(oldState);
								state.setCost(currState.getCost() + state.getCost());
								state.setAction(action);
								state.setCameFrom(currState);
								queue.add(state);
							}
								
						}
					}
				}
			}
		}
		return null;
	}

}
