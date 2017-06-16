package server;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import model.data.Level;
import searchLib.Action;
import solver.SolveSokobanLevel;

/**
 * <p> Sokoban solving server client handler.<br>Solving a Sokoban level </p>

 * @author Elad Ben Zaken
 *
 */
public class SokobanClientHandler implements ClientHandler {

	static final int times = 10;

	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) throws Exception {
		ObjectInputStream objectReader = new ObjectInputStream(inFromClient);
		Level lvl = (Level) objectReader.readObject();
		PrintWriter writeToClient = new PrintWriter(outToClient, true);

		// TEST

		ArrayList<String> list = lvl.getLevelByArrayListOfStrings();
		
		
		for (int i = 0; i < list.size(); i++) {
			writeToClient.println(list.get(i));
		}

		// END OF TEST
		
		SolveSokobanLevel solver = new SolveSokobanLevel();
		List<Action> actions = solver.optimalSolve(lvl, times);

		StringBuilder str = new StringBuilder("");
		actions.forEach((a) -> str.append(a.getName().split(" ")[1].charAt(0)));

		writeToClient.println(str.toString());

		objectReader.close();
		writeToClient.close();
	}

}
