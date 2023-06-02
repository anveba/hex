package main.hex.player;

import java.util.function.BiConsumer;

import main.engine.Point2;
import main.engine.TimeRecord;
import main.engine.input.Controls;
import main.engine.input.ControlsArgs;
import main.engine.input.ControlsCallback;
import main.hex.Game;
import main.hex.Move;
import main.hex.Preferences;
import main.hex.Updateable;
import main.hex.board.Board;
import main.hex.board.TileColour;

public class UserPlayer extends Player {

	private Board board;
	private ConcurrentPlayerResponse response;
	private boolean isCurrentTurn;
	
	public UserPlayer(TileColour playerColour) {
		super(playerColour);
		isCurrentTurn = false;
	}

	public UserPlayer(TileColour playerColour, int timeLimit) {
		super(playerColour, timeLimit);
		isCurrentTurn = false;
	}
	
	private void handleClick() {
		assert response != null;
		if (response == null)
			return;
		Point2 tileIndex;
		if (Preferences.getInstance().is3DEnabled()) {
			tileIndex = board.screenToTile3D(Game.getInstance().getControlsListener().getCursorX(),
					Game.getInstance().getControlsListener().getCursorY());
		} else {
			tileIndex = board.screenToTile2D(Game.getInstance().getControlsListener().getCursorX(),
				Game.getInstance().getControlsListener().getCursorY());
		}
        if (!board.isOutOfBounds(tileIndex.getX(), tileIndex.getY())) {
        	response.placeMove(new Move(tileIndex.getX(), tileIndex.getY()));
        }
	}

	@Override
	public void processTurn(Board board, ConcurrentPlayerResponse response) {
		isCurrentTurn = true;
		this.board = board;
		this.response = response;
	}

	@Override
	public void correctInvalidTurn(Board board, ConcurrentPlayerResponse response) {
		processTurn(board, response);
	}

	@Override
	public void onTurnReceival() {
		isCurrentTurn = false;
	}
	
	@Override
	public void onEndOfTurn() {
    	
	}
	

	@Override
	public void update(TimeRecord elapsed) {
		if (isCurrentTurn && 
				Game.getInstance().getControlsListener().isReleased(Controls.LEFT_MOUSE))
			handleClick();
		
	}
}