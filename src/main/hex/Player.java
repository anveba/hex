package main.hex;

import java.util.function.BiConsumer;

import main.hex.board.Board;
import main.hex.board.Tile;
import main.hex.board.TileColour;

 public abstract class Player {

    private TileColour playerColour;

    public Player (TileColour playerColour) {
    	setColour(playerColour);
    }

    public TileColour getColour() {
        return playerColour;
    }

    public void setColour(TileColour newColour) {
    	if (newColour == null || newColour == TileColour.WHITE)
    		throw new HexException("Invalid player colour");
    	this.playerColour = newColour;
	}
    
    public boolean winsByVerticalConnection() {
    	return playerColour.winsByVerticalConnection();
    }
    
    protected abstract void processTurn(Board board, ConcurrentPlayerResponse response);
    
    protected abstract void correctInvalidTurn(Board board, ConcurrentPlayerResponse response);
    
    protected abstract void onTurnReceival();
    
    protected abstract void onEndOfTurn();
}
