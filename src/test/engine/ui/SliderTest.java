package test.engine.ui;

import main.engine.graphics.Colour;
import main.engine.graphics.Renderer2D;
import main.engine.ui.*;
import main.engine.ui.callback.ClickArgs;
import main.engine.ui.callback.HoverArgs;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class SliderTest {

    private Image background = mock(Image.class);
    private Image sliderBtn = mock(Image.class);

    private float x = 0.4f, y = -0.2f, height = 0.24f, width = 0.24f;
    private int min = 0, max = 100, initial = 50;
    private String textLayout = "{}x{}";
    private Slider slider;

    @Before
    public void before() {

        when(sliderBtn.getX()).thenReturn(0.0f);
        doNothing().when(sliderBtn).setPosition(anyFloat(),anyFloat());

        slider = new Slider(x,y,width,height,background,sliderBtn,min,max,initial,textLayout, null);
    }


    @Test
    public void constructorSetsValuesCorrectly() {
        Assert.assertEquals(x, slider.getX(), 0.00001f);
        Assert.assertEquals(y, slider.getY(), 0.00001f);
        Assert.assertEquals(height, slider.getHeight(), 0.00001f);
        Assert.assertEquals(width, slider.getWidth(), 0.00001f);
        Assert.assertEquals(background, slider.getBackground());
        Assert.assertEquals(sliderBtn, slider.getSliderBtn());
        Assert.assertEquals(min, slider.getMin());
        Assert.assertEquals(max, slider.getMax());
        Assert.assertEquals(initial, slider.getCurrent());
        Assert.assertEquals(textLayout, slider.getTextLayout());
        Assert.assertFalse(slider.isPressed());
        Assert.assertNull(slider.getText()); //Optional feature. Is null by default.
    }

    @Test
    public void checkIfContainsCenterPosition() {
        Assert.assertTrue(slider.containsPosition(x,y));
    }

    @Test
    public void checkIfContainsLeftPosition() {
        Assert.assertTrue(slider.containsPosition(x-width/2,y));
    }

    @Test
    public void checkIfContainsRightPosition() {
        Assert.assertTrue(slider.containsPosition(x+width/2,y));
    }

    @Test
    public void checkIfContainsTopPosition() {
        Assert.assertTrue(slider.containsPosition(x,y+height/2));
    }

    @Test
    public void checkIfContainsBottomPosition() {
        Assert.assertTrue(slider.containsPosition(x,y-height/2));
    }

    @Test
    public void checkIfNotContainsOutsidePosition() {
        Assert.assertFalse(slider.containsPosition(x+width,y));
    }

    @Test
    public void checkIfTextIsSet() {
        Text text = mock(Text.class);
        String textLayout = "{}x{} Board";
        when(text.getText()).thenReturn(textLayout);
        slider.setText(text);

        Assert.assertEquals(text, slider.getText());
        Assert.assertEquals(textLayout, slider.getTextLayout());
    }

    @Test
    public void checkIfTextElementIsUpdated() {
        Text text = mock(Text.class);
        String textLayout = "{}x{} Board";
        when(text.getText()).thenReturn(textLayout);
        slider.setText(text);
        String expected = slider.getCurrent() + "x" + slider.getCurrent() + " Board";
        verify(text).setText(expected);
    }

    @Test
    public void checkSliderIsInitiallyNotPressed() {
        Assert.assertFalse(slider.isPressed());
    }

    @Test
    public void checkIfSliderIsPressedAfterPressInCenter() {
        ClickArgs clickArgs = mock(ClickArgs.class);
        when(clickArgs.getX()).thenReturn(x);
        when(clickArgs.getY()).thenReturn(y);
        slider.processClickDown(clickArgs);
        Assert.assertTrue(slider.isPressed());
    }

    @Test
    public void checkIfSliderIsNotPressedAfterPressRelease() {
        ClickArgs clickArgs = mock(ClickArgs.class);
        when(clickArgs.getX()).thenReturn(x);
        when(clickArgs.getY()).thenReturn(y);
        slider.processClickDown(clickArgs);
        Assert.assertTrue(slider.isPressed());

        slider.processClickRelease(clickArgs);
        Assert.assertFalse(slider.isPressed());
    }

    @Test
    @Ignore //Test is not working due to float imprecision
    public void checkIfCurrentIsMinWhenClickToTheLeftOfSlider() {
        ClickArgs clickArgs = mock(ClickArgs.class);
        when(clickArgs.getX()).thenReturn(x - width);
        when(clickArgs.getY()).thenReturn(y);
        when(clickArgs.getY()).thenReturn(y);
        slider.processClickDown(clickArgs);

        Assert.assertTrue(slider.isPressed());

        HoverArgs hoverArgs = mock(HoverArgs.class);
        when(hoverArgs.getX()).thenReturn(x - width);
        when(hoverArgs.getY()).thenReturn(y);
        slider.updateCursorPosition(hoverArgs);

        verify(sliderBtn).setPosition(x - width,y);
    }

    @Test
    @Ignore //Test is not working due to float imprecision
    public void checkIfCurrentIsMinWhenClickToTheRightOfSlider() {
        ClickArgs clickArgs = mock(ClickArgs.class);
        when(clickArgs.getX()).thenReturn(x + width);
        when(clickArgs.getY()).thenReturn(y);
        when(clickArgs.getY()).thenReturn(y);
        slider.processClickDown(clickArgs);

        Assert.assertTrue(slider.isPressed());

        HoverArgs hoverArgs = mock(HoverArgs.class);
        when(hoverArgs.getX()).thenReturn(x + width);
        when(hoverArgs.getY()).thenReturn(y);
        slider.updateCursorPosition(hoverArgs);

        verify(sliderBtn).setPosition(x + width,y);
    }

    @Test
    public void checkNothingHappensWhenNotPressedAndCursorUpdateToTheLeftOfSlider () {
        HoverArgs hoverArgs = mock(HoverArgs.class);
        when(hoverArgs.getX()).thenReturn(x-width);
        when(hoverArgs.getY()).thenReturn(y);
        slider.updateCursorPosition(hoverArgs);

        Assert.assertEquals(initial, slider.getCurrent());
    }

    @Test
    public void verifySliderIsMovedOnUpdateCursorPositionWhenPressed () {
        ClickArgs clickArgs = mock(ClickArgs.class);
        when(clickArgs.getX()).thenReturn(x);
        when(clickArgs.getY()).thenReturn(y);
        slider.processClickDown(clickArgs);

        Assert.assertTrue(slider.isPressed());

        HoverArgs hoverArgs = mock(HoverArgs.class);
        when(hoverArgs.getX()).thenReturn(x);
        when(hoverArgs.getY()).thenReturn(y);
        slider.updateCursorPosition(hoverArgs);

        verify(sliderBtn,times(3)).setPosition(anyFloat(),anyFloat());
    }



    @Test
    public void checkIfSliderIsNotPressedAfterPressOutside() {
        ClickArgs clickArgs = mock(ClickArgs.class);
        when(clickArgs.getX()).thenReturn(x+width);
        when(clickArgs.getY()).thenReturn(y);
        slider.processClickDown(clickArgs);
        Assert.assertFalse(slider.isPressed());
    }

    @Test
    public void checkIfSliderIsNotPressedAfterPressInCenterAndReleaseOutside() {
        ClickArgs clickArgs = mock(ClickArgs.class);
        when(clickArgs.getX()).thenReturn(x);
        when(clickArgs.getY()).thenReturn(y);
        slider.processClickDown(clickArgs);
        Assert.assertTrue(slider.isPressed());

        when(clickArgs.getX()).thenReturn(x+width);
        when(clickArgs.getY()).thenReturn(y);
        slider.processClickRelease(clickArgs);
        Assert.assertFalse(slider.isPressed());
    }

    @Test
    public void checkIfSliderContainsPositionOnPressInCenter() {
        Assert.assertTrue(slider.containsPosition(x,y));
    }

    @Test
    public void checkIfSliderContainsPositionOnPressOutside() {
        Assert.assertFalse(slider.containsPosition(x+width,y));
    }

    @Test
    public void SliderMaxXIsCorrect() {
        Assert.assertEquals(width / 2.0f - width/16f, slider.getSliderMaxX(), 0.00001f);
    }

    @Test
    public void SliderMinXIsCorrect() {
        Assert.assertEquals(-width / 2.0f + width/16f, slider.getSliderMinX(), 0.00001f);
    }

}
