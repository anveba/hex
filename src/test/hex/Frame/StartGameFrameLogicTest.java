package test.hex.Frame;

import main.engine.graphics.Texture;
import main.hex.ui.StartGameFrameLogic;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

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

}
