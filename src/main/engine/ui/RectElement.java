package main.engine.ui;

import main.engine.EngineException;
import main.engine.graphics.Colour;
import main.engine.graphics.Renderer2D;
import main.engine.math.Vector2;

public abstract class RectElement extends UIElement {
	
	private float x, y;
	private float width, height;
	
	public RectElement(float x, float y, float width, float height) {
		setPosition(x, y);
		setWidth(width);
		setHeight(height);
	}
	
	@Override
	public float getX() {
		return x;
	}
	
	@Override
	public float getY() {
		return y;
	}
	
	@Override
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setWidth(float width) {
		if (width < 0.0f)
			throw new EngineException("Illegal negative width");
		this.width = width;
	}
	
	public float getWidth() {
		return width;
	}
	
	public void setHeight(float height) {
		if (height < 0.0f)
			throw new EngineException("Illegal negative height");
		this.height = height;
	}
	
	public float getHeight() {
		return height;
	}

    protected abstract void drawElement(Renderer2D renderer, float offsetX, float offsetY, Colour colour);
}
