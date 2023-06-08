package main.hex.player;

import main.hex.HexException;
import main.hex.ai.*;
import main.hex.board.Board;
import main.hex.board.TileColour;

public class AIPlayer extends Player {

	private float timeLimitPerTurnInSeconds;

	public static final float defaultMaximumProcessingTime = 5.0f;

	public AIPlayer(TileColour playerColour, float initialTimerDuration, float timeLimitPerTurnInSeconds) {
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

	private float getTimeLimitPerTurnInSeconds() {
		return timeLimitPerTurnInSeconds;
	}

	private void setTimeLimitPerTurnInSeconds(float timeLimitPerTurnInSeconds) {
		if (timeLimitPerTurnInSeconds <= 0.0f)
			throw new HexException("Search depth set to zero or negative number");
		this.timeLimitPerTurnInSeconds = timeLimitPerTurnInSeconds;
	}
}
