package solver.moveBox;

import java.util.List;

import model.data.Level;
import model.data.Move;
import model.data.gameObjects.Box;
import model.data.gameObjects.Position;
import model.policy.Policy;
import searchLib.Action;
import searchLib.Solution;
import searchLib.searchers.BFS;
import solver.Searchables.CommonSearchable;

public class MoveBox1Step implements MoveBox{
	private Policy policy;
	private Move move;
	private CommonSearchable searchablePlayerPath;
	
	public MoveBox1Step(CommonSearchable searchablePlayerPath, Policy policy, Move move) {
		this.searchablePlayerPath = searchablePlayerPath;
		this.policy = policy;
		this.move = move;
	}
	
	@Override	
	public List<Action> move(Level lvl, Box box, String order) throws Exception{
		List<Action> actionList = null;
		Position boxPos = box.getPos(); 
		Position playerPosition = lvl.getPlayer().getPos();
		
		Position readyToPushPosition = calculatePushPosition(order, boxPos); // calculate the position the the player need to be at to push the box in the right direction
			
		searchablePlayerPath.initialize(lvl, playerPosition, readyToPushPosition);
		BFS<Position> searcher = new BFS<Position>();
		Solution solution = searcher.search(searchablePlayerPath);
		
		if(solution == null)
			return null;
		
		actionList = solution.getActions();
		actionList.add(new Action(order));
		
		for(Action a: actionList){
			String[] strs = a.getName().split(" ");
			move.Action(lvl, policy, strs[1]);
		}
		
		return actionList;
	}

	private Position calculatePushPosition(String order, Position boxPos) throws IllegalArgumentException{
		int boxRow = boxPos.getRow();
		int boxCol = boxPos.getCol();
		Position tempPosition = null;
		
		if(order.equals("move up")){
			tempPosition = new Position(boxRow + 1, boxCol);
		}
		else if(order.equals("move right")){
			tempPosition = new Position(boxRow, boxCol - 1);
		}	
		else if(order.equals("move down")){
			tempPosition = new Position(boxRow - 1, boxCol);
		}
		else if(order.equals("move left")){
			tempPosition = new Position(boxRow, boxCol + 1);
		}else
			throw new IllegalArgumentException("illegal order");
		
		return tempPosition;
	}

}
