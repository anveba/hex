package main.engine.ui;

import main.engine.TimeRecord;
import main.engine.format.Format;
import main.engine.graphics.Colour;
import main.engine.graphics.Renderer2D;
import main.engine.graphics.Texture;
import main.engine.input.Controls;
import main.engine.input.ControlsArgs;
import main.engine.ui.callback.ButtonCallback;
import main.engine.ui.callback.ButtonCallbackArgs;
import main.engine.ui.callback.ClickArgs;
import main.engine.ui.callback.HoverArgs;
import main.engine.ui.callback.SliderCallback;
import main.engine.ui.callback.SliderCallbackArgs;
import main.engine.ui.callback.TextInputArgs;
import main.hex.resources.TextureLibrary;

/**
 *
 * The slider is a UI element that allows the user to select a value from a range of values.
 * The slider is used in the options frame, to allow the user to change the volume of the game, and in the
 * startGameFrame, to allow the user to select the size of the board + the time limit.
 *
 * @Author Oliver Grønborg Christensen - s204479
 */


public class Slider extends RectElement implements Clickable {

    private SliderCallback sliderChangedCallback;
    private Image background, sliderBtn;
    private Text text;
    private String textLayout;
    private boolean isPressed;
    private int min, max, current;
    private float sliderMaxX, sliderMinX;
    private Format format;

    public Slider(float x, float y, float width,float height, 
    		Texture backgroundTexture, Texture sliderTexture, 
    		int min, int max, int initial, SliderCallback sliderChangedCallback) {
        this(x,
             y,
             width,
             height,
             new Image(0.0f, 0.0f, width, height, backgroundTexture),
             new Image(0.0f, 0.0f, height, height, sliderTexture), //We want the sliderBtn to be a square of height * height.
             min,
             max,
             initial,
             "{}x{}",
             sliderChangedCallback
        );

    }

    public Slider(float x, float y, float width, float height, Image background, Image sliderBtn, 
    		int min, int max, int initial, String textLayout, SliderCallback sliderChangedCallback) {
        super(x, y, width, height);
        this.background = background;
        this.sliderBtn = sliderBtn;
        sliderMaxX = width / 2.0f - width / 16f;
        sliderMinX = -width / 2.0f + width / 16f;
        this.min = min;
        this.max = max;
        this.current = initial;

        float initialPercent = (float) (current - min) / (max - min);
        setSliderPercent(initialPercent);

        this.format = null;

        this.text = null;
        this.textLayout = textLayout; // {} is replaced with current value

        this.sliderChangedCallback = sliderChangedCallback;

        isPressed = false;
    }

    public void setText(Text text) {
    	this.text = text;
        textLayout = text.getText();
        updateText();
    }

    private void updateText() {

        String replacer = (format == null) ? Integer.toString(current) : format.formatInt(current);

        if(text != null) text.setText(textLayout.replace("{}", replacer));
    }

    private void moveSlider(float x) {
        float sliderX = x;
        if(x < sliderMinX) {
            sliderX = sliderMinX;
        } else if (x > sliderMaxX) {
            sliderX = sliderMaxX;
        }
        sliderBtn.setPosition(sliderX, 0.0f);
        int newCurrent = (int) (min + (max - min) * getSliderPercent());
        if(current != newCurrent) {
        	current = newCurrent;
        	updateText();
        }
    }

    public float getSliderPercent() {
        return (sliderBtn.getX() - sliderMinX) / (sliderMaxX - sliderMinX);
    }

    private void setSliderPercent(float percent) {
    	moveSlider((sliderMaxX - sliderMinX) * percent - (sliderMaxX - sliderMinX) / 2);
    }


    @Override
    public void updateCursorPosition(HoverArgs args) {
        if(!isPressed) return;
        moveSlider(args.getX() - getX());
    }

    @Override
    public void processClickDown(ClickArgs args) {
        if(!containsPosition(args.getX(), args.getY())) return;
        isPressed = true;
        moveSlider(args.getX() - getX());
    }

    @Override
    public void processClickRelease(ClickArgs args) {
        if(!isPressed) return;

        isPressed = false;

        if (sliderChangedCallback != null) {
            sliderChangedCallback.call(new SliderCallbackArgs(current));
        }
    }

    @Override
    public boolean containsPosition(float x, float y) {
        return this.getX() - getWidth() / 2.0f <= x
                && this.getY() - getHeight() / 2.0f <= y
                && this.getX() + getWidth() / 2.0f >= x
                && this.getY() + getHeight() / 2.0f >= y;
    }

    @Override
    protected void draw(Renderer2D renderer, float offsetX, float offsetY, Colour colour) {
        background.draw(renderer, offsetX + getX(), offsetY + getY(), colour);
        sliderBtn.draw(renderer, offsetX + getX(), offsetY + getY(), colour);
        if(text != null) text.draw(renderer, offsetX + getX(), offsetY + getY(), colour);
    }

    @Override
    public void update(TimeRecord elapsed) {

    }

    public void setFormat(Format format) {
        this.format = format;
        updateText();
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getCurrent() {
        return current;
    }

    public Text getText() {
        return text;
    }

    public String getTextLayout() {
        return textLayout;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public float getSliderMaxX() {
        return sliderMaxX;
    }

    public float getSliderMinX() {
        return sliderMinX;
    }

    public Image getBackground() {
        return background;
    }

    public Image getSliderBtn() {
        return sliderBtn;
    }

	@Override
	public void processTextInput(TextInputArgs args) {
		
	}

	@Override
	public void processControlsInput(ControlsArgs args) {
		
	}
}
