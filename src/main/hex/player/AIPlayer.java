package main.hex.player;

import main.hex.HexException;
import main.hex.ai.*;
import main.hex.board.Board;
import main.hex.board.TileColour;

public class AIPlayer extends Player {

	private int timeLimitPerTurnInSeconds;

	
	public AIPlayer(TileColour playerColour, int timeLimitPerTurnInSeconds) {
		super(playerColour);
		setTimeLimitPerTurnInSeconds(timeLimitPerTurnInSeconds);
	}

	public AIPlayer(TileColour playerColour, int initialTimerDuration, int timeLimitPerTurnInSeconds) {
		super(playerColour, initialTimerDuration);
		setTimeLimitPerTurnInSeconds(timeLimitPerTurnInSeconds);
	}

	@Override
	public void processTurn(Board board, ConcurrentPlayerResponse response) {
		Thread t = new Thread(() -> response.placeMove(new AI(board, this).getBestMoveWithTimeLimit(timeLimitPerTurnInSeconds)));
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

	private int getTimeLimitPerTurnInSeconds() {
		return timeLimitPerTurnInSeconds;
	}

	private void setTimeLimitPerTurnInSeconds(int timeLimitPerTurnInSeconds) {
		if (timeLimitPerTurnInSeconds < 1)
			throw new HexException("Search depth set to zero or negative number");
		this.timeLimitPerTurnInSeconds = timeLimitPerTurnInSeconds;
	}
}
