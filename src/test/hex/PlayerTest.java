package test.hex;

import static org.junit.Assert.*;
import org.junit.*;

import main.hex.*;

public class PlayerTest {

    private Player player1, player2;
    private GameLogic gameLogic;

    @Before
    public void setup() {
        player1 = new Player(Tile.Colour.BLUE);
    }

    @Test
    public void constructor_player_success() {
        assertNotNull(player1);
    }

    @Test
    public void getPlayerColour_player1BlueColour_colourReturned() {
        assertEquals(Tile.Colour.BLUE, player1.getPlayerColour());
    }

    @Test
    public void setPlayerColour_player1RedColour_player1HasRedColour() {
        player1.setPlayerColour(Tile.Colour.RED);
        assertEquals(Tile.Colour.RED, player1.getPlayerColour());
    }
}
