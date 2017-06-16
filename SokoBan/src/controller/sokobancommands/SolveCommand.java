package controller.sokobancommands;

public class SolveCommand extends SokobanCommand {

	@Override
	public void execute() {
		model.solve();
	}

}
