package main.engine.ui;

import main.engine.TimeRecord;
import main.engine.font.BitmapFont;
import main.engine.graphics.Colour;
import main.engine.graphics.Renderer2D;
import main.engine.input.Controls;
import main.engine.input.ControlsArgs;
import main.engine.ui.callback.ClickArgs;
import main.engine.ui.callback.HoverArgs;
import main.engine.ui.callback.TextInputArgs;
import main.hex.resources.TextureLibrary;

/**
 *
 * The text field is a UI element that allows the user to input text, as known from any text editor.
 * The text field is used in the options frame, to allow the user to change the name of the player, and
 * could easily be used in other frames as well.
 *
 * @Author Oliver Grønborg Christensen - s204479
 */


public class TextField extends RectElement implements Clickable {

    private boolean isFocused;
    private StringBuilder textString = new StringBuilder();
    private String defaultString;
    private Text text;
    private Image image;
    private float animationTime;
    private float timeTillNextUpdate;
    private boolean textAnimationLineShown = false;
    
    public TextField(float x, float y, BitmapFont font, String defaultString, float width, float height, Colour lineColour) {
        this(  x,
                y,
                width,
                height,
                new Text(0.0f, 0.0f, font, defaultString, height, lineColour),
                new Image(0.0f, -height/1.25f, width, height/8, TextureLibrary.WHITE_SQUARE.getTexture()),
                defaultString,
                0.6f
        );
    }

    public TextField(float x, float y, float width, float height, Text text, Image image, String defaultString, float animationTime) {
        super(x,y,width,height);
        this.text = text;
        this.text.setAnchorPoint(AnchorPoint.Left);
        this.text.setPosition(-width / 2.0f, 0.0f);

        this.image = image;

        this.defaultString = defaultString;
        textString = new StringBuilder();

        this.animationTime = animationTime;
        timeTillNextUpdate = animationTime;
    }

    private void focus() {
    	if(!isFocused) {
            text.setText(textString.toString());
            isFocused = true;
        }
    }
    
    private void unfocus() {
        if(!isFocused) return;

        text.setText((textString.isEmpty()) ? defaultString : textString.toString());
        isFocused = false;
    }

    @Override
    protected void drawElement(Renderer2D renderer, float offsetX, float offsetY, Colour colour) {
        image.draw(renderer, offsetX + getX(), offsetY + getY(), colour);
        text.draw(renderer, offsetX + getX(), offsetY + getY(), colour);
    }
    
	@Override
	public void processClickDown(ClickArgs args) {
		
	}

    @Override
    public void processClickRelease(ClickArgs args) {
    	if (containsPosition(args.getX(), args.getY()))
	        focus();
    	else 
    		unfocus();
    }

    @Override
    public boolean containsPosition(float x, float y) {
        return this.getX() - 1.0f / 2.0f <= x
                && this.getY() - getHeight() / 2.0f <= y
                && this.getX() + 1.0f / 2.0f >= x
                && this.getY() + getHeight() / 2.0f >= y;
    }

    @Override
    public void updateCursorPosition(HoverArgs args) {

    }
    
    @Override
	public void processControlsInput(ControlsArgs args) {
		if (args.getControls() == Controls.ESCAPE)
			unfocus();
	}

	@Override
	public void processTextInput(TextInputArgs args) {
        if(!isFocused) return;

        if (args.getCharacter() == '\b') { //backspace
        	if (textString.length() > 0)
        		textString.deleteCharAt(textString.length() - 1);
        }
        else {
            textString.append(args.getCharacter());
        }

        text.setText(textString.toString());
        truncateTextIfTooLong();
	}

    private void truncateTextIfTooLong() {
        if (textString.length() > 12) {
            textString.deleteCharAt(textString.length() - 1);
            text.setText(textString.toString());
        }
    }

    @Override
    protected void updateElement(TimeRecord elapsed) {
        if (!isFocused) return;

        timeTillNextUpdate -= elapsed.elapsedSeconds();
        if(timeTillNextUpdate > 0.0f) return;

        //Update cursor animation
        textAnimationLineShown = !textAnimationLineShown;
        text.setText(textAnimationLineShown ? textString.toString() + "|" : textString.toString());

        timeTillNextUpdate = animationTime;
    }
    public Text getTextElement() {
        return text;
    }
    public Image getImageElement() {
        return image;
    }
    public String getText() {
    	return textString.isEmpty() ? defaultString : textString.toString();
    }

    public String getDefaultString() {
        return defaultString;
    }

    public float getTimeTillNextUpdate() {
        return timeTillNextUpdate;
    }

    public boolean isFocused() {
        return isFocused;
    }
}
