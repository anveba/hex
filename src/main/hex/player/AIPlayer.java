package main.hex.player;

import main.hex.HexException;
import main.hex.ai.*;
import main.hex.board.Board;
import main.hex.board.TileColour;

public class AIPlayer extends Player {

	private int searchDepth;
	
	public AIPlayer(TileColour playerColour, int searchDepth) {
		super(playerColour);
		setSearchDepth(searchDepth);
	}

	public AIPlayer(TileColour playerColour, float timeLimit, int searchDepth) {
		super(playerColour, timeLimit);
		setSearchDepth(searchDepth);
	}

	@Override
	public void processTurn(Board board, ConcurrentPlayerResponse response) {
		Thread t = new Thread(() -> response.placeMove(new AI(board, this).getBestMoveWithDepth(searchDepth)));
		t.setUncaughtExceptionHandler((th, ex) -> { response.setError(ex); });
		t.start();
	}

	@Override
	public void correctInvalidTurn(Board board, ConcurrentPlayerResponse response) {
		throw new AIException("AI made an invalid move.");
	}

	@Override
	public void onTurnReceival() {
		
	}
	
	@Override
	public void onEndOfTurn() {
		
	}

	private int getSearchDepth() {
		return searchDepth;
	}

	private void setSearchDepth(int searchDepth) {
		if (searchDepth < 1)
			throw new HexException("Search depth set to zero or negative number");
		this.searchDepth = searchDepth;
	}
}
