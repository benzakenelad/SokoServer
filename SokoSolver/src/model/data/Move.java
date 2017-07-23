package model.data;

import model.policy.Policy;

public interface Move {
	public void Action(Level lvl, Policy policy, String direction) throws Exception;
}
