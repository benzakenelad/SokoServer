package model.levelLoaders;

import java.io.FileInputStream;
import java.util.HashMap;

import model.data.Level;

public class LoadLevel{
	private HashMap<String, LevelLoader> hm = new HashMap<String, LevelLoader>();
	
	public LoadLevel() {
		hm.put("txt", new MyTextLevelLoader());
		hm.put("obj", new MyObjectLevelLoader());
		hm.put("xml", new MyXMLLevelLoader());
	}
	
	public Level Action(String fileName) throws Exception 
	{
		String s = null;
		int ind = fileName.indexOf('.', 0);
		if(ind == -1)
			throw new Exception("Could not load the desired Level, Please include the filename extension.");
		s = fileName.substring(ind + 1, fileName.length()); // find the file suffix
		
		Level lvl = null;
		LevelLoader ll = null;
		
		ll = hm.get(s);
		
		if(ll == null)
			throw new Exception("Illegel filename extension.");
		
		lvl = ll.loadLevel(new FileInputStream(fileName));
		
		if(lvl != null)
			return lvl;
		else
			throw new Exception("Could not find the file specified.");
	}
}
