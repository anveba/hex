package test.engine.ui;

import main.engine.EngineException;
import main.engine.graphics.Colour;
import main.engine.graphics.Texture;
import main.engine.ui.ToggleSwitch;
import main.engine.ui.callback.ButtonCallback;
import main.engine.ui.callback.ClickArgs;
import main.engine.ui.callback.HoverArgs;
import org.junit.Before;
import org.junit.Test;

import static main.engine.Utility.floatEquals;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.*;

public class ToggleSwitchTest {

    private final float x = 0.2f, y = -0.5f, width = 0.4f, height = 0.15f;
    private final boolean initialToggleOn = true;
    private Texture t1, t2;
    private Colour c1, c2;
    private ButtonCallback callback;

    @Before
    public void setup() {
        t1 = mock(Texture.class);
        t2 = mock(Texture.class);

        c1 = mock(Colour.class);
        c2 = mock(Colour.class);

        callback = mock(ButtonCallback.class);
    }

    @Test
    public void constructorSetsValuesCorrectly() {
        ToggleSwitch toggleSwitch = new ToggleSwitch(x, y, width, height, initialToggleOn,
        t1, t2, c1, c2, null, null, null, null);


        assertTrue(floatEquals(x, toggleSwitch.getX()));
        assertTrue(floatEquals(y, toggleSwitch.getY()));
        assertTrue(floatEquals(width, toggleSwitch.getWidth()));
        assertTrue(floatEquals(height, toggleSwitch.getHeight()));

        assertEquals(t1, toggleSwitch.getBackgroundImage().getTexture());
        assertEquals(t2, toggleSwitch.getForegroundImage().getTexture());

        assertEquals(initialToggleOn, toggleSwitch.getToggleSwitchOn());
    }

    @Test
    public void updatesPositionCorrectly() {
        ToggleSwitch toggleSwitch = new ToggleSwitch(999.0f, 999.0f, width, height, initialToggleOn,
                t1, t2, c1, c2, null, null, null, null);

        toggleSwitch.setPosition(x, y);

        assertTrue(floatEquals(x, toggleSwitch.getX()));
        assertTrue(floatEquals(y, toggleSwitch.getY()));
    }

    @Test
    public void updatesWidthAndHeight() {
        ToggleSwitch toggleSwitch = new ToggleSwitch(x, y, 999.0f, 999.0f, initialToggleOn,
                t1, t2, c1, c2, null, null, null, null);

        toggleSwitch.setWidth(width);
        toggleSwitch.setHeight(height);

        assertTrue(floatEquals(width, toggleSwitch.getWidth()));
        assertTrue(floatEquals(height, toggleSwitch.getHeight()));
    }

    @Test (expected = EngineException.class)
    public void negativeWidthInConstructorThrowsException() {
        new ToggleSwitch(x, y, -width, height, initialToggleOn,
                t1, t2, c1, c2, null, null, null, null);
    }

    @Test (expected = EngineException.class)
    public void negativeHeightInConstructorThrowsException() {
        new ToggleSwitch(x, y, width, -height, initialToggleOn,
                t1, t2, c1, c2, null, null, null, null);
    }

    @Test
    public void settingNegativeWidthThrowsException() {
        ToggleSwitch toggleSwitch = new ToggleSwitch(x, y, width, height, initialToggleOn,
                t1, t2, c1, c2, null, null, null, null);

        try {
            toggleSwitch.setWidth(-0.5f);
        } catch (EngineException e) {
            return;
        }
        fail();
    }

    @Test
    public void settingNegativeHeightThrowsException() {
        ToggleSwitch toggleSwitch = new ToggleSwitch(x, y, width, height, initialToggleOn,
                t1, t2, c1, c2, null, null, null, null);

        try {
            toggleSwitch.setHeight(-0.5f);
        } catch (EngineException e) {
            return;
        }
        fail();
    }

    @Test
    public void recognizesClicksInsideSwitch() {
        ToggleSwitch toggleSwitch = new ToggleSwitch(x, y, width, height, initialToggleOn,
                t1, t2, c1, c2, null, null, null, null);

        float wHalf = width/2, hHalf = height/2;

        assertTrue(toggleSwitch.containsPosition(x, y));
        assertTrue(toggleSwitch.containsPosition(x + wHalf - 0.01f, y));
        assertTrue(toggleSwitch.containsPosition(x - wHalf + 0.01f, y));
        assertTrue(toggleSwitch.containsPosition(x, y + hHalf - 0.01f));
        assertTrue(toggleSwitch.containsPosition(x, y - hHalf + 0.01f));
    }

    @Test
    public void doesNotRecognizeClicksOutsideSwitch() {
        ToggleSwitch toggleSwitch = new ToggleSwitch(x, y, width, height, initialToggleOn,
                t1, t2, c1, c2, null, null, null, null);

        float wHalf = width/2, hHalf = height/2;

        assertFalse(toggleSwitch.containsPosition(x + wHalf + 0.01f, y));
        assertFalse(toggleSwitch.containsPosition(x - wHalf - 0.01f, y));
        assertFalse(toggleSwitch.containsPosition(x, y + hHalf + 0.01f));
        assertFalse(toggleSwitch.containsPosition(x, y - hHalf - 0.01f));
    }

    @Test
    public void toggleSwitchOnGoesFromFalseToTrueWhenClicked_initiallyDisabled() {
        ToggleSwitch toggleSwitch = spy(new ToggleSwitch(x, y, width, height, false,
                t1, t2, c1, c2, null, null, null, null));

        assertFalse(toggleSwitch.getToggleSwitchOn());

        ClickArgs args = new ClickArgs(0.0f, 0.0f);
        when(toggleSwitch.containsPosition(anyFloat(), anyFloat())).thenReturn(true); // Makes sure click is registered no matter what
        toggleSwitch.processClickRelease(args);

        assertTrue(toggleSwitch.getToggleSwitchOn());
    }

    @Test
    public void toggleSwitchOnGoesFromTrueToFalseWhenClicked_initiallyEnabled() {
        ToggleSwitch toggleSwitch = spy(new ToggleSwitch(x, y, width, height, true,
                t1, t2, c1, c2, null, null, null, null));

        assertTrue(toggleSwitch.getToggleSwitchOn());

        ClickArgs args = new ClickArgs(0.0f, 0.0f);
        when(toggleSwitch.containsPosition(anyFloat(), anyFloat())).thenReturn(true); // Makes sure click is registered no matter what
        toggleSwitch.processClickRelease(args);

        assertFalse(toggleSwitch.getToggleSwitchOn());
    }

    @Test
    public void enableCallbackCalledWhenClicked_toggleSwitchOnIsFalse() {
        ToggleSwitch toggleSwitch = spy(new ToggleSwitch(x, y, width, height, false,
                t1, t2, c1, c2, callback, null, null, null));

        verify(callback, times(0)).call(any());
        ClickArgs args = new ClickArgs(0.0f, 0.0f);
        when(toggleSwitch.containsPosition(anyFloat(), anyFloat())).thenReturn(false); // Makes sure no click is registered
        toggleSwitch.processClickRelease(args);
        verify(callback, times(0)).call(any());
        when(toggleSwitch.containsPosition(anyFloat(), anyFloat())).thenReturn(true); // Makes sure click is registered no matter what
        toggleSwitch.processClickRelease(args);
        verify(callback, times(1)).call(any());
    }

    @Test
    public void enableCallbackNotCalledWhenClicked_toggleSwitchOnIsTrue() {
        ToggleSwitch toggleSwitch = spy(new ToggleSwitch(x, y, width, height, true,
                t1, t2, c1, c2, callback, null, null, null));

        verify(callback, times(0)).call(any());
        ClickArgs args = new ClickArgs(0.0f, 0.0f);
        when(toggleSwitch.containsPosition(anyFloat(), anyFloat())).thenReturn(false); // Makes sure no click is registered
        toggleSwitch.processClickRelease(args);
        verify(callback, times(0)).call(any());
        when(toggleSwitch.containsPosition(anyFloat(), anyFloat())).thenReturn(true); // Makes sure click is registered no matter what
        toggleSwitch.processClickRelease(args);
        verify(callback, times(0)).call(any());
    }

    @Test
    public void disableCallbackCalledWhenClicked_toggleSwitchOnIsTrue() {
        ToggleSwitch toggleSwitch = spy(new ToggleSwitch(x, y, width, height, true,
                t1, t2, c1, c2, null, callback, null, null));

        verify(callback, times(0)).call(any());
        ClickArgs args = new ClickArgs(0.0f, 0.0f);
        when(toggleSwitch.containsPosition(anyFloat(), anyFloat())).thenReturn(false); // Makes sure no click is registered
        toggleSwitch.processClickRelease(args);
        verify(callback, times(0)).call(any());
        when(toggleSwitch.containsPosition(anyFloat(), anyFloat())).thenReturn(true); // Makes sure click is registered no matter what
        toggleSwitch.processClickRelease(args);
        verify(callback, times(1)).call(any());
    }

    @Test
    public void disableCallbackNotCalledWhenClicked_toggleSwitchOnIsFalse() {
        ToggleSwitch toggleSwitch = spy(new ToggleSwitch(x, y, width, height, false,
                t1, t2, c1, c2, null, callback, null, null));

        verify(callback, times(0)).call(any());
        ClickArgs args = new ClickArgs(0.0f, 0.0f);
        when(toggleSwitch.containsPosition(anyFloat(), anyFloat())).thenReturn(false); // Makes sure no click is registered
        toggleSwitch.processClickRelease(args);
        verify(callback, times(0)).call(any());
        when(toggleSwitch.containsPosition(anyFloat(), anyFloat())).thenReturn(true); // Makes sure click is registered no matter what
        toggleSwitch.processClickRelease(args);
        verify(callback, times(0)).call(any());
    }

    @Test
    public void enableAndDisableCallbackIgnoredWhenNoCallbackIsSet() {
        ToggleSwitch toggleSwitch = spy(new ToggleSwitch(x, y, width, height, false,
                t1, t2, c1, c2, null, null, null, null));

        ClickArgs args = new ClickArgs(0.0f, 0.0f);
        when(toggleSwitch.containsPosition(anyFloat(), anyFloat())).thenReturn(true); // Makes sure click is registered no matter what
        toggleSwitch.processClickRelease(args);
    }

    @Test
    public void hoverEnterCallsHoverEnterCallback() {
        ToggleSwitch toggleSwitch = spy(new ToggleSwitch(x, y, width, height, false,
                t1, t2, c1, c2, null, null, callback, null));

        HoverArgs args = new HoverArgs(0.0f, 0.0f);
        when(toggleSwitch.containsPosition(anyFloat(), anyFloat())).thenReturn(false); // Makes sure that no hover is registered
        toggleSwitch.updateCursorPosition(args);
        verify(callback, times(0)).call(any());
        when(toggleSwitch.containsPosition(anyFloat(), anyFloat())).thenReturn(true); // Makes sure that hover is registered no matter what
        toggleSwitch.updateCursorPosition(args);
        verify(callback, times(1)).call(any());
    }

    @Test
    public void hoverExitCallsHoverExitCallback() {
        ToggleSwitch toggleSwitch = spy(new ToggleSwitch(x, y, width, height, false,
                t1, t2, c1, c2, null, null, null, callback));

        HoverArgs args = new HoverArgs(0.0f, 0.0f);
        when(toggleSwitch.containsPosition(anyFloat(), anyFloat())).thenReturn(true); // Makes sure that hover is registered no matter what
        toggleSwitch.updateCursorPosition(args);
        verify(callback, times(0)).call(any());
        when(toggleSwitch.containsPosition(anyFloat(), anyFloat())).thenReturn(false); // Makes sure that no hover is registered
        toggleSwitch.updateCursorPosition(args);
        verify(callback, times(1)).call(any());
    }

    @Test
    public void hoverEnterAndHoverExitCallbackIgnoredWhenNoCallbackIsSet() {
        ToggleSwitch toggleSwitch = spy(new ToggleSwitch(x, y, width, height, false,
                t1, t2, c1, c2, null, null, null, null));

        HoverArgs args = new HoverArgs(0.0f, 0.0f);
        when(toggleSwitch.containsPosition(anyFloat(), anyFloat())).thenReturn(true); // Makes sure that hover is registered no matter what
        toggleSwitch.updateCursorPosition(args);
    }
}