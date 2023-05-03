package main.hex.player;

import main.hex.HexException;
import main.hex.Move;

public class ConcurrentPlayerResponse {
	
	private Move heldMove;
	private Throwable error;
	
	public ConcurrentPlayerResponse() {
		heldMove = null;
		error = null;
	}
	
	public synchronized void placeMove(Move move) {
		if (heldMove != null)
			throw new HexException("A move is already held");
		if (move == null)
			throw new HexException("The given move was null");
		heldMove = move;
	}
	
	/**
	 * 
	 * @return Returns null if no move is held.
	 */
	public synchronized Move getMove() {
		return heldMove;
	}
	
	public synchronized void setError(Throwable error) {
		if (error == null)
			throw new HexException("Error was null");
		this.error = error;
	}
	
	/**
	 * 
	 * @return Returns null if no error is held.
	 */
	public synchronized Throwable getError() {
		return error;
	}
}
