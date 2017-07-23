package model.data;

import model.policy.Policy;

public class Move1Step implements Move{
	
	public void Action(Level lvl, Policy policy, String direction) throws Exception // move the character left/up/right/down
	{
		if(lvl == null)
			throw new Exception("Can not move, There is no loaded level.");

		if(direction == null)
			throw new Exception("Illegal Move.");
		
		Direction dir = null;
		if(direction.compareTo("left") == 0 || direction.compareTo("up") == 0 || direction.compareTo("right") == 0 || direction.compareTo("down") == 0)
			dir = Direction.valueOf(direction);		
		else
			throw new Exception("Illegal Move.");
		
		
		
		switch(dir){
		case left: 
			policy.check(lvl, dir);
			break;
		case up:
			policy.check(lvl, dir);
			break;
		case right:
			policy.check(lvl, dir);
			break;
		case down:
			policy.check(lvl, dir);
			break;
		default: throw new Exception("Illegal Move.");
		}
	}
}
