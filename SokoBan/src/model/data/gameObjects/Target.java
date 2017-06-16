package model.data.gameObjects;

import java.io.Serializable;

public class Target extends GameObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// data members
	private GameObject onMe = null;
	private boolean onMeflag = false;
	private boolean boxOnMeFlag = false;

	public Target(Position pos) {
		super(pos);
	}

	// getters and setters
	public void setOnMe(GameObject onMe)// set a game object on the target
	{

		this.onMe = onMe;

		if (this.onMe != null) {
			setOnMeFlag(true);

			if (this.onMe instanceof Box) {
				((Box) onMe).setOnTarget(true);
				boxOnMeFlag = true;
			}else
				boxOnMeFlag = false;
			

		} else // if new onMe is null
		{
			setBoxOnMeFlag(false);
			setOnMeFlag(false);
		}
	}

	public GameObject getOnMe() {
		return onMe;
	}

	public boolean gotGameObjectOnMe() {
		return onMeflag;
	}

	private void setOnMeFlag(boolean onMeflag) {
		this.onMeflag = onMeflag;
	}

	public boolean gotBoxOnMe() {
		return boxOnMeFlag;
	}

	private void setBoxOnMeFlag(boolean boxOnMeFlag) {
		this.boxOnMeFlag = boxOnMeFlag;
	}

	// methods
	@Override
	public String toString() {
		if (onMeflag == true) { // if there is an object on me
			if (onMe instanceof Character)
				return "B";
			else
				return "$";
		} else
			return "o";
	}

	@Override
	public GameObject clone() throws CloneNotSupportedException {
		Target t = new Target(new Position(pos));
		t.boxOnMeFlag = boxOnMeFlag;
		t.onMeflag = onMeflag;
		if (onMe != null)
			t.onMe = onMe.clone();

		return t;
	}

}
