package test.hex;

import main.hex.logic.GameCustomisation;
import main.hex.player.PlayerSkin;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class GameCustomisationTest {

    private String p1Name, p2Name;
    private PlayerSkin p1Skin, p2Skin;

    private final int initialTimeLimit = 300;

    private final boolean swapRule = true;

    private GameCustomisation gameCustomisation;

    public GameCustomisationTest() {
    }


    @Before
    public void setup() {
        p1Name = "Player 1";
        p2Name = "Player 2";

        p1Skin = mock(PlayerSkin.class);
        p2Skin = mock(PlayerSkin.class);

        gameCustomisation = new GameCustomisation(p1Name, p2Name, p1Skin, p2Skin, initialTimeLimit, swapRule);
    }

    @Test
    public void constructorSetsValuesCorrectly() {
        assertEquals(p1Name, gameCustomisation.getPlayer1Name());
        assertEquals(p2Name, gameCustomisation.getPlayer2Name());
        assertEquals(p1Skin, gameCustomisation.getPlayer1Skin());
        assertEquals(p2Skin, gameCustomisation.getPlayer2Skin());
        assertEquals(initialTimeLimit, gameCustomisation.getInitialTimeLimit());
        assertEquals(swapRule, gameCustomisation.getSwapRule());
    }

    @Test
    public void playersSwappedCorrectly() {
        gameCustomisation.setSwapped(false);

        assertEquals(p1Name, gameCustomisation.getPlayer1Name());
        assertEquals(p2Name, gameCustomisation.getPlayer2Name());
        assertEquals(p1Skin, gameCustomisation.getPlayer1Skin());
        assertEquals(p2Skin, gameCustomisation.getPlayer2Skin());

        gameCustomisation.setSwapped(true);

        assertEquals(p1Name, gameCustomisation.getPlayer2Name());
        assertEquals(p2Name, gameCustomisation.getPlayer1Name());
        assertEquals(p1Skin, gameCustomisation.getPlayer2Skin());
        assertEquals(p2Skin, gameCustomisation.getPlayer1Skin());
    }
}
