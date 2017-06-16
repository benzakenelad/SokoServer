package controller.sokobancommands;

public class ShowMenuCommand extends SokobanCommand {

	// for Command line interface utilization
	@Override
	public void execute() {
		System.out.println("Type 'menu' To see the menu.");
		System.out.println("Type 'load FileName'(include the suffix) To load a level.");
		System.out.println("Type 'save FileName'(include the suffix) To save a level.");
		System.out.println("Type 'move left/up/right/down' to move your character.");
		System.out.println("Type 'display' To display the level.");
		System.out.println("Type 'exit' To exit the game.");
	}

}
