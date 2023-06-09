package main.engine.ui;

import main.engine.TimeRecord;
import main.engine.graphics.Colour;
import main.engine.graphics.Renderer2D;
import main.engine.graphics.Texture;
import main.engine.input.ControlsArgs;
import main.hex.resources.TextureLibrary;

/**
 * ToggleSwitch (disabled/enabled switch) allows for something to be disabled or enabled depending on
 * how the switch is set. The switch is enabled when the circle is to the right and disabled when the switch is
 * to the left. (The initial state can be set in the constructor).
 *
 * @Author Oliver Siggaard - s204450
 */

public class ToggleSwitch extends RectElement implements Clickable {

    private ButtonCallback enableCallback, disableCallback, hoverEnterCallback, hoverExitCallback;
    private boolean isHovering, toggleSwitchOn;
    private final Image backgroundImage, foregroundImage;

    public ToggleSwitch(float x, float y, float width, float height, boolean initialToggleOn,
                        Texture backgroundTexture,
                        Texture foregroundTexture,
                        ButtonCallback enableCallback,
                        ButtonCallback disableCallback,
                        ButtonCallback onHoverEnterCallback,
                        ButtonCallback onHoverExitCallback) {
        super(x, y, width, height);
        this.toggleSwitchOn = initialToggleOn;
        backgroundImage = new Image(x, y, width * 0.9f, height * 0.9f, backgroundTexture);
        foregroundImage = new Image(x - width/2 + height/2, y, height, height, foregroundTexture);
        if (toggleSwitchOn) {
            foregroundImage.setPosition(x + width/2 - height/2, y);
        }
        setEnableCallback(enableCallback);
        setDisableCallback(disableCallback);
        setHoverEnterCallback(onHoverEnterCallback);
        setHoverExitCallback(onHoverExitCallback);
        setWidth(width);
        setHeight(height);
        setPosition(x, y);
    }

    public ToggleSwitch(float x, float y, float width, float height, boolean initialToggleOn,
                        ButtonCallback enableCallback,
                        ButtonCallback disableCallback,
                        ButtonCallback onHoverEnterCallback,
                        ButtonCallback onHoverExitCallback) {
        this (x, y, width, height, initialToggleOn,
                TextureLibrary.TOGGLE_SWITCH_BACKGROUND.getTexture(),
                TextureLibrary.TOGGLE_SWITCH_FOREGROUND.getTexture(),
                enableCallback, disableCallback, onHoverEnterCallback, onHoverExitCallback);
    }

    public void setEnableCallback(ButtonCallback callback) {
        this.enableCallback = callback;
    }

    public void setDisableCallback(ButtonCallback callback) {
        this.disableCallback = callback;
    }

    public void setHoverEnterCallback(ButtonCallback callback) {
        this.hoverEnterCallback = callback;
    }

    public void setHoverExitCallback(ButtonCallback callback) {
        this.hoverExitCallback = callback;
    }

    public boolean getToggleSwitchOn() {
        return toggleSwitchOn;
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public Image getForegroundImage() {
        return foregroundImage;
    }

    @Override
    public void processClickRelease(ClickArgs args) {
        if(isDisabled()) return;

        if (!toggleSwitchOn) {
            if (containsPosition(args.getX(), args.getY())) {
                if (enableCallback != null) {
                    enableCallback.call(new ButtonCallbackArgs());
                }
                toggleSwitchOn = true;
                foregroundImage.setPosition(getX() + getWidth()/2 - getHeight()/2, getY());
             }
        } else {
            if (containsPosition(args.getX(), args.getY())) {
                if (disableCallback != null) {
                    disableCallback.call(new ButtonCallbackArgs());
                }
                toggleSwitchOn = false;
                foregroundImage.setPosition(getX() - getWidth()/2 + getHeight()/2, getY());
            }
        }
    }

    @Override
    public void processClickDown(ClickArgs args) {
        if(isDisabled()) return;
    }

    @Override
    public void updateCursorPosition(HoverArgs args) {
        boolean containsPos = containsPosition(args.getX(), args.getY());
        if (!isHovering && containsPos) {
            isHovering = true;
            if (hoverEnterCallback != null)
                hoverEnterCallback.call(new ButtonCallbackArgs());
        }
        else if (isHovering && !containsPos) {
            isHovering = false;
            if (hoverExitCallback != null)
                hoverExitCallback.call(new ButtonCallbackArgs());
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
    public void processTextInput(TextInputArgs args) {

    }

    @Override
    public void processControlsInput(ControlsArgs args) {

    }

    @Override
    public void update(TimeRecord elapsed) {

    }

    @Override
    protected void drawElement(Renderer2D renderer, float offsetX, float offsetY, Colour colour) {
        Colour highlight = isHovering ? Colour.White : Colour.LightGrey;
        backgroundImage.draw(renderer, offsetX, offsetY, Colour.multiply(colour, highlight));
        foregroundImage.draw(renderer, offsetX, offsetY, Colour.multiply(colour, highlight));
    }
}