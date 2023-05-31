package test.hex;

import main.hex.PlayerTimer;
import main.hex.board.TileColour;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.spy;

public class TimerTest {

    private TestPlayerClass player1, player2;

    @Before
    public void setup() {
        player1 = spy(new TestPlayerClass(TileColour.PLAYER1));
        player2 = spy(new TestPlayerClass(TileColour.PLAYER2));
    }


}
