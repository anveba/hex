package test.hex;

import main.hex.Player;
import main.hex.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player player1;

    @BeforeEach
    void setup() {
        player1 = new Player(Tile.Colour.BLUE);
    }

    @Test
    void constructor_player_success() {
        assertNotNull(player1);
    }

    @Test
    void getPlayerColour_player1BlueColour_colourReturned() {
        assertEquals(Tile.Colour.BLUE, player1.getPlayerColor());
    }



}
