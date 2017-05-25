package solver.Searchables;

import model.data.Level;
import model.data.gameObjects.Position;
import searchLib.Searchable;
import searchLib.State;

public abstract class CommonSearchable implements Searchable<Position> {

	protected char[][] levelData = null;
	protected Position startPosition = null;
	protected Position goalPosition = null;
	private boolean initialized = false;

	public void initialize(Level lvl, Position startPosition, Position goalPosition) {
		this.levelData = lvl.getLevelByChar2DArray();
		this.startPosition = startPosition;
		this.goalPosition = goalPosition;
		levelData[startPosition.getRow()][startPosition.getCol()] = ' ';
		initialized = true;
	}

	@Override
	public State<Position> getInitialState() throws ExceptionInInitializerError {
		if (initialized)
			return new State<Position>(startPosition);
		else
			throw new ExceptionInInitializerError("Not initialized searchable");
	}

	@Override
	public State<Position> getGoalState() throws ExceptionInInitializerError {
		if (initialized)
			return new State<Position>(goalPosition);
		else
			throw new ExceptionInInitializerError("Not initialized searchable");
	}

}
