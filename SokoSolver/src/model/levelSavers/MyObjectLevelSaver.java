package model.levelSavers;

import java.io.ObjectOutputStream;
import java.io.OutputStream;

import model.data.Level;

public class MyObjectLevelSaver implements LevelSaver {

	@Override
	public void saveLevel(OutputStream out, Level lvl) throws Exception {
		ObjectOutputStream output = null;
		
		output = new ObjectOutputStream(out);
		
		output.writeObject(lvl);
		
		out.close();
		output.close();
	}

}
