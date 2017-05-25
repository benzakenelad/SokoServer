package searchLib;

import java.util.LinkedList;
import java.util.List;

public class Solution {
	private LinkedList<Action> actions = new LinkedList<Action>();

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(LinkedList<Action> actions) {
		this.actions = actions;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (Action a : actions) {
			sb.append(a.getName()).append("\n");
		}
		
		return sb.toString();
	}
}
