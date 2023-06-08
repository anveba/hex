package main.hex.player;

import java.util.function.BiConsumer;

import main.engine.TimeRecord;
import main.hex.HexException;
import main.hex.PlayerTimer;
import main.hex.Updateable;
import main.hex.board.Board;
import main.hex.board.Tile;
import main.hex.board.TileColour;

 public abstract class Player implements Updateable {

    private TileColour playerColour;
    private PlayerTimer playerTimer;
    
    public static final float defaultTimeLimit = 60.0f;

    public Player (TileColour playerColour) {
    	this(playerColour, defaultTimeLimit);
    }

    public Player (TileColour playerColour, double InitialTimerDuration) {
        setColour(playerColour);
        this.playerTimer = new PlayerTimer(InitialTimerDuration);
    }

    public TileColour getColour() {
        return playerColour;
    }

    public void setColour(TileColour newColour) {
    	if (newColour == null || newColour == TileColour.WHITE)
    		throw new HexException("Invalid player colour");
    	this.playerColour = newColour;
	}

    public PlayerTimer getTimer() {
        return playerTimer;
    }
    
    public boolean winsByVerticalConnection() {
    	return playerColour.winsByVerticalConnection();
    }
    
    public abstract void processTurn(Board board, ConcurrentPlayerResponse response);
    
    public abstract void correctInvalidTurn(Board board, ConcurrentPlayerResponse response);
    
    public abstract void onTurnReceival();
    
    public abstract void onEndOfTurn();
    

	@Override
	public void update(TimeRecord elapsed) {
		
	}
}
