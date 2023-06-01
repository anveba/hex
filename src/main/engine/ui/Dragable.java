package main.engine.ui;

import main.engine.input.ControlsArgs;

public interface Dragable extends Hoverable {
    boolean containsPosition(float x, float y);
    void processPress(ClickArgs args);
    void processRelease(ClickArgs args);
}
