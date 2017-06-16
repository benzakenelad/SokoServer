package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import model.data.Level;
import model.data.Move;
import model.levelLoaders.LoadLevel;
import model.levelSavers.SaveLevel;
import model.policy.Policy;
import searchLib.Action;
import solver.SolveSokobanLevel;

public class SokobanModel extends Observable implements Model {

	// Data members
	private Level lvl = null;
	private ExecutorService executor = Executors.newCachedThreadPool();
	private SolveSokobanLevel solver = new SolveSokobanLevel();

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
		executor.shutdown();
		this.lvl = null;
	}

	// getters and setters
	public Level getLvl() {
		return lvl;
	}

	public void setLvl(Level lvl) {
		this.lvl = lvl;
	}

	@Override
	public void solve() {
		if (lvl == null)
			return;

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					long begin = System.currentTimeMillis();
					final List<Action> actions = MultiThreadedSolver(50, 50);
					long end = System.currentTimeMillis();
					double time = (end - begin) / 1000;
					System.out.println("Solving time : " + time + " seconds");

					if (actions == null) {
						System.out.println("Could not solve the level.");
						return;
					}
					executeActions(actions);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void quickSolve() {
		if (lvl == null)
			return;

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					long begin = System.currentTimeMillis();
					List<Action> actions = solver.noneOptimalSolve(lvl);
					long end = System.currentTimeMillis();
					double time = (end - begin) / 1000;
					System.out.println("Solving time : " + time + " seconds");
					executeActions(actions);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private List<Action> MultiThreadedSolver(int threadsNum, int testSize)
			throws InterruptedException, ExecutionException, TimeoutException {
		if (lvl == null)
			return null;
		// Future product (futures)
		ArrayList<Future<List<Action>>> futures = new ArrayList<Future<List<Action>>>();

		for (int i = 0; i < threadsNum; i++) {
			futures.add(executor.submit(new Callable<List<Action>>() {
				@Override
				public List<Action> call() throws Exception {
					List<Action> actions = solver.optimalSolve(lvl, testSize);
					return actions;
				}
			}));
		}
		ArrayList<List<Action>> lists = new ArrayList<List<Action>>();

		for (int i = 0; i < threadsNum; i++)
			lists.add(futures.get(i).get());

		List<Action> returnedList = null;

		int largest = Integer.MAX_VALUE;

		for (int i = 0; i < threadsNum; i++) {
			if (lists.get(i) != null)
				if (lists.get(i).size() < largest) {
					largest = lists.get(i).size();
					returnedList = lists.get(i);
				}
		}
		return returnedList;
	}

	private void executeActions(List<Action> actions) throws InterruptedException {
		if (actions == null)
			return;
		for (Action action : actions) {
			setChanged();
			notifyObservers(action.getName());
			Thread.sleep(150);
		}
	}
}
