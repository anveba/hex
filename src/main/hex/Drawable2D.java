package main.hex;

import main.engine.graphics.Renderer2D;

public interface Drawable2D {
    void draw(Renderer2D renderer);
    boolean hasLoadedDrawingResources();
    void loadDrawingResources();
}
