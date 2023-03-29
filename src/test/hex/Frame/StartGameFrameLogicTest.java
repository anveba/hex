package test.hex.Frame;

import main.engine.graphics.Texture;
import main.engine.ui.TextField;
import main.hex.PlayerType;
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
        Assert.assertEquals(0, startGameFrameLogic.getPlayerSkinIndex(0));
    }
    @Test
    public void getPlayerTextureIndex_player1_is0() {
        Assert.assertEquals(0, startGameFrameLogic.getPlayerSkinIndex(1));
    }
    @Test
    public void setPlayerTextureIndex_player0Texture1_is1() {
        startGameFrameLogic.setPlayerSkinIndex(0, 1);
        Assert.assertEquals(1, startGameFrameLogic.getPlayerSkinIndex(0));
    }

    @Test
    public void addHexSkin_3Added_addedInRightOrder() {
        Assert.assertEquals(0, startGameFrameLogic.getHexSkinCount());

        Texture t1 = mock(Texture.class);
        Texture t2 = mock(Texture.class);
        Texture t3 = mock(Texture.class);

        startGameFrameLogic.addHexSkin(t1);
        startGameFrameLogic.addHexSkin(t2);
        startGameFrameLogic.addHexSkin(t3);
        Assert.assertEquals(t1, startGameFrameLogic.getHexSkin(0));
        Assert.assertEquals(t2, startGameFrameLogic.getHexSkin(1));
        Assert.assertEquals(t3, startGameFrameLogic.getHexSkin(2));
    }

    @Test
    public void getHexSkinCount_3Added_is3() {
        Assert.assertEquals(0, startGameFrameLogic.getHexSkinCount());

        Texture t1 = mock(Texture.class);
        Texture t2 = mock(Texture.class);
        Texture t3 = mock(Texture.class);

        startGameFrameLogic.addHexSkin(t1);
        startGameFrameLogic.addHexSkin(t2);
        startGameFrameLogic.addHexSkin(t3);
        Assert.assertEquals(3, startGameFrameLogic.getHexSkinCount());
    }

    @Test
    public void getPlayerSkinIndex_player0SkinIndex1_skin1() {
        Texture t0 = mock(Texture.class);
        Texture t1 = mock(Texture.class);

        startGameFrameLogic.addHexSkin(t0);
        startGameFrameLogic.addHexSkin(t1);

        startGameFrameLogic.setPlayerSkinIndex(0,1);

        Assert.assertEquals(1, startGameFrameLogic.getPlayerSkinIndex(0));
    }

    @Test
    public void getPlayerSkin_player0SkinIndex0_skin0() {
        Texture t0 = mock(Texture.class);
        Texture t1 = mock(Texture.class);

        startGameFrameLogic.addHexSkin(t0);
        startGameFrameLogic.addHexSkin(t1);

        Assert.assertEquals(t0, startGameFrameLogic.getPlayerSkin(0));
    }

    @Test
    public void nextSkin_player0Texture0TexturesTotal3_isTexture1() {

        Texture t0 = mock(Texture.class);
        Texture t1 = mock(Texture.class);
        Texture t2 = mock(Texture.class);

        startGameFrameLogic.addHexSkin(t0);
        startGameFrameLogic.addHexSkin(t1);
        startGameFrameLogic.addHexSkin(t2);

        Assert.assertEquals(t0, startGameFrameLogic.getPlayerSkin(0));
        startGameFrameLogic.nextSkin(0);
        Assert.assertEquals(t1, startGameFrameLogic.getPlayerSkin(0));
    }

    @Test
    public void nextSkin_onLastSkin_loopedAroundToFirstSkin() {

        Texture t0 = mock(Texture.class);
        Texture t1 = mock(Texture.class);
        Texture t2 = mock(Texture.class);

        startGameFrameLogic.addHexSkin(t0);
        startGameFrameLogic.addHexSkin(t1);
        startGameFrameLogic.addHexSkin(t2);

        startGameFrameLogic.setPlayerSkinIndex(0,2);
        Assert.assertEquals(t2, startGameFrameLogic.getPlayerSkin(0));
        startGameFrameLogic.nextSkin(0);
        Assert.assertEquals(t0, startGameFrameLogic.getPlayerSkin(0));
    }

    @Test
    public void previousSkin_player0Texture2TexturesTotal3_isTexture1() {

        Texture t0 = mock(Texture.class);
        Texture t1 = mock(Texture.class);
        Texture t2 = mock(Texture.class);

        startGameFrameLogic.addHexSkin(t0);
        startGameFrameLogic.addHexSkin(t1);
        startGameFrameLogic.addHexSkin(t2);

        startGameFrameLogic.setPlayerSkinIndex(0,2);
        Assert.assertEquals(t2, startGameFrameLogic.getPlayerSkin(0));
        startGameFrameLogic.previousSkin(0);
        Assert.assertEquals(t1, startGameFrameLogic.getPlayerSkin(0));
    }

    @Test
    public void previousSkin_onLastSkin_loopedAroundToLastSkin() {

        Texture t0 = mock(Texture.class);
        Texture t1 = mock(Texture.class);
        Texture t2 = mock(Texture.class);

        startGameFrameLogic.addHexSkin(t0);
        startGameFrameLogic.addHexSkin(t1);
        startGameFrameLogic.addHexSkin(t2);

        startGameFrameLogic.setPlayerSkinIndex(0,0);
        Assert.assertEquals(t0, startGameFrameLogic.getPlayerSkin(0));
        startGameFrameLogic.previousSkin(0);
        Assert.assertEquals(t2, startGameFrameLogic.getPlayerSkin(0));
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
