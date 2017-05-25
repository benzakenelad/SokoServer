package searchLib.searchers;

import java.util.LinkedList;

import searchLib.Action;
import searchLib.Searchable;
import searchLib.Searcher;
import searchLib.Solution;
import searchLib.State;

public abstract class CommonSearcher <T> implements Searcher<T> {

	private int evaluatedNodes = 0;
	
	@Override
	public abstract Solution search(Searchable<T> searchable);
	
	@Override
	public int getNumberOfNodesEvaluated(){
		return evaluatedNodes;
	}

	protected Solution backTrace(State<T> goalState) {
		LinkedList<Action> actions = new LinkedList<Action>();
		
		State<T> currState = goalState;
		while (currState.getCameFrom() != null) {			
			actions.addFirst(currState.getAction());
			currState = currState.getCameFrom();
			this.evaluatedNodes++;
		}
		
		Solution sol = new Solution();
		sol.setActions(actions);
		return sol;
	}
}
