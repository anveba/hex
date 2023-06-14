package main.hex.serialisation;

import com.google.gson.*;

/**
 * Responsible for serialising and deserialising the game state.
 * @author Andreas - s214971
 *
 */
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
