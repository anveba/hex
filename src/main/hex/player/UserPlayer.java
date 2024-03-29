package main.hex.player;

import java.util.function.BiConsumer;

import main.engine.TimeRecord;
import main.engine.input.Controls;
import main.engine.input.ControlsArgs;
import main.engine.input.ControlsCallback;
import main.engine.math.Point2;
import main.engine.ui.FrameStack;
import main.hex.Game;
import main.hex.Preferences;
import main.hex.Updateable;
import main.hex.board.Board;
import main.hex.board.TileColour;
import main.hex.logic.Move;

/**
 * 
 * @author Andreas - s214971
 *
 */
public class UserPlayer extends Player {

	private Board board;
	private ConcurrentPlayerResponse response;
	private boolean isCurrentTurn;
	
	public UserPlayer(TileColour playerColour, String name) {
		super(playerColour, name);
		isCurrentTurn = false;
	}

	public UserPlayer(TileColour playerColour, float timeLimit, String name) {
		super(playerColour, timeLimit, name);
		isCurrentTurn = false;
	}
	
	private void handleClick() {
		assert response != null;
		if (response == null)
			return;
		
		float cursorX = Game.getInstance().getControlsListener().getCursorX();
		float cursorY = Game.getInstance().getControlsListener().getCursorY();
		if (FrameStack.getInstance().peek() != null &&
				FrameStack.getInstance().peek().containsPosition(cursorX, cursorY))
			return;
		
		Point2 tileIndex;
		if (Preferences.getCurrent().is3DEnabled()) {
			tileIndex = board.screenToTile3D(cursorX, cursorY);
		} else {
			tileIndex = board.screenToTile2D(cursorX, cursorY);
		}
        if (!board.isOutOfBounds(tileIndex.getX(), tileIndex.getY()) && response.getMove() == null) {
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
	public void stopProcessing() {
		
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
