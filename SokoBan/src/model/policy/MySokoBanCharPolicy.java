package model.policy;

import model.data.Direction;
import model.data.gameObjects.Position;

public class MySokoBanCharPolicy {

	
	boolean moveIsLegal(char[][] levelData, Direction direction) throws Exception
	{
		Position source = new Position();
		Position dest = new Position();
		Position afterDest = new Position();
		
		for(int i = 0; i < levelData.length; i++) // finding the Player
			for(int j = 0; j < levelData[i].length; j++)
				if(levelData[i][j] == 'A' || levelData[i][j] == 'B')
				{
					source.setRow(i);
					source.setCol(j);
				}
			
		dest = PositionCalculator(source, direction);
		afterDest = PositionCalculator(dest, direction);
		
		if(levelData[dest.getRow()][dest.getCol()] == '#')
			return false;
		
		if(levelData[dest.getRow()][dest.getCol()] == '@' || levelData[dest.getRow()][dest.getCol()] == '$')
			if(levelData[afterDest.getRow()][afterDest.getCol()] == '@' || levelData[afterDest.getRow()][afterDest.getCol()] == '$' || levelData[afterDest.getRow()][afterDest.getCol()] == '#')
				return false;
		
		return true;
	}
	

	public Position PositionCalculator(Position source, Direction dir) throws Exception 
	{
		switch(dir){
		case left:
			return new Position(source.getCol(), source.getCol() - 1);
		case up:
			return new Position(source.getCol() - 1, source.getCol());
		case right:
			return new Position(source.getCol(), source.getCol() + 1);
		case down:
			return new Position(source.getCol() + 1, source.getCol());
		default: throw new Exception("Invaild Direction");
		
		}
	}
	

}
