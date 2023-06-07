package main.hex.serialisation;

import com.google.gson.*;

public class GameStateSerialiser {

	public GameStateSerialiser() {
		
	}
	
	public String gameStateToJson(GameState state) {
		return new Gson().toJson(state);
	}
	
	public GameState gameStateFromJson(String json) {
		return new Gson().fromJson(json, GameState.class);
	}
}
