package model.levelSavers;

import java.io.OutputStream;

import model.data.Level;

public interface LevelSaver {
	public void saveLevel(OutputStream out, Level lvl) throws Exception;
}
