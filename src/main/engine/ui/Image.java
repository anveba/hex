package main.engine.ui;

import main.engine.*;
import main.engine.graphics.*;

public class Image extends RectElement {

	private Texture texture;
	private int sourceX, sourceY;
	private int sourceWidth, sourceHeight;
	private Colour colour = Colour.White; // If one of the constructors that does not specify color is used the default is white
	
	public Image(float x, float y, float width, float height,
			Texture tex, int sourceX, int sourceY, int sourceWidth, int sourceHeight) {
		super(x, y, width, height);
		setTexture(tex);
		setSourceX(sourceX);
		setSourceY(sourceY);
		setSourceWidth(sourceWidth);
		setSourceHeight(sourceHeight);
	}

	public Image(float x, float y, float width, float height, Texture tex) {
		super(x, y, width, height);
		setTexture(tex);
		setSourceX(0);
		setSourceY(0);
		setSourceWidth(tex.width());
		setSourceHeight(tex.height());
	}

	public Image(float x, float y, float width, float height, Texture tex, Colour col) {
		super(x, y, width, height);
		setTexture(tex);
		setSourceX(0);
		setSourceY(0);
		setSourceWidth(tex.width());
		setSourceHeight(tex.height());
		setColour(col);
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

	public Colour getColour() {
		return colour;
	}

	public void setColour(Colour c) {
		colour = c;
	}
	
	@Override
	public void update(TimeRecord elapsed) {
		
	}

	@Override
	public void draw(Renderer2D renderer, float offsetX, float offsetY, Colour c) {
		renderer.drawSprite(getTexture(), getX() + offsetX, getY() + offsetY, getWidth(), getHeight(), 
				getSourceX(), getSourceY(), getSourceWidth(), getSourceHeight(), getColour());
	}

}
