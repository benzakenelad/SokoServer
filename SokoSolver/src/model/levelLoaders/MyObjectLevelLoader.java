package model.levelLoaders;

import java.io.InputStream;
import java.io.ObjectInputStream;

import model.data.Level;

public class MyObjectLevelLoader implements LevelLoader {

	@Override
	public Level loadLevel(InputStream in) throws Exception {
		Level lvl = null;		
		
		ObjectInputStream input = new ObjectInputStream(in);
		try {
			lvl = (Level)input.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		input.close();
		in.close();		
		
		return lvl;
	}

}
