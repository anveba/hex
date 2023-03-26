package main.engine.ui;

import main.engine.Vector2;
import main.engine.font.BitmapFont;
import main.engine.input.Controls;
import main.engine.input.ControlsArgs;
import main.hex.Game;

public class TextField extends Text implements Clickable {
    //TODO: Add cursor on hover
    //TODO: Add cursor blinking
    //TODO: ADD Text left alignment (figure out the with of the text)
    //TODO: Add Rect element for selecting text (if based on text width, no with when no text)

    private boolean isFocused;
    StringBuilder textString = new StringBuilder();
    public TextField(float x, float y, BitmapFont font, String text, float height) {
        super(x, y, font, text, height);
        addKeyboardListeners();
    }

    private void removeFocus(ControlsArgs controlsArgs) {
        isFocused = false;
        removeKeyboardListeners();
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.LEFT_MOUSE, this::keyPressed);
    }

    public void keyPressed(ControlsArgs controlsArgs) {
        if (!isFocused) return;
        Controls c = controlsArgs.getControls();

        if (c == Controls.ESCAPE) {
            removeFocus(controlsArgs);
        } else if (c == Controls.BACKSPACE) {
            if (textString.length() > 0) textString.deleteCharAt(textString.length() - 1);
        } else if (c == Controls.SPACE) {
            textString.append(' ');
        } else {
            textString.append(c.toString());
        }

        setText(textString.toString());
    }

    @Override
    public void onClick(ClickArgs args) {
        if(!isFocused) {
            setText(textString.toString());
            isFocused = true;
            Game.getInstance().getControlsListener().addOnPressCallback(Controls.LEFT_MOUSE, this::removeFocus);
        }
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


    private void addKeyboardListeners() {
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.Q, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.W, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.E, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.R, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.T, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.Y, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.U, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.I, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.O, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.P, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.A, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.S, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.D, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.F, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.G, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.H, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.J, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.K, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.L, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.Z, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.X, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.C, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.V, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.B, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.N, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.M, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.SPACE, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.BACKSPACE, this::keyPressed);
        Game.getInstance().getControlsListener().addOnPressCallback(Controls.ESCAPE, this::keyPressed);
    }
    private void removeKeyboardListeners() {
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.Q, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.W, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.E, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.R, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.T, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.Y, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.U, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.I, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.O, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.P, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.A, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.S, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.D, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.F, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.G, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.H, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.J, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.K, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.L, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.Z, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.X, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.C, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.V, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.B, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.N, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.M, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.SPACE, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.BACKSPACE, this::keyPressed);
        Game.getInstance().getControlsListener().removeOnPressCallback(Controls.ESCAPE, this::keyPressed);
    }

}
