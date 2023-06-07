package main.hex.serialisation;

import main.engine.math.Pair;
import main.hex.*;
import main.hex.board.Board;
import main.hex.board.TileColour;
import main.hex.player.*;

/**
 * Represents a snapshot of a game's state. All information to 
 * rebuild a game is contained.
 * This class is meant for serialisation.
 * @author andreas
 *
 */
public class GameState {
	
	public final PlayerState player1, player2;
	public final boolean swapRuleEnabled;
	public final GameStateChange[] history;
	public final int boardSize;
	public final int initialTimeLimit;
	
	private GameState(GameCustomisation custom, GameLogic logic) {
		if (custom == null || logic == null)
			throw new HexException("Null argument given");
		if (!logic.gameHasStarted())
			throw new HexException("Game hasn't started");
		
		swapRuleEnabled = custom.getSwapRule();
		history = logic.getHistory();
		boardSize = logic.getBoard().size();
		initialTimeLimit = custom.getInitialTimeLimit();
		
		player1 = generatePlayerStateFromPlayer(logic.getPlayer1(), 
				custom.getOriginalPlayer1Name(), custom.getOriginalPlayer1Skin());
		player2 = generatePlayerStateFromPlayer(logic.getPlayer2(), 
				custom.getOriginalPlayer2Name(), custom.getOriginalPlayer2Skin());
	}
	
	private PlayerState generatePlayerStateFromPlayer(Player player, String name, PlayerSkin skin) {
		PlayerType type;
		if (player instanceof AIPlayer)
			type = PlayerType.AI_EASY; //TODO, serialise difficulty.
		else if (player instanceof UserPlayer)
			type = PlayerType.HUMAN;
		else
			throw new HexException("Unrecognised player type");
		float time = (float)player.getTimer().getRemainingTime();
		return new PlayerState(type, name, skin, time);
	}
	
	public static GameState sessionToState(GameSession session) {
		return new GameState(session.customisation, session.gameLogic);
	}
	
	public GameSession stateToSession() {
		GameCustomisation custom = new GameCustomisation(player1.name, player2.name, 
				player1.skin, player2.skin, initialTimeLimit, swapRuleEnabled);
		Board board = new Board(boardSize);
		Player p1 = generatePlayerFromPlayerState(player1, TileColour.PLAYER1);
		Player p2 = generatePlayerFromPlayerState(player2, TileColour.PLAYER2);
		GameLogic logic = new GameLogic(board, p1, p2);
		logic.start();
		logic.makeStateChanges(history);
		return new GameSession(custom, logic);
	}
	
	public Player generatePlayerFromPlayerState(PlayerState state, TileColour col) {
		Player p;
		switch (state.type) {
		case HUMAN:
			p = new UserPlayer(col, state.timeRemaining);
			break;
		case AI_EASY:
		case AI_NORMAL:
		case AI_HARD:
			p = new AIPlayer(col, state.timeRemaining, 2); //TODO set depth properly
			break;
		default:
			throw new HexException("Unrecognised player type");
		}
		return p;
	}
}
