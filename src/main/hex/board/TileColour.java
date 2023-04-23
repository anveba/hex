package main.hex.board;

import main.hex.HexException;

public enum TileColour {
    PLAYER1,
    PLAYER2,
    WHITE;
    
    public static TileColour opposite(TileColour c){
    	if (c == null)
    		throw new HexException("Colour was null");
        if(c == TileColour.PLAYER1){
            return TileColour.PLAYER2;
        }
        if(c == TileColour.PLAYER2){
            return TileColour.PLAYER1;
        }
        return TileColour.WHITE;
    }
    
    public boolean winsByVerticalConnection() {
    	if (this == WHITE || this == null)
    		throw new HexException("Invalid colour.");
    	return this == PLAYER2;
    }
}
