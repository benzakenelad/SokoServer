package controller.sokobancommands;

public class SaveLevelCommand extends SokobanCommand{
	
	@Override
	public void execute() { // invoke the matching receiver
		model.saveLevel(order);
	}

}
