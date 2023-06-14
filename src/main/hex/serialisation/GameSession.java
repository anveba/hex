package main.hex.serialisation;

import main.hex.*;
import main.hex.logic.GameCustomisation;
import main.hex.logic.GameLogic;

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
