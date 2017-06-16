package controller.sokobancommands;

public class QuickSolveCommand extends SokobanCommand {

	@Override
	public void execute() {
		model.quickSolve();
	}

}
