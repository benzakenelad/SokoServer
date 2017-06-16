package model.levelLoaders;

import java.io.InputStream;

import model.data.Level;

/**
 <h3>javadoc LevelLoader:</h3>
 
 <p>
 1) The separation was made in accordance with the principle of single responsibility (SOLID). We did this by creating the interface LevelLoader that load the information for Level class.
 <p>
 2) Because if another programmer wants to add loads of other file type we can do this by creating a class that implements the LevelLoader interface and he doesn't need to change existing code.
 <p>
 3) If the user wants to load any type of level file he can do this by injecting the file of the same type to the appropriate level loading class and that's how he can choose the file type in run time.
 <p>
 4) In order to guarantee the opening of file responsibility to the operator of LevelLoader.
 */

public interface LevelLoader {
	public Level loadLevel(InputStream in) throws Exception;
}
