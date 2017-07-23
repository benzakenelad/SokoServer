package model.levelLoaders;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.InputStream;

import model.data.Level;

public class MyXMLLevelLoader implements LevelLoader {

	@Override
	public Level loadLevel(InputStream in) throws Exception {
		BufferedInputStream bis = new BufferedInputStream(in);
		XMLDecoder input = new XMLDecoder(bis);
		
		Level lvl = (Level) input.readObject();
		
		in.close();
		input.close();
		
		return lvl;
	}

}
