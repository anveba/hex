package main.hex;

/**
 * Represents a change in the game state made by some move by a player.
 * @author Andreas - s214971
 *
 */
public class GameStateChange {
	public final Move move;
	public final boolean swapRuleExecuted;
	
	public GameStateChange(Move move, boolean swapRuleExecuted) {
		this.move = move;
		this.swapRuleExecuted = swapRuleExecuted;
	}
}
