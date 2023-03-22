package main.engine.ui;

import main.engine.graphics.Colour;
import main.engine.graphics.Renderer2D;

public abstract class UIElement {
	public abstract float getX();
	public abstract float getY();
	public abstract void setPosition(float x, float y);
    abstract void draw(Renderer2D renderer, float offsetX, float offsetY, Colour colour);
}
