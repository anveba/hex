package main.engine.ui;

import main.engine.TimeRecord;
import main.engine.graphics.Colour;
import main.engine.graphics.Renderer2D;
import main.engine.graphics.Texture;
import main.engine.input.ControlsArgs;
import main.engine.ui.callback.ButtonCallback;
import main.engine.ui.callback.ButtonCallbackArgs;
import main.engine.ui.callback.ClickArgs;
import main.engine.ui.callback.HoverArgs;
import main.engine.ui.callback.TextInputArgs;
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
    private Image backgroundImage, foregroundImage;
    private Colour disabledColour, enabledColour;

    public ToggleSwitch(float x, float y, float width, float height, boolean initialToggleOn,
                        Texture backgroundTexture,
                        Texture foregroundTexture,
                        Colour disabledColour,
                        Colour enabledColour,
                        ButtonCallback enableCallback,
                        ButtonCallback disableCallback,
                        ButtonCallback onHoverEnterCallback,
                        ButtonCallback onHoverExitCallback) {
        super(x, y, width, height);
        this.toggleSwitchOn = initialToggleOn;
        this.disabledColour = disabledColour;
        this.enabledColour = enabledColour;
        if (!initialToggleOn) {
            backgroundImage = new Image(0.0f, 0.0f, width * 0.9f, height * 0.9f, backgroundTexture, disabledColour);
        } else {
            backgroundImage = new Image(0.0f, 0.0f, width * 0.9f, height * 0.9f, backgroundTexture, enabledColour);
        }
        foregroundImage = new Image(-width / 2.0f + height / 2.0f, 0.0f, height, height, foregroundTexture);
        if (toggleSwitchOn) {
            foregroundImage.setPosition(width / 2.0f - height / 2.0f, 0.0f);
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
                        Colour disabledColour,
                        Colour enabledColour,
                        ButtonCallback enableCallback,
                        ButtonCallback disableCallback,
                        ButtonCallback onHoverEnterCallback,
                        ButtonCallback onHoverExitCallback) {
        this (x, y, width, height, initialToggleOn,
                TextureLibrary.TOGGLE_SWITCH_BACKGROUND.getTexture(),
                TextureLibrary.TOGGLE_SWITCH_FOREGROUND.getTexture(),
                disabledColour,
                enabledColour,
                enableCallback, disableCallback, onHoverEnterCallback, onHoverExitCallback);
    }

    public ToggleSwitch(float x, float y, float width, float height, boolean initialToggleOn,
                        ButtonCallback enableCallback,
                        ButtonCallback disableCallback,
                        ButtonCallback onHoverEnterCallback,
                        ButtonCallback onHoverExitCallback) {
        this (x, y, width, height, initialToggleOn,
                TextureLibrary.TOGGLE_SWITCH_BACKGROUND.getTexture(),
                TextureLibrary.TOGGLE_SWITCH_FOREGROUND.getTexture(),
                Colour.White,
                Colour.White,
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
        if(isHidden()) return;

        if (!toggleSwitchOn) {
            if (containsPosition(args.getX(), args.getY())) {
                if (enableCallback != null) {
                    enableCallback.call(new ButtonCallbackArgs());
                }
                toggleSwitchOn = true;
                backgroundImage.setColour(enabledColour);
                foregroundImage.setPosition(getWidth()/2 - getHeight()/2, 0.0f);
             }
        } else {
            if (containsPosition(args.getX(), args.getY())) {
                if (disableCallback != null) {
                    disableCallback.call(new ButtonCallbackArgs());
                }
                toggleSwitchOn = false;
                backgroundImage.setColour(disabledColour);
                foregroundImage.setPosition(-getWidth()/2 + getHeight()/2, 0.0f);
            }
        }
    }

    @Override
    public void processClickDown(ClickArgs args) {
        if(isHidden()) return;
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
    protected void draw(Renderer2D renderer, float offsetX, float offsetY, Colour colour) {
        Colour highlight = isHovering ? Colour.White : Colour.LightGrey;
        backgroundImage.draw(renderer, offsetX + getX(), offsetY + getY(), Colour.multiply(colour, highlight));
        foregroundImage.draw(renderer, offsetX + getX(), offsetY + getY(), Colour.multiply(colour, highlight));
    }
}