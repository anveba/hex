package main.engine.io;

import main.engine.EngineException;
import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

/**
 * Represents and abstracts a file system for the game to use.
 * @author Andreas - s214971
 *
 */

public abstract class GameFileSystem {

	public GameFileSystem() {
		
	}
	
	protected String getPersistentDataPath() {
		final String appName = appName();
		if (appName == null || appName.isBlank())
			throw new EngineException("App name was empty");
		
		final String devName = devName();
		if (devName == null || devName.isBlank())
			throw new EngineException("Developer name was empty");
		
		AppDirs appDirs = AppDirsFactory.getInstance();
		var p = appDirs.getUserDataDir(appName, null, devName) + "/";
		System.out.println(p);
		return p;
	}
	
	public abstract String appName();
	
	public abstract String devName();
}
