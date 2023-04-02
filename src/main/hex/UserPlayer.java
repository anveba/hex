package main.hex;

import java.util.function.BiConsumer;

import main.engine.Point2;
import main.engine.input.Controls;
import main.engine.input.ControlsArgs;
import main.engine.input.ControlsCallback;
import main.hex.board.Board;
import main.hex.board.TileColour;

public class UserPlayer extends Player implements ControlsCallback {

	private Board board;
	private ConcurrentPlayerResponse response;
	
	public UserPlayer(TileColour playerColour) {
		super(playerColour);
	}
	
	private void handleClick() {
		assert response != null;
		if (response == null)
			return;
		Point2 tileIndex = board.screenToTile(Game.getInstance().getControlsListener().getCursorX(),
                Game.getInstance().getControlsListener().getCursorY());
        if (!board.isOutOfBounds(tileIndex.getX(), tileIndex.getY())) {
        	response.placeMove(new Move(tileIndex.getX(), tileIndex.getY()));
        }
	}

	@Override
	public void processTurn(Board board, ConcurrentPlayerResponse response) {
		Game.getInstance().getControlsListener()
			.addOnAnyPressCallback(this);
		this.board = board;
		this.response = response;
	}

	@Override
	public void onControlsInput(ControlsArgs args) {
		if (args.getControls() == Controls.LEFT_MOUSE) {
			handleClick();
		}
	}

	@Override
	public void correctInvalidTurn(Board board, ConcurrentPlayerResponse response) {
		processTurn(board, response);
	}

	@Override
	public void onTurnReceival() {
		Game.getInstance().getControlsListener()
			.removeOnAnyPressCallback(this);
	}
	
	@Override
	public void onEndOfTurn() {
    	
	}
}
