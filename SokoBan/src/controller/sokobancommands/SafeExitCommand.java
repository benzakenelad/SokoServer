	package controller.sokobancommands;

import controller.SokobanController;

public class SafeExitCommand extends SokobanCommand {
	private SokobanController sokobanController = null;
	
	public SafeExitCommand(SokobanController sokobanController) {
		this.sokobanController = sokobanController;
	}
	
	@Override
	public void execute() {
		if(sokobanController != null)
			sokobanController.exit();
	}

}
