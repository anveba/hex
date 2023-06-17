package main.hex.board;

import main.hex.HexException;

/**
 * Immutable.
 * @author Andreas - s214971
 */
public class Tile {
	
    private final TileColour colour;

    public Tile(TileColour colour){
        if (colour == null) 
            throw new HexException("Colour was null");
        this.colour = colour;
    }

    public TileColour getColour() {
        return colour;
    }
}
