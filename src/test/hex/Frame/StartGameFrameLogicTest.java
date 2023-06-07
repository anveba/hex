package test.hex.Frame;

import main.engine.graphics.Colour;
import main.engine.graphics.Texture;
import main.engine.ui.TextField;
import main.hex.player.PlayerType;
import main.hex.ui.StartGameFrameLogic;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StartGameFrameLogicTest {

    private StartGameFrameLogic startGameFrameLogic;

    @Before
    public void setup() {
        startGameFrameLogic = new StartGameFrameLogic();
    }

    @Test
    public void getSwapRule_initialValue_isFalse() {
        Assert.assertFalse(startGameFrameLogic.getSwapRule());
    }

    @Test
    public void toggleSwapRule_onToggleOnce_isTrue() {
        startGameFrameLogic.toggleSwapRule();
        Assert.assertTrue(startGameFrameLogic.getSwapRule());
    }

    @Test
    public void toggleSwapRule_onToggleTwice_isFalse() {
        startGameFrameLogic.toggleSwapRule();
        startGameFrameLogic.toggleSwapRule();
        Assert.assertFalse(startGameFrameLogic.getSwapRule());
    }

    @Test
    public void getPlayerTextureIndex_player0_is0() {
        Assert.assertEquals(0, startGameFrameLogic.getPlayerTextureIndex(0));
    }
    @Test
    public void getPlayerTextureIndex_player1_is0() {
        Assert.assertEquals(0, startGameFrameLogic.getPlayerTextureIndex(1));
    }
    @Test
    public void setPlayerTextureIndex_player0Texture1_is1() {
        startGameFrameLogic.setPlayerTextureIndex(0, 1);
        Assert.assertEquals(1, startGameFrameLogic.getPlayerTextureIndex(0));
    }

    @Test
    public void playerColoursSetCorrectly() {
        startGameFrameLogic.setPlayer1Col(Colour.Red);
        startGameFrameLogic.setPlayer2Col(Colour.Blue);
        Assert.assertEquals(Colour.Red, startGameFrameLogic.getPlayer1Col());
        Assert.assertEquals(Colour.Blue, startGameFrameLogic.getPlayer2Col());
    }

    @Test
    public void addHexTexture_3Added_addedInRightOrder() {
        Assert.assertEquals(0, startGameFrameLogic.getHexTextureCount());

        int t1 = 4;
        int t2 = 8;
        int t3 = 19;

        startGameFrameLogic.addHexTextureId(t1);
        startGameFrameLogic.addHexTextureId(t2);
        startGameFrameLogic.addHexTextureId(t3);
        Assert.assertEquals(t1, startGameFrameLogic.getHexTextureId(0));
        Assert.assertEquals(t2, startGameFrameLogic.getHexTextureId(1));
        Assert.assertEquals(t3, startGameFrameLogic.getHexTextureId(2));
    }

    @Test
    public void getHexTextureCount_3Added_is3() {
        Assert.assertEquals(0, startGameFrameLogic.getHexTextureCount());

        int t1 = 4;
        int t2 = 8;
        int t3 = 19;

        startGameFrameLogic.addHexTextureId(t1);
        startGameFrameLogic.addHexTextureId(t2);
        startGameFrameLogic.addHexTextureId(t3);
        Assert.assertEquals(3, startGameFrameLogic.getHexTextureCount());
    }

    @Test
    public void getPlayerTextureIndex_player0TextureIndex1_skin1() {
    	int t0 = 2;
        int t1 = 9;

        startGameFrameLogic.addHexTextureId(t0);
        startGameFrameLogic.addHexTextureId(t1);

        startGameFrameLogic.setPlayerTextureIndex(0,1);

        Assert.assertEquals(1, startGameFrameLogic.getPlayerTextureIndex(0));
    }

    @Test
    public void getPlayerTexture_player0TextureIndex0_texture0() {
    	int t0 = 2;
        int t1 = 9;

        startGameFrameLogic.addHexTextureId(t0);
        startGameFrameLogic.addHexTextureId(t1);

        Assert.assertEquals(t0, startGameFrameLogic.getPlayerTextureId(0));
    }

    @Test
    public void nextTexture_player0Texture0TexturesTotal3_isTexture1() {

    	int t0 = 42;
        int t1 = 62;
        int t2 = 13;

        startGameFrameLogic.addHexTextureId(t0);
        startGameFrameLogic.addHexTextureId(t1);
        startGameFrameLogic.addHexTextureId(t2);

        Assert.assertEquals(t0, startGameFrameLogic.getPlayerTextureId(0));
        startGameFrameLogic.nextTexture(0);
        Assert.assertEquals(t1, startGameFrameLogic.getPlayerTextureId(0));
    }

    @Test
    public void nextTexture_onLastTexture_loopedAroundToFirstTexture() {

    	int t0 = 42;
        int t1 = 62;
        int t2 = 13;

        startGameFrameLogic.addHexTextureId(t0);
        startGameFrameLogic.addHexTextureId(t1);
        startGameFrameLogic.addHexTextureId(t2);

        startGameFrameLogic.setPlayerTextureIndex(0,2);
        Assert.assertEquals(t2, startGameFrameLogic.getPlayerTextureId(0));
        startGameFrameLogic.nextTexture(0);
        Assert.assertEquals(t0, startGameFrameLogic.getPlayerTextureId(0));
    }

    @Test
    public void previousTexture_player0Texture2TexturesTotal3_isTexture1() {

    	int t0 = 42;
        int t1 = 62;
        int t2 = 13;

        startGameFrameLogic.addHexTextureId(t0);
        startGameFrameLogic.addHexTextureId(t1);
        startGameFrameLogic.addHexTextureId(t2);

        startGameFrameLogic.setPlayerTextureIndex(0,2);
        Assert.assertEquals(t2, startGameFrameLogic.getPlayerTextureId(0));
        startGameFrameLogic.previousTexture(0);
        Assert.assertEquals(t1, startGameFrameLogic.getPlayerTextureId(0));
    }

    @Test
    public void previousTexture_onLastTexture_loopedAroundToLastTexture() {

    	int t0 = 42;
        int t1 = 62;
        int t2 = 13;

        startGameFrameLogic.addHexTextureId(t0);
        startGameFrameLogic.addHexTextureId(t1);
        startGameFrameLogic.addHexTextureId(t2);

        startGameFrameLogic.setPlayerTextureIndex(0,0);
        Assert.assertEquals(t0, startGameFrameLogic.getPlayerTextureId(0));
        startGameFrameLogic.previousTexture(0);
        Assert.assertEquals(t2, startGameFrameLogic.getPlayerTextureId(0));
    }

    @Test
    public void addPlayerType_3Added_addedInRightOrder() {
        Assert.assertEquals(0, startGameFrameLogic.getOpponentTypeCount());

        PlayerType t1 = mock(PlayerType.class);
        PlayerType t2 = mock(PlayerType.class);
        PlayerType t3 = mock(PlayerType.class);

        startGameFrameLogic.addPlayerType(t1, "t1");
        startGameFrameLogic.addPlayerType(t2, "t2");
        startGameFrameLogic.addPlayerType(t3, "t3");
        Assert.assertEquals(t1, startGameFrameLogic.getOpponentType(0));
        Assert.assertEquals(t2, startGameFrameLogic.getOpponentType(1));
        Assert.assertEquals(t3, startGameFrameLogic.getOpponentType(2));
        Assert.assertEquals("t1", startGameFrameLogic.getOpponentTypeString(t1));
        Assert.assertEquals("t2", startGameFrameLogic.getOpponentTypeString(t2));
        Assert.assertEquals("t3", startGameFrameLogic.getOpponentTypeString(t3));
    }

    @Test
    public void nextPlayerType_3AddedPlayerFirstTypeAdded_isSecondType() {
        PlayerType t1 = mock(PlayerType.class);
        PlayerType t2 = mock(PlayerType.class);
        PlayerType t3 = mock(PlayerType.class);

        startGameFrameLogic.addPlayerType(t1, "t1");
        startGameFrameLogic.addPlayerType(t2, "t2");
        startGameFrameLogic.addPlayerType(t3, "t3");

        Assert.assertEquals(t1, startGameFrameLogic.getPlayerType(0));
        startGameFrameLogic.nextPlayerType(0);
        Assert.assertEquals(t2, startGameFrameLogic.getPlayerType(0));
    }

    @Test
    public void nextPlayerType_3AddedPlayerLastTypeAdded_isFirstType() {
        PlayerType t1 = mock(PlayerType.class);
        PlayerType t2 = mock(PlayerType.class);
        PlayerType t3 = mock(PlayerType.class);

        startGameFrameLogic.addPlayerType(t1, "t1");
        startGameFrameLogic.addPlayerType(t2, "t2");
        startGameFrameLogic.addPlayerType(t3, "t3");

        startGameFrameLogic.setPlayerTypeIndex(0,2);
        Assert.assertEquals(t3, startGameFrameLogic.getPlayerType(0));
        startGameFrameLogic.nextPlayerType(0);
        Assert.assertEquals(t1, startGameFrameLogic.getPlayerType(0));
    }

    @Test
    public void previousPlayerType_3AddedPlayerLastTypeAdded_isSecondType() {
        PlayerType t1 = mock(PlayerType.class);
        PlayerType t2 = mock(PlayerType.class);
        PlayerType t3 = mock(PlayerType.class);

        startGameFrameLogic.addPlayerType(t1, "t1");
        startGameFrameLogic.addPlayerType(t2, "t2");
        startGameFrameLogic.addPlayerType(t3, "t3");

        startGameFrameLogic.setPlayerTypeIndex(0,2);
        Assert.assertEquals(t3, startGameFrameLogic.getPlayerType(0));
        startGameFrameLogic.previousPlayerType(0);
        Assert.assertEquals(t2, startGameFrameLogic.getPlayerType(0));
    }
    @Test
    public void previousPlayerType_3AddedPlayerFirstTypeAdded_isLastType() {
        PlayerType t1 = mock(PlayerType.class);
        PlayerType t2 = mock(PlayerType.class);
        PlayerType t3 = mock(PlayerType.class);

        startGameFrameLogic.addPlayerType(t1, "t1");
        startGameFrameLogic.addPlayerType(t2, "t2");
        startGameFrameLogic.addPlayerType(t3, "t3");

        Assert.assertEquals(t1, startGameFrameLogic.getPlayerType(0));
        startGameFrameLogic.previousPlayerType(0);
        Assert.assertEquals(t3, startGameFrameLogic.getPlayerType(0));
    }

    @Test
    public void setPlayerTypeIndex_3AddedPlayerFirstTypeAddedSetToSecond_isSecondType() {
        PlayerType t1 = mock(PlayerType.class);
        PlayerType t2 = mock(PlayerType.class);
        PlayerType t3 = mock(PlayerType.class);

        startGameFrameLogic.addPlayerType(t1, "t1");
        startGameFrameLogic.addPlayerType(t2, "t2");
        startGameFrameLogic.addPlayerType(t3, "t3");

        Assert.assertEquals(t1, startGameFrameLogic.getPlayerType(0));
        startGameFrameLogic.setPlayerTypeIndex(0,1);
        Assert.assertEquals(t2, startGameFrameLogic.getPlayerType(0));
    }

    @Test
    public void getPlayerType_PlayerType2_isPlayerType2() {
        PlayerType t1 = mock(PlayerType.class);
        PlayerType t2 = mock(PlayerType.class);
        PlayerType t3 = mock(PlayerType.class);

        startGameFrameLogic.addPlayerType(t1, "t1");
        startGameFrameLogic.addPlayerType(t2, "t2");
        startGameFrameLogic.addPlayerType(t3, "t3");

        startGameFrameLogic.setPlayerTypeIndex(0,1);
        Assert.assertEquals(t2, startGameFrameLogic.getPlayerType(0));
    }

    @Test
    public void getPlayerTypeString_PlayerType2_isPlayerType2String() {
        PlayerType t1 = mock(PlayerType.class);
        PlayerType t2 = mock(PlayerType.class);
        PlayerType t3 = mock(PlayerType.class);

        startGameFrameLogic.addPlayerType(t1, "t1");
        startGameFrameLogic.addPlayerType(t2, "t2");
        startGameFrameLogic.addPlayerType(t3, "t3");

        startGameFrameLogic.setPlayerTypeIndex(0,1);
        Assert.assertEquals("t2", startGameFrameLogic.getPlayerTypeString(0));
    }

    @Test
    public void getPlayerTypeIndex_PlayerType2_isPlayerType2Index() {
        PlayerType t1 = mock(PlayerType.class);
        PlayerType t2 = mock(PlayerType.class);
        PlayerType t3 = mock(PlayerType.class);

        startGameFrameLogic.addPlayerType(t1, "t1");
        startGameFrameLogic.addPlayerType(t2, "t2");
        startGameFrameLogic.addPlayerType(t3, "t3");

        startGameFrameLogic.setPlayerTypeIndex(0,1);
        Assert.assertEquals(1, startGameFrameLogic.getPlayerTypeIndex(0));
    }

    @Test
    public void setPlayerName_player0TextField_isPlayer0Name() {

        TextField textField = mock(TextField.class);
        when(textField.getText()).thenReturn("player0");

        startGameFrameLogic.setPlayerName(0, textField);
        Assert.assertEquals("player0", startGameFrameLogic.getPlayerName(0));
    }

}
