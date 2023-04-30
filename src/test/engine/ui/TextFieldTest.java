package test.engine.ui;

import main.engine.graphics.Texture;
import main.engine.input.Controls;
import main.engine.input.ControlsArgs;
import main.engine.ui.*;
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
        TextField textField = new TextField(x, y, width, height, text, image);

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
        TextField textField = new TextField(x, y, width, height, text, image);

        Assert.assertTrue(textField.containsPosition(x,y));
    }

    @Test
    public void initialTextIsDefaultText() {
        Text text = mock(Text.class);
        Image image = mock(Image.class);
        float x = 0.4f, y = -0.2f, height = 0.24f, width = 0.24f;
        TextField textField = new TextField(x, y, width, height, text, image);

        assertEquals(textField.getDefaultString(), textField.getText());
    }

    @Test
    public void initiallyNotFocused() {
        Text text = mock(Text.class);
        Image image = mock(Image.class);
        float x = 0.4f, y = -0.2f, height = 0.24f, width = 0.24f;
        TextField textField = new TextField(x, y, width, height, text, image);

        Assert.assertFalse(textField.isFocused());
    }
    @Test
    public void textAfterTextInputWhenUnfocusedNotChanging() {
        Text text = mock(Text.class);
        Image image = mock(Image.class);
        float x = 0.4f, y = -0.2f, height = 0.24f, width = 0.24f;
        TextField textField = new TextField(x, y, width, height, text, image);
        TextInputArgs textInputArgs = new TextInputArgs('a');
        textField.processTextInput(textInputArgs);
        assertEquals(textField.getDefaultString(), textField.getText());
    }
    @Test
    public void textFieldFocusedWhenClicked() {
        Text text = mock(Text.class);
        Image image = mock(Image.class);
        float x = 0.4f, y = -0.2f, height = 0.24f, width = 0.24f;
        TextField textField = new TextField(x, y, width, height, text, image);
        ClickArgs clickArgs = new ClickArgs(x,y);
        textField.processClick(clickArgs);

        Assert.assertTrue(textField.isFocused());
    }

    @Test
    public void textFieldEmptyWhenInitiallyFocused() {
        Text text = mock(Text.class);
        Image image = mock(Image.class);
        float x = 0.4f, y = -0.2f, height = 0.24f, width = 0.24f;
        TextField textField = new TextField(x, y, width, height, text, image);
        ClickArgs clickArgs = new ClickArgs(x,y);
        textField.processClick(clickArgs);
        Assert.assertTrue(textField.isFocused());
        assertEquals(null, textField.getText());
    }
    @Test
    public void textFieldChangedWhenFocusedAndTextInput() {
        Text text = mock(Text.class);
        Image image = mock(Image.class);
        float x = 0.4f, y = -0.2f, height = 0.24f, width = 0.24f;
        TextField textField = new TextField(x, y, width, height, text, image);
        ClickArgs clickArgs = new ClickArgs(x,y);
        textField.processClick(clickArgs);

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
        TextField textField = new TextField(x, y, width, height, text, image);
        ClickArgs clickArgs = new ClickArgs(x,y);
        textField.processClick(clickArgs);

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
        TextField textField = new TextField(x, y, width, height, text, image);
        ClickArgs clickArgs = new ClickArgs(x,y);
        textField.processClick(clickArgs);

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
        TextField textField = new TextField(x, y, width, height, text, image);
        textField.processClick(new ClickArgs(x,y));
        Assert.assertTrue(textField.isFocused());

        textField.processClick(new ClickArgs(x + 2 * width,y + 2 * height));
        Assert.assertFalse(textField.isFocused());
        assertEquals(textField.getDefaultString(), textField.getText());
    }
    @Test
    public void textFieldNotChangedAfterFucusTextInputAndUnfocus() {
        Text text = mock(Text.class);
        Image image = mock(Image.class);
        float x = 0.4f, y = -0.2f, height = 0.24f, width = 0.24f;
        TextField textField = new TextField(x, y, width, height, text, image);
        textField.processClick(new ClickArgs(x,y));
        Assert.assertTrue(textField.isFocused());

        textField.processTextInput(new TextInputArgs('a'));
        assertEquals("a", textField.getText());

        textField.processClick(new ClickArgs(x + 2 * width,y + 2 * height));
        Assert.assertFalse(textField.isFocused());
        assertEquals("a", textField.getText());
    }
    @Test
    public void textFieldUnfocusedWhenEscapedClicked() {
        Text text = mock(Text.class);
        Image image = mock(Image.class);
        float x = 0.4f, y = -0.2f, height = 0.24f, width = 0.24f;
        TextField textField = new TextField(x, y, width, height, text, image);

        textField.processClick(new ClickArgs(x,y));
        Assert.assertTrue(textField.isFocused());

        textField.processControlsInput(new ControlsArgs(Controls.ESCAPE));
        Assert.assertFalse(textField.isFocused());
    }
}
