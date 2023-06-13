package test.engine.ui;

import main.engine.TimeRecord;
import main.engine.graphics.Texture;
import main.engine.input.Controls;
import main.engine.input.ControlsArgs;
import main.engine.ui.*;
import main.engine.ui.callback.ClickArgs;
import main.engine.ui.callback.TextInputArgs;

import org.junit.Assert;
import org.junit.Test;

import static main.engine.Utility.floatEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class TextFieldTest {

    @Test
    public void constructorSetsValuesCorrectly() {
        Text text = mock(Text.class);
        Image image = mock(Image.class);
        float x = 0.4f, y = -0.2f, height = 0.24f, width = 0.24f;
        TextField textField = new TextField(x, y, width, height, text, image, "default",0.6f);

        doNothing().when(text).setAnchorPoint(AnchorPoint.Left);
        doNothing().when(text).setPosition(anyFloat(),anyFloat());
        assertEquals(text, textField.getTextElement());
        assertEquals(image, textField.getImageElement());
    }

    @Test
    public void checkIfContainsCenterPosition() {
        Text text = mock(Text.class);
        Image image = mock(Image.class);
        float x = 0.4f, y = -0.2f, height = 0.24f, width = 0.24f;
        TextField textField = new TextField(x, y, width, height, text, image, "default",0.6f);

        Assert.assertTrue(textField.containsPosition(x,y));
    }

    @Test
    public void initialTextIsDefaultText() {
        Text text = mock(Text.class);
        Image image = mock(Image.class);
        float x = 0.4f, y = -0.2f, height = 0.24f, width = 0.24f;
        TextField textField = new TextField(x, y, width, height, text, image, "default",0.6f);

        assertEquals(textField.getDefaultString(), textField.getText());
    }

    @Test
    public void initiallyNotFocused() {
        Text text = mock(Text.class);
        Image image = mock(Image.class);
        float x = 0.4f, y = -0.2f, height = 0.24f, width = 0.24f;
        TextField textField = new TextField(x, y, width, height, text, image, "default",0.6f);

        Assert.assertFalse(textField.isFocused());
    }
    @Test
    public void textAfterTextInputWhenUnfocusedNotChanging() {
        Text text = mock(Text.class);
        Image image = mock(Image.class);
        float x = 0.4f, y = -0.2f, height = 0.24f, width = 0.24f;
        TextField textField = new TextField(x, y, width, height, text, image, "default",0.6f);
        TextInputArgs textInputArgs = new TextInputArgs('a');
        textField.processTextInput(textInputArgs);
        assertEquals(textField.getDefaultString(), textField.getText());
    }
    @Test
    public void textFieldFocusedWhenClicked() {
        Text text = mock(Text.class);
        Image image = mock(Image.class);
        float x = 0.4f, y = -0.2f, height = 0.24f, width = 0.24f;
        TextField textField = new TextField(x, y, width, height, text, image, "default",0.6f);
        ClickArgs clickArgs = new ClickArgs(x,y);
        textField.processClickRelease(clickArgs);

        Assert.assertTrue(textField.isFocused());
    }

    @Test
    public void textFieldEmptyWhenInitiallyFocused() {
        Text text = mock(Text.class);
        Image image = mock(Image.class);
        float x = 0.4f, y = -0.2f, height = 0.24f, width = 0.24f;
        String defaultString = "default";
        TextField textField = new TextField(x, y, width, height, text, image, defaultString,0.6f);
        ClickArgs clickArgs = new ClickArgs(x,y);
        textField.processClickRelease(clickArgs);
        Assert.assertTrue(textField.isFocused());
        assertEquals(defaultString, textField.getText());
    }
    @Test
    public void textFieldChangedWhenFocusedAndTextInput() {
        Text text = mock(Text.class);
        Image image = mock(Image.class);
        float x = 0.4f, y = -0.2f, height = 0.24f, width = 0.24f;
        TextField textField = new TextField(x, y, width, height, text, image, "default",0.6f);
        ClickArgs clickArgs = new ClickArgs(x,y);
        textField.processClickRelease(clickArgs);

        TextInputArgs textInputArgs = new TextInputArgs('a');
        textField.processTextInput(textInputArgs);

        Assert.assertTrue(textField.isFocused());
        assertEquals("a", textField.getText());
    }
    @Test
    public void textFieldChangedWhenFocusedAndMultipleTextInput() {
        Text text = mock(Text.class);
        Image image = mock(Image.class);
        float x = 0.4f, y = -0.2f, height = 0.24f, width = 0.24f;
        TextField textField = new TextField(x, y, width, height, text, image, "default",0.6f);
        ClickArgs clickArgs = new ClickArgs(x,y);
        textField.processClickRelease(clickArgs);

        textField.processTextInput(new TextInputArgs('a'));
        textField.processTextInput(new TextInputArgs('b'));

        Assert.assertTrue(textField.isFocused());
        assertEquals("ab", textField.getText());
    }
    @Test
    public void textFieldChangedWhenFocusedAndMultipleTextInputWithBackspace() {
        Text text = mock(Text.class);
        Image image = mock(Image.class);
        float x = 0.4f, y = -0.2f, height = 0.24f, width = 0.24f;
        TextField textField = new TextField(x, y, width, height, text, image, "default",0.6f);
        ClickArgs clickArgs = new ClickArgs(x,y);
        textField.processClickRelease(clickArgs);

        textField.processTextInput(new TextInputArgs('a'));
        textField.processTextInput(new TextInputArgs('b'));
        textField.processTextInput(new TextInputArgs('\b'));
        textField.processTextInput(new TextInputArgs('c'));

        Assert.assertTrue(textField.isFocused());
        assertEquals("ac", textField.getText());
    }

    @Test
    public void textFieldChangedToDefaultAfterFucusAndUnfocus() {
        Text text = mock(Text.class);
        Image image = mock(Image.class);
        float x = 0.4f, y = -0.2f, height = 0.24f, width = 0.24f;
        TextField textField = new TextField(x, y, width, height, text, image, "default",0.6f);
        textField.processClickRelease(new ClickArgs(x,y));
        Assert.assertTrue(textField.isFocused());

        textField.processClickRelease(new ClickArgs(x + 2 * width,y + 2 * height));
        Assert.assertFalse(textField.isFocused());
        assertEquals(textField.getDefaultString(), textField.getText());
    }
    @Test
    public void textFieldNotChangedAfterFucusTextInputAndUnfocus() {
        Text text = mock(Text.class);
        Image image = mock(Image.class);
        float x = 0.4f, y = -0.2f, height = 0.24f, width = 0.24f;
        TextField textField = new TextField(x, y, width, height, text, image, "default",0.6f);
        textField.processClickRelease(new ClickArgs(x,y));
        Assert.assertTrue(textField.isFocused());

        textField.processTextInput(new TextInputArgs('a'));
        assertEquals("a", textField.getText());

        textField.processClickRelease(new ClickArgs(x + 2 * width,y + 2 * height));
        Assert.assertFalse(textField.isFocused());
        assertEquals("a", textField.getText());
    }
    @Test
    public void textFieldUnfocusedWhenEscapedClicked() {
        Text text = mock(Text.class);
        Image image = mock(Image.class);
        float x = 0.4f, y = -0.2f, height = 0.24f, width = 0.24f;
        TextField textField = new TextField(x, y, width, height, text, image, "default",0.6f);

        textField.processClickRelease(new ClickArgs(x,y));
        Assert.assertTrue(textField.isFocused());

        textField.processControlsInput(new ControlsArgs(Controls.ESCAPE));
        Assert.assertFalse(textField.isFocused());
    }

    @Test
    public void updateTimeTillNextUpdate() {
        Text text = mock(Text.class);
        Image image = mock(Image.class);
        float x = 0.4f, y = -0.2f, height = 0.24f, width = 0.24f, animationTime = 0.6f;
        TextField textField = new TextField(x, y, width, height, text, image, "default", animationTime);

        textField.processClickRelease(new ClickArgs(x,y));
        Assert.assertTrue(textField.isFocused());

        textField.processTextInput(new TextInputArgs('a'));
        assertEquals("a", textField.getText());

        //First time not updated
        TimeRecord timeRecord = mock(TimeRecord.class);
        when(timeRecord.elapsedSeconds()).thenReturn(2 * animationTime/3.0f);
        textField.update(timeRecord);
        Assert.assertEquals(animationTime / 3.0f, textField.getTimeTillNextUpdate(), 0.0001f);
        Assert.assertEquals("a", textField.getText());

        //Visual inspection done, to ensure blinking animation works.

        //Second time updated
        textField.update(timeRecord);
        Assert.assertEquals(animationTime, textField.getTimeTillNextUpdate(), 0.0001f);

        //Third time updated
        textField.update(timeRecord);
        Assert.assertEquals(animationTime / 3.0f, textField.getTimeTillNextUpdate(), 0.0001f);

        //Fourth time back to scratch
        textField.update(timeRecord);
        Assert.assertEquals(animationTime, textField.getTimeTillNextUpdate(), 0.0001f);
    }

    @Test
    public void testTextTruncation() {
        Text text = mock(Text.class);
        Image image = mock(Image.class);
        float x = 0.4f, y = -0.2f, height = 0.24f, width = 0.24f, animationTime = 0.6f;
        TextField textField = new TextField(x, y, width, height, text, image, "0123456789a", animationTime);
        textField.processClickRelease(new ClickArgs(x,y));

        Assert.assertEquals(11, textField.getText().length());

        TextInputArgs inputArgs = mock(TextInputArgs.class);
        when(inputArgs.getCharacter()).thenReturn('a');

        for(int i = 0; i < 12; i++) {
            textField.processTextInput(inputArgs);
            Assert.assertEquals(i + 1, textField.getText().length());
        }

        textField.processTextInput(inputArgs);
        Assert.assertEquals(12, textField.getText().length());
    }


}
