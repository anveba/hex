package main.hex.serialisation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;

import main.engine.io.GameFileSystem;
import main.hex.*;
import net.harawata.appdirs.*;

/**
 * Singleton that manages any persistent data that has to be
 * saved and loaded from execution to execution.
 * @author andreas
 *
 */
public class HexFileSystem extends GameFileSystem {

	private static HexFileSystem instance;
	public static HexFileSystem getInstance() {
		if (instance == null)
			instance = new HexFileSystem();
		return instance;
	}
	
	private static final String savedGamesDir = "saves/";
	private static final String previousGameSaveName = "previous.json";
	private static final String preferencesFileName = "pref.json";
	
	private GameStateSerialiser serialiser;
	
	private HexFileSystem() {
		serialiser = new GameStateSerialiser();
	}
	
	public void saveGame(GameSession session) {
		String json = serialiser.gameStateToJson(GameState.sessionToState(session));
		Path path = Paths.get(getPersistentDataPath() + savedGamesDir + previousGameSaveName);
		ensureDirectoryExists(path);
		try {
			Files.write(path, json.getBytes());
		} catch (IOException e) {
			throw new HexException("Could not save to file system: " + e.toString());
		}
	}
	
	public GameSession loadGame() {
		Path path = Paths.get(getPersistentDataPath() + savedGamesDir + previousGameSaveName);
		
		String json;
		try {
			json = Files.readString(path);
		} catch (IOException e) {
			throw new HexException("Could not read file: " + e.toString());
		}
		
		GameState state = serialiser.gameStateFromJson(json);
		
		return state.stateToSession();
	}
	
	public void savePreferences(Preferences pref) {
		String json = new Gson().toJson(pref);
		Path path = Paths.get(getPersistentDataPath() + preferencesFileName);
		ensureDirectoryExists(path);
		try {
			Files.write(path, json.getBytes());
		} catch (IOException e) {
			throw new HexException("Could not save to file system: " + e.toString());
		}
	}
	
	public Preferences loadPreferences() {
		Path path = Paths.get(getPersistentDataPath() + preferencesFileName);
		
		String json;
		try {
			json = Files.readString(path);
		} catch (IOException e) {
			throw new HexException("Could not read file: " + e.toString());
		}
		return new Gson().fromJson(json, Preferences.class);
	}
	
	private void ensureDirectoryExists(Path path) {
		path.getParent().toFile().mkdirs();
	}

	@Override
	public String appName() {
		return "hex";
	}

	@Override
	public String devName() {
		return "group3";
	}
	
	
}
