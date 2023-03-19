package main.engine.ui;

import main.engine.*;
import main.engine.graphics.*;

public class Image extends RectElement {

	private Texture texture;
	private int sourceX, sourceY;
	private int sourceWidth, sourceHeight;
	
	public Image(float x, float y, float width, float height,
			Texture tex, int sourceX, int sourceY, int sourceWidth, int sourceHeight) {
		super(x, y, width, height);
		setTexture(tex);
		setSourceX(sourceX);
		setSourceY(sourceY);
		setSourceWidth(sourceWidth);
		setSourceHeight(sourceHeight);
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public void setTexture(Texture tex) {
		if (tex == null)
			throw new EngineException("Texture given was null");
		texture = tex;
	}
	
	public int getSourceX() {
		return sourceX;
	}
	
	public void setSourceX(int x) {
		sourceX = x;
	}
	
	public int getSourceY() {
		return sourceY;
	}
	
	public void setSourceY(int y) {
		sourceY = y;
	}
	
	public int getSourceWidth() {
		return sourceWidth;
	}
	
	public void setSourceWidth(int w) {
		sourceWidth = w;
	}
	
	public int getSourceHeight() {
		return sourceHeight;
	}
	
	public void setSourceHeight(int h) {
		sourceHeight = h;
	}

	@Override
	public void draw(Renderer2D renderer, float offsetX, float offsetY) {
		renderer.draw(getTexture(), getX() + offsetX, getY() + offsetY, getWidth(), getHeight(), 
				getSourceX(), getSourceY(), getSourceWidth(), getSourceHeight(), Colour.White);
	}

}
