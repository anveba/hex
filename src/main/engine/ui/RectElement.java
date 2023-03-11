package main.engine.ui;

import main.engine.EngineException;
import main.engine.Vector2;

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

	@Override
	public boolean containsPosition(float x, float y) {
		return this.x - width / 2.0f <= x 
				&& this.y - height / 2.0f <= y 
				&& this.x + width / 2.0f >= x
				&& this.y + height / 2.0f >= y;
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
}
