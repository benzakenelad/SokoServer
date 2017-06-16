package model.data.gameObjects;

import java.io.Serializable;

public class Wall extends GameObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Wall(Position pos) {
		super(pos);
	}

	@Override
	public String toString() {
		return "#";
	}

	@Override
	public GameObject clone() throws CloneNotSupportedException {
		return new Wall(new Position(pos));
	}
}
