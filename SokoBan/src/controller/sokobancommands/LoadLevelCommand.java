package controller.sokobancommands;

public class LoadLevelCommand extends SokobanCommand {

	@Override
	public void execute() { // invoke the matching receiver
		model.loadLevel(order);
	}
}
