package main.hex.player;

import main.hex.HexException;
import main.hex.ai.*;
import main.hex.board.Board;
import main.hex.board.TileColour;

public class AIPlayer extends Player {

	private float timeLimitPerTurnInSeconds;

	public static final float defaultMaximumProcessingTime = 5.0f;
	
	private Thread aiThread;

	public AIPlayer(TileColour playerColour, float initialTimerDuration, float timeLimitPerTurnInSeconds, String name) {
		super(playerColour, initialTimerDuration, name);
		setTimeLimitPerTurnInSeconds(timeLimitPerTurnInSeconds);
	}

	@Override
	public void processTurn(Board board, ConcurrentPlayerResponse response) {
		aiThread = new Thread(() -> response.placeMove(new AI(board, this).getBestMoveWithTimeLimit(timeLimitPerTurnInSeconds)));
		aiThread.setUncaughtExceptionHandler((th, ex) -> { response.setError(ex); });
		aiThread.start();
	}
	
	@Override
	public void stopProcessing() {
		if (aiThread != null && aiThread.isAlive()) {
			aiThread.stop();
			System.out.println("AI killed");
		}
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
