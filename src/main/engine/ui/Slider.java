package main.engine.ui;

import main.engine.TimeRecord;
import main.engine.graphics.Colour;
import main.engine.graphics.Renderer2D;
import main.engine.graphics.Texture;
import main.engine.input.Controls;
import main.engine.input.ControlsArgs;
import main.hex.resources.TextureLibrary;

public class Slider extends RectElement implements Dragable {
    private Image background, sliderBtn;
    private Text text;
    private String textLayout;
    private boolean isPressed;
    private int min, max, current;
    private float sliderMaxX, sliderMinX;

    public Slider(float x, float y, float width,float height, Texture backgroundTexture, Texture sliderTexture, int min, int max, int initial) {
        this(x,
             y,
             width,
             height,
             new Image(x, y, width, height, backgroundTexture),
             new Image(x, y, height, height, sliderTexture), //We want the sliderBtn to be a square of height * height.
             min,
             max,
             initial,
             "{}x{}"
        );

    }

    public Slider(float x, float y, float width, float height, Image background, Image sliderBtn, int min, int max, int initial, String textLayout) {
        super(x, y, width, height);
        this.background = background;
        this.sliderBtn = sliderBtn;
        sliderMaxX = x + width / 2.0f - width/16f;
        sliderMinX = x - width / 2.0f + width/16f;
        this.min = min;
        this.max = max;
        this.current = initial;

        float initialPercent = (float) (current - min) / (max - min);
        setSliderPercent(initialPercent);

        this.text = null;
        this.textLayout = textLayout; // {} is replaced with current value

        isPressed = false;
    }

    public void setText(Text text) {
    	this.text = text;
        textLayout = text.getText();
        updateText();
    }

    private void updateText() {
        if(text != null) text.setText(textLayout.replace("{}", current + ""));
    }

    private void moveSlider(float x) {
        float sliderX = x;
        if(x < sliderMinX) {
            sliderX = sliderMinX;
        } else if (x > sliderMaxX) {
            sliderX = sliderMaxX;
        }
        sliderBtn.setPosition(sliderX, this.getY());
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
    	moveSlider(this.getX() + (sliderMaxX - sliderMinX) * percent - (sliderMaxX - sliderMinX) / 2);
    }


    @Override
    public void updateCursorPosition(HoverArgs args) {
        if(!isPressed) return;
        moveSlider(args.getX());
    }

    @Override
    public void processPress(ClickArgs args) {
        if(!containsPosition(args.getX(), args.getY())) return;
        isPressed = true;
        moveSlider(args.getX());
    }

    @Override
    public void processRelease(ClickArgs args) {
        if(!isPressed) return;

        isPressed = false;
    }

    @Override
    public boolean containsPosition(float x, float y) {
        return this.getX() - getWidth() / 2.0f <= x
                && this.getY() - getHeight() / 2.0f <= y
                && this.getX() + getWidth() / 2.0f >= x
                && this.getY() + getHeight() / 2.0f >= y;
    }

    @Override
    protected void drawElement(Renderer2D renderer, float offsetX, float offsetY, Colour colour) {
        background.draw(renderer, offsetX, offsetY, colour);
        sliderBtn.draw(renderer, offsetX, offsetY, colour);
        if(text != null) text.draw(renderer, offsetX, offsetY, colour);
    }

    @Override
    public void update(TimeRecord elapsed) {

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
}
