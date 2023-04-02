package test.hex;

import java.util.function.BiConsumer;

import main.hex.Player;
import main.hex.ConcurrentPlayerResponse;
import main.hex.Move;
import main.hex.board.Board;
import main.hex.board.TileColour;

public class TestPlayerClass extends Player {

	private ConcurrentPlayerResponse response;
	
	public TestPlayerClass(TileColour playerColour) {
		super(playerColour);
	}

	@Override
	public void processTurn(Board board, ConcurrentPlayerResponse response) {
		this.response = response;
	}

	@Override
	public void correctInvalidTurn(Board board, ConcurrentPlayerResponse response) {
		throw new IllegalStateException();
	}

	@Override
	public void onTurnReceival() {
		
	}
	
	@Override
	public void onEndOfTurn() {
		
	}
	
	public void relayResponseAsMove(int x, int y) {
		response.placeMove(new Move(x, y));
	}

	public void relayResponseAsError(Throwable error) {
		response.setError(error);
	}
}
