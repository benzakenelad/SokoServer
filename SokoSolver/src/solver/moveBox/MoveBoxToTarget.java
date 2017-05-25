package solver.moveBox;

import java.util.ArrayList;
import java.util.List;

import model.data.Level;
import model.data.gameObjects.Box;
import model.data.gameObjects.Position;
import model.data.gameObjects.Target;
import searchLib.Action;
import searchLib.Solution;
import searchLib.searchers.BFS;
import solver.Searchables.CommonSearchable;

public class MoveBoxToTarget {

	private MoveBox moveBox;
	private CommonSearchable searchableBoxPath;

	public MoveBoxToTarget(CommonSearchable searchableBoxPath, MoveBox moveBox) {
		this.moveBox = moveBox;
		this.searchableBoxPath = searchableBoxPath;
	}

	public List<Action> move(Level lvl, Box box, Target target) throws Exception {
		List<Action> actionList = new ArrayList<Action>();
		List<Action> boxToTargetPath;

		searchableBoxPath.initialize(lvl, box.getPos(), target.getPos());
		BFS<Position> searcher = new BFS<Position>();
		Solution solution = searcher.search(searchableBoxPath);

		if (solution == null)
			return null;

		boxToTargetPath = solution.getActions();

		for (Action a : boxToTargetPath) {
			List<Action> moveList;
			moveList = moveBox.move(lvl, box, a.getName());
			if (moveList == null)
				return null;
			for (Action action : moveList)
				actionList.add(action);
		}

		return actionList;
	}

}
