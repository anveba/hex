package main.hex.ai;

import main.hex.Move;
import main.hex.Player;
import main.hex.board.Board;

public abstract class AI {

	private Board board;
	private Player player;
	
	public AI(Board board, Player player) {
		if (board == null || player == null)
			throw new AIException("null given.");
		this.board = board.clone();
		this.player = player;
	}
	
	public abstract Move getBestMove(int depth);
	
	
	Board getBoard() {
		return board;
	}
	
	Player getPlayer() {
		return player;
	}
}
