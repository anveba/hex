package test.hex;

import hex.Board;
import hex.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TileTest {
    private Tile tile;

    @BeforeEach
    void setup() {
        tile = new Tile();
    }

    @Test
    public void getSetTileColour_someColour_someColour(){
        tile.setColour(Tile.Colour.WHITE);
        assertEquals(Tile.Colour.WHITE,tile.getColour());
    }

}
