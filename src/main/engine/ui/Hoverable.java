package main.engine.ui;

import main.engine.ui.callback.HoverArgs;

/**
 * Represent a UI element that has hovering functionality.
 * @author Andreas - s214971
 *
 */
public interface Hoverable {
    void updateCursorPosition(HoverArgs args);
}
