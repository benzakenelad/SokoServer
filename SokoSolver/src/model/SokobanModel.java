package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;

import model.data.Level;
import model.data.Move;
import model.levelLoaders.LoadLevel;
import model.levelSavers.SaveLevel;
import model.policy.Policy;

public class SokobanModel extends Observable implements Model {

	// Data members
	private Level lvl = null;
	private static final int port = 5555;
	private static final String ip = "127.0.0.1";
	private static final int timeToWait = 15;

	
	public SokobanModel() {
		
	}
	
	// Methods implementation
	@Override
	public void move(Move move, Policy policy, String note) {
		try {
			move.Action(lvl, policy, note);
			this.setChanged();
			this.notifyObservers("display");

		} catch (Exception e) {
			/* e.printStackTrace(); */}
	}

	@Override
	public void loadLevel(String note) {
		Level tempLvl = null;
		try {
			tempLvl = new LoadLevel().Action(note);
			this.lvl = tempLvl;
			this.setChanged();
			this.notifyObservers("display");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void saveLevel(String note) {
		try {
			new SaveLevel().Action(lvl, note);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void close() {
		this.lvl = null;
	}

	@Override
	public void optimalSolve() {
		if (!serverSolving("optimal solving"))
			try {
				executeActions(new Solver().optimalSolve(this.lvl), timeToWait);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

	}

	@Override
	public void quickSolve() {
		if (!serverSolving("quick solving"))
			try {
				executeActions(new Solver().quickSolve(this.lvl), timeToWait);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

	}

	private boolean serverSolving(String note) {
		try {
			Socket connectionSocket = new Socket(ip, port);
			ObjectOutputStream objWriter = new ObjectOutputStream(connectionSocket.getOutputStream());
			objWriter.writeObject(this.lvl);
			PrintWriter txtWriter = new PrintWriter(connectionSocket.getOutputStream(), true);
			txtWriter.println(note);
			BufferedReader reader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			String actions = reader.readLine();
			executeActions(actions, timeToWait);
			connectionSocket.close();
			return true;
		} catch (Exception e) {
			System.out.println("Server is offline, solving with local solver.");
			return false;
		}
	}

	private void executeActions(String actions, int timeToWait) {
		if (actions == null)
			return;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					int len = actions.length();
					for (int i = 0; i < len; i++) {
						switch (actions.charAt(i)) {
						case 'u':
							setChanged();
							notifyObservers("move up");
							Thread.sleep(timeToWait);
							break;
						case 'r':
							setChanged();
							notifyObservers("move right");
							Thread.sleep(timeToWait);
							break;
						case 'd':
							setChanged();
							notifyObservers("move down");
							Thread.sleep(timeToWait);
							break;
						case 'l':
							setChanged();
							notifyObservers("move left");
							Thread.sleep(timeToWait);
							break;
						default:
							break;
						}
					}
				} catch (Exception e) {
				}
				
			}
		}).start();

	}

	// getters and setters
	public Level getLvl() {
		return lvl;
	}

	public void setLvl(Level lvl) {
		this.lvl = lvl;
	}

}
