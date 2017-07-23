package model.data.gameObjects;

import java.io.Serializable;

public class Character extends GameObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean onTarget = false;
	
	public Character(Position pos) {
		super(pos);
	}

	public boolean isOnTarget() {
		return onTarget;
	}

	public void setOnTarget(boolean onTarget) {
		this.onTarget = onTarget;
	}

	@Override
	public String toString() {
		return "A";
	}

	@Override
	public GameObject clone() throws CloneNotSupportedException {
		Character ch = new Character(new Position(pos));
		ch.onTarget = onTarget;
		
		return ch;
	}
}
