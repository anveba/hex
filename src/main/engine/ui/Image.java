package main.engine.ui;

import main.engine.*;
import main.engine.graphics.*;

/**
 * Represents an image in the user interface. Contains information pertaining to
 * how to draw it.
 * @author andreas
 *
 */
public class Image extends RectElement {

	private Texture texture;
	private int sourceX, sourceY;
	private int sourceWidth, sourceHeight;
	private Colour colour; 
	
	public Image(float x, float y, float width, float height,
			Texture tex, int sourceX, int sourceY, int sourceWidth, int sourceHeight,
			Colour colour) {
		super(x, y, width, height);
		setTexture(tex);
		setSourceX(sourceX);
		setSourceY(sourceY);
		setSourceWidth(sourceWidth);
		setSourceHeight(sourceHeight);
		setColour(colour);
	}
	
	public Image(float x, float y, float width, float height,
			Texture tex, int sourceX, int sourceY, int sourceWidth, int sourceHeight) {
		this(x, y, width, height, tex, sourceX, sourceY, sourceWidth, sourceHeight, Colour.White);
	}

	public Image(float x, float y, float width, float height, Texture tex, Colour col) {
		this(x, y, width, height, tex, 0, 0, tex.width(), tex.height(), col);
	}
	
	public Image(float x, float y, float width, float height, Texture tex) {
		this(x, y, width, height, tex, Colour.White);
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
	protected void draw(Renderer2D renderer, float offsetX, float offsetY, Colour colour) {
		Colour col = Colour.multiply(getColour(), colour);
		renderer.drawSprite(getTexture(), getX() + offsetX, getY() + offsetY, getWidth(), getHeight(), 
				getSourceX(), getSourceY(), getSourceWidth(), getSourceHeight(), col);
	}

}
