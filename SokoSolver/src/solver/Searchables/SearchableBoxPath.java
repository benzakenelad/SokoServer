package solver.Searchables;

import java.util.HashMap;

import model.data.gameObjects.Position;
import searchLib.Action;
import searchLib.State;


// calculates the shortest path to move box legally to position
public class SearchableBoxPath extends CommonSearchable {

	
	@Override
	public HashMap<Action, State<Position>> getAllPossibleStates(State<Position> s) {
		HashMap<Action, State<Position>> hm = new HashMap<Action, State<Position>>();
		int currentRow = s.getObj().getRow();
		int currentCol = s.getObj().getCol();
		
		if (((levelData[currentRow - 1][currentCol] == ' ') || (levelData[currentRow - 1][currentCol] == 'o')) && ((levelData[currentRow + 1][currentCol] == ' ') || (levelData[currentRow + 1][currentCol] == 'o'))) {
			Position pos1 = new Position(currentRow - 1, currentCol);
			Position pos2 = new Position(currentRow + 1, currentCol);
			hm.put(new Action("move up"),new State<Position>(pos1));
			hm.put(new Action("move down"),new State<Position>(pos2));
		}
		if (((levelData[currentRow][currentCol + 1] == ' ') || (levelData[currentRow][currentCol + 1] == 'o')) && ((levelData[currentRow][currentCol - 1] == ' ') || (levelData[currentRow][currentCol - 1] == 'o'))) {
			Position pos1 = new Position(currentRow, currentCol + 1);
			Position pos2 = new Position(currentRow, currentCol - 1);
			hm.put(new Action("move right"),new State<Position>(pos1));
			hm.put(new Action("move left"),new State<Position>(pos2));
		}		
		return hm;
	}

}
