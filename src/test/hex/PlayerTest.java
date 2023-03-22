package test.hex;

import static org.junit.Assert.*;
import org.junit.*;

import main.hex.*;

public class PlayerTest {

    private Player player1;

    @Before
    public void setup() {
        player1 = new Player(Tile.Colour.BLUE, false);
    }

    @Test
    public void constructor_player_success() {
        assertNotNull(player1);
    }

    @Test
    public void getPlayerColour_playerBlueColour_colourReturned() {
        assertEquals(Tile.Colour.BLUE, player1.getPlayerColour());
    }

    @Test
    public void setPlayerColour_player1RedColour_playerHasRedColour() {
        player1.setPlayerColour(Tile.Colour.RED);
        assertEquals(Tile.Colour.RED, player1.getPlayerColour());
    }
    
    @Test
    public void constructor_winsHorizontally_playerDoesNotWinVertically() {
        Player p = new Player(Tile.Colour.RED, false);
        assertFalse(p.winsByVerticalConnection());
    }
    
    @Test
    public void constructor_winsVertically_playerWinsVertically() {
        Player p = new Player(Tile.Colour.RED, true);
        assertTrue(p.winsByVerticalConnection());
    }
}
