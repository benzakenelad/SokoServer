package view.TXT;

import java.util.ArrayList;

import model.data.Level;

public class TXTLevelDisplay implements LevelDisplay{

	@Override
	public void Display(Level lvl) {
		if(lvl == null)
			return;
		ArrayList<String> levelDataTXT = lvl.getLevelByArrayListOfStrings();
		for(String s: levelDataTXT)
			System.out.println(s);
	}

}
