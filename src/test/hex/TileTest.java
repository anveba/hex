package test.hex;

import static org.junit.Assert.*;

import org.junit.*;

import main.hex.*;
import main.hex.board.Tile;
import main.hex.board.TileColour;

public class TileTest {
    private Tile tile;

    @Before
    public void setup() {
        tile = new Tile(TileColour.WHITE);
    }

    @Test
    public void getSetTileColour_someColour_someColour(){
        assertEquals(TileColour.WHITE,tile.getColour());
    }

}
