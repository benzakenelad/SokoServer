package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import model.data.Level;
import searchLib.Action;
import solver.SolveSokobanLevel;

public class Solver {

	private ExecutorService executor = Executors.newCachedThreadPool();
	private SolveSokobanLevel solver = new SolveSokobanLevel();
	

	public String optimalSolve(Level lvl) throws Exception {
		if (lvl == null)
			return null;

		final List<Action> actions = MultiThreadedSolver(lvl, 50, 50);

		if (actions == null) {
			System.out.println("Could not solve the level.");
			return null;
		}

		return generateSolution(actions);
	}

	public String quickSolve(Level lvl) throws Exception {
		if (lvl == null)
			return null;

		List<Action> actions = solver.noneOptimalSolve(lvl);

		if (actions == null) {
			System.out.println("Could not solve the level.");
			return null;
		}

		return generateSolution(actions);
	}

	private List<Action> MultiThreadedSolver(Level lvl, int threadsNum, int testSize) throws Exception {
		if (lvl == null)
			return null;
		// Future product (futures)
		ArrayList<Future<List<Action>>> futures = new ArrayList<Future<List<Action>>>();

		// asynchronous solving
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

	private String generateSolution(List<Action> actions) throws Exception {
		if (actions == null)
			return null;
		StringBuilder builder = new StringBuilder("");
		actions.forEach((a) -> builder.append(a.getName().split(" ")[1].charAt(0)));

		return builder.toString();
	}
}
