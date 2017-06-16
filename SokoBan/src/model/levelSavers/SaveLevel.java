package model.levelSavers;

import java.io.FileOutputStream;
import java.util.HashMap;

import model.data.Level;

public class SaveLevel {
	private HashMap<String, LevelSaver> hm = new HashMap<String, LevelSaver>();
	
	public SaveLevel() {
		hm.put("txt", new MyTextLevelSaver());
		hm.put("obj", new MyObjectLevelSaver());
		hm.put("xml", new MyXMLLevelSaver());
	}
	public void Action(Level lvl, String fileName) throws Exception
	{
		if(lvl == null)
			throw new Exception("There is no level loaded, can not save empty level.");
			
		
		String fileNameExtension = null;
		
		int ind = fileName.indexOf('.', 0);
		if(ind == -1)
			throw new Exception("Could not save the Level, Please include the filename extension.");
		
		fileNameExtension = fileName.substring(ind + 1, fileName.length()); // copy the filename extension into fileNameExtension
		
		LevelSaver ls = null;
		ls = hm.get(fileNameExtension);
		
		if(ls != null) // invalid filename Extension
		{
			ls.saveLevel(new FileOutputStream(fileName), lvl);
			throw new Exception("The level has been successfully saved.");
		}
		else
			throw new Exception("Illegel filename extension.");
		
	}
}
