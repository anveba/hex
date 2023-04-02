package test.hex;

import static org.junit.Assert.*;
import org.junit.*;

import main.hex.*;
import main.hex.board.Tile;
import main.hex.board.TileColour;

public class PlayerTest {

    private Player player1;

    @Before
    public void setup() {
        player1 = new UserPlayer(TileColour.BLUE);
    }

    @Test
    public void constructor_player_success() {
        assertNotNull(player1);
    }

    @Test
    public void getPlayerColour_playerBlueColour_colourReturned() {
        assertEquals(TileColour.BLUE, player1.getColour());
    }

    @Test
    public void setPlayerColour_player1RedColour_playerHasRedColour() {
        player1.setColour(TileColour.RED);
        assertEquals(TileColour.RED, player1.getColour());
    }
    
    @Test
    public void constructor_blue_playerDoesNotWinVertically() {
        Player p = new UserPlayer(TileColour.BLUE);
        assertFalse(p.winsByVerticalConnection());
    }
    
    @Test
    public void constructor_red_playerWinsVertically() {
        Player p = new UserPlayer(TileColour.RED);
        assertTrue(p.winsByVerticalConnection());
    }
}
