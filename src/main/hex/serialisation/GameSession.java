package main.hex.serialisation;

import main.hex.*;

public class GameSession {

	public final GameCustomisation customisation;
	public final GameLogic gameLogic;
	
	public GameSession(GameCustomisation custom, GameLogic logic) {
		this.customisation = custom;
		this.gameLogic = logic;
	}
}
