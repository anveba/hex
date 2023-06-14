package main.hex.serialisation;

import main.hex.*;

/**
 * Represents a game session, that is, a match.
 * @author Andreas - s214971
 *
 */
public class GameSession {

	public final GameCustomisation customisation;
	public final GameLogic gameLogic;
	
	public GameSession(GameCustomisation custom, GameLogic logic) {
		this.customisation = custom;
		this.gameLogic = logic;
	}
}
