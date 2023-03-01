package test.hex;

import static org.junit.Assert.*;

import org.junit.*;

import main.hex.*;

public class TileTest {
    private Tile tile;

    @Before
    public void setup() {
        tile = new Tile();
    }

    @Test
    public void getSetTileColour_someColour_someColour(){
        tile.setColour(Tile.Colour.WHITE);
        assertEquals(Tile.Colour.WHITE,tile.getColour());
    }

}
