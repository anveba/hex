package test.hex;

import static org.junit.Assert.*;

import org.junit.*;

import main.hex.*;

public class TileTest {
    private Tile tile;

    @Before
    public void setup() {
        tile = new Tile(Tile.Colour.WHITE);
    }

    @Test
    public void getSetTileColour_someColour_someColour(){
        tile.setColour(Tile.Colour.BLUE);
        assertEquals(Tile.Colour.BLUE,tile.getColour());
    }

}
