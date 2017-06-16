package model.policy;

import model.data.Direction;
import model.data.Level;
import model.data.gameObjects.Position;

public abstract class Policy {
	public abstract void check(Level lvl, Direction dir) throws Exception;
	
	
	
	public Position PositionCalculator(Position source, Direction dir) throws Exception 
	{
		switch(dir){
		case left:
			return new Position(source.getRow(), source.getCol() - 1);
		case up:
			return new Position(source.getRow() - 1, source.getCol());
		case right:
			return new Position(source.getRow(), source.getCol() + 1);
		case down:
			return new Position(source.getRow() + 1, source.getCol());
		default: throw new Exception("Invaild Direction");
		
		}
	}
	
}
