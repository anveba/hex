package engine.ui;

import engine.Vector2;

public interface UIElement {
    Vector2 getPosition();

    boolean containsPosition(float x, float y);
}
