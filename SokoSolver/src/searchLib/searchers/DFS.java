package searchLib.searchers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import searchLib.Action;
import searchLib.Searchable;
import searchLib.Solution;
import searchLib.State;

public class DFS<T> extends CommonSearcher<T> {
	
	
	@Override
	public Solution search(Searchable<T> s) {
		HashSet<State<T>> visited = new HashSet<>(); 
		
		Stack<State<T>> stack = new Stack<>();
		State<T> state = s.getInitialState();
		stack.push(state);
		
		while (!stack.isEmpty()) {
			State<T> currState = stack.pop();
			
			if (currState.equals(s.getGoalState()))
				return backTrace(currState);
			
			if (!visited.contains(currState)) {
				visited.add(currState);
				
				HashMap<Action, State<T>> map = s.getAllPossibleStates(currState);							
				
				for (Action a : map.keySet()) {
					State<T> n = map.get(a);
					
					if (!visited.contains(n)) {
						stack.push(n);
						n.setCameFrom(currState);
						n.setAction(a);
					}					
				}
			}
		}
		return null;
	}

}
