package solver.moveBox;

import java.util.List;

import model.data.Level;
import model.data.gameObjects.Box;
import searchLib.Action;

public interface MoveBox {	
	List<Action> move(Level lvl, Box box, String order) throws Exception;
}
