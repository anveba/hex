package main.hex.board;

import main.hex.HexException;

public enum TileColour {
    BLUE,
    RED,
    WHITE;
    
    public static TileColour opposite(TileColour c){
    	if (c == null)
    		throw new HexException("Colour was null");
        if(c == TileColour.BLUE){
            return TileColour.RED;
        }
        if(c == TileColour.RED){
            return TileColour.BLUE;
        }
        return TileColour.WHITE;
    }
}
