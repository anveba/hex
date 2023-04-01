package main.hex;

import main.engine.graphics.Renderer2D;
import main.hex.ui.GameCustomization;

public interface Drawable2D {
    void draw(Renderer2D renderer, GameCustomization gameCustomization);
}
