package model.levelLoaders;

import java.io.InputStream;

import model.data.Level;


public interface LevelLoader {
	public Level loadLevel(InputStream in) throws Exception;
}
