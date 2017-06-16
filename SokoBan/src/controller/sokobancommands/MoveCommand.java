	package controller.sokobancommands;

import model.data.Move1Step;
import model.policy.MySokobanPolicy;

public class MoveCommand extends SokobanCommand {
	
	public void execute() { // Invoke the matching movement (by the specified policy) receiver
		model.move(new Move1Step(), new MySokobanPolicy(), order);
	}
}
