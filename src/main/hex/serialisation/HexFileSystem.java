package main.hex.serialisation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import main.hex.*;
import net.harawata.appdirs.*;

/**
 * Singleton that manages any persistent data that has to be
 * saved and loaded from execution to execution.
 * @author andreas
 *
 */
public class HexFileSystem {

	private static HexFileSystem instance;
	public static HexFileSystem getInstance() {
		if (instance == null)
			instance = new HexFileSystem();
		return instance;
	}
	
	private static final String savedGamesDir = "/saves/";
	private static final String tempSaveName = "previous.json";
	
	private GameStateSerialiser serialiser;
	
	private HexFileSystem() {
		serialiser = new GameStateSerialiser();
	}
	
	public void saveGame(GameSession session) {
		String json = serialiser.gameStateToJson(GameState.sessionToState(session));
		Path path = Paths.get(getPersistentDataPath() + savedGamesDir + tempSaveName);
		ensureDirectoryExists(path);
		try {
			Files.write(path, json.getBytes());
		} catch (IOException e) {
			throw new HexException("Could not save to file system: " + e.toString());
		}
	}
	
	public GameSession loadGame() {
		Path path = Paths.get(getPersistentDataPath() + savedGamesDir + tempSaveName);
		
		String json;
		try {
			json = Files.readString(path);
		} catch (IOException e) {
			throw new HexException("Could not read file: " + e.toString());
		}
		
		GameState state = serialiser.gameStateFromJson(json);
		
		return state.stateToSession();
	}
	
	private void ensureDirectoryExists(Path path) {
		path.getParent().toFile().mkdirs();
	}
	
	private String getPersistentDataPath() {
		AppDirs appDirs = AppDirsFactory.getInstance();
		return appDirs.getUserDataDir("hex", null, "group3");
	}
}
