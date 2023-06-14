package main.hex.board;

import main.hex.HexException;

/**
 * Immutable.
 * @author Andreas - s214971
 */
public class Tile {
	
    private TileColour colour;

    public Tile(TileColour colour){
        if (colour == null) {
            this.colour = TileColour.WHITE;
        } else {
            this.colour = colour;
        }

    }

    public TileColour getColour() {
        return colour;
    }
}
