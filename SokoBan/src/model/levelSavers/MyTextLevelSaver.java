package model.levelSavers;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import model.data.Level;

public class MyTextLevelSaver implements LevelSaver {

	@Override
	public void saveLevel(OutputStream out, Level lvl) throws Exception{
		ArrayList<String> levelDataTXT = lvl.getLevelByArrayListOfStrings();
		int size = levelDataTXT.size();
		
		BufferedWriter bw = new BufferedWriter(new PrintWriter(out));
		
		if(!lvl.getID().equals(""))
			bw.write(lvl.getID() + '\n');
		
		for(int i = 0; i < size - 1; i++)
			bw.write(levelDataTXT.get(i) + '\n');
		bw.write(levelDataTXT.get(size - 1));

		
		bw.close();
		out.close();
		
	}
}
