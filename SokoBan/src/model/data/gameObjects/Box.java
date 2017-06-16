package model.data.gameObjects;

import java.io.Serializable;

public class Box extends GameObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean onTarget = false;

	public Box(Position pos) {
		super(pos);
	}
	
	@Override
	public String toString() {
		return "@";
	}

	public boolean isOnTarget() {
		return onTarget;
	}

	public void setOnTarget(boolean onTarget) {
		this.onTarget = onTarget;
	}

	@Override
	public GameObject clone() throws CloneNotSupportedException {
		Box b = new Box(new Position(pos));
		b.onTarget = onTarget;
		
		return b;
	}

}
