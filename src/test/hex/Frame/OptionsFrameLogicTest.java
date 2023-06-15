package test.hex.Frame;

import main.engine.ui.Frame;
import main.engine.ui.FrameStack;
import main.engine.ui.UIGroup;
import main.hex.Preferences;
import main.hex.ui.OptionsFrameLogic;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class OptionsFrameLogicTest {

    private OptionsFrameLogic optionsFrameLogic;

    @Before
    public void setup() {
        Preferences preferences = Preferences.getCurrent();
        FrameStack frameStack = FrameStack.getInstance();
        optionsFrameLogic = new OptionsFrameLogic();
    }

    @Test
    public void testExitSettingsButtonPressed() {
        Frame frame1 = mock(Frame.class);
        Frame frame2 = mock(Frame.class);
        FrameStack.getInstance().push(frame1);
        FrameStack.getInstance().push(frame2);

        Assert.assertEquals(frame2,FrameStack.getInstance().peek());

        optionsFrameLogic.exitSettingsButtonPressed(mock(UIGroup.class));

        Assert.assertEquals(frame1,FrameStack.getInstance().peek());
    }

    @Test
    public void testEnable3DGraphics() {
    	Preferences.getCurrent().disable3D();
    	Assert.assertFalse(Preferences.getCurrent().is3DEnabled());
        Assert.assertFalse(optionsFrameLogic.is3DEnabled());
    	optionsFrameLogic.enable3DGraphics();
    	Assert.assertTrue(Preferences.getCurrent().is3DEnabled());
        Assert.assertTrue(optionsFrameLogic.is3DEnabled());
    }

    @Test
    public void testEnable2DGraphics() {
        Preferences.getCurrent().enable3D();
        Assert.assertTrue(Preferences.getCurrent().is3DEnabled());
        Assert.assertTrue(optionsFrameLogic.is3DEnabled());
        optionsFrameLogic.enable2DGraphics();
        Assert.assertFalse(Preferences.getCurrent().is3DEnabled());
        Assert.assertFalse(optionsFrameLogic.is3DEnabled());
    }

    @Test
    public void testSetMusicVolume() {
        Preferences.getCurrent().setMusicVolume(0.5f);
        Assert.assertEquals(0.5f, Preferences.getCurrent().getMusicVolume(), 0.0001);
        optionsFrameLogic.setMusicVolume(70);
        Assert.assertEquals(0.7f, Preferences.getCurrent().getMusicVolume(), 0.0001);
        Assert.assertEquals(70, optionsFrameLogic.getMusicVolume());
    }

}
