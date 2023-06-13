package test.hex;

import static org.junit.Assert.*;
import org.junit.*;

import main.hex.*;
import main.hex.board.Tile;
import main.hex.board.TileColour;
import main.hex.player.Player;
import main.hex.player.UserPlayer;

public class PlayerTest {

    private Player player1;

    @Before
    public void setup() {
        player1 = new UserPlayer(TileColour.PLAYER1, "Player 1");
    }

    @Test
    public void constructor_player_success() {
        assertNotNull(player1);
    }

    @Test
    public void getPlayerColour_playerBlueColour_colourReturned() {
        assertEquals(TileColour.PLAYER1, player1.getColour());
    }

    @Test
    public void setPlayerColour_player1RedColour_playerHasRedColour() {
        player1.setColour(TileColour.PLAYER2);
        assertEquals(TileColour.PLAYER2, player1.getColour());
    }
    
    @Test
    public void constructor_blue_playerDoesNotWinVertically() {
        Player p = new UserPlayer(TileColour.PLAYER1, "Player 1");
        assertFalse(p.winsByVerticalConnection());
    }
    
    @Test
    public void constructor_red_playerWinsVertically() {
        Player p = new UserPlayer(TileColour.PLAYER2, "Player 2");
        assertTrue(p.winsByVerticalConnection());
    }
}
