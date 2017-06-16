package model.levelSavers;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.OutputStream;

import model.data.Level;

public class MyXMLLevelSaver implements LevelSaver {

	@Override
	public void saveLevel(OutputStream out, Level lvl) throws Exception {
		
		
		BufferedOutputStream bos = new BufferedOutputStream(out);
		XMLEncoder output = new XMLEncoder(bos);
		
		output.writeObject(lvl);
		
		out.close();
		output.close();
	}

}
