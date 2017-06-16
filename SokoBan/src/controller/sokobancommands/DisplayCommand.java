package controller.sokobancommands;

public class DisplayCommand extends SokobanCommand {
    
	public void execute() { // invoke the matching receiver
		view.Display(model.getLvl());
	}

}
