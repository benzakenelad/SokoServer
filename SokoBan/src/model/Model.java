package model;

import model.data.Level;
import model.data.Move;
import model.policy.Policy;

public interface Model {
	public Level getLvl();
	public void setLvl(Level lvl);
	public void move(Move move, Policy policy, String note);
	public void loadLevel(String note);
	public void saveLevel(String note);
	public void solve();
	public void quickSolve();
	public void close();

}
