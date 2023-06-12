package main.engine.ui;

import main.engine.*;
import main.engine.font.BitmapFont;
import main.engine.font.FontCharacterData;
import main.engine.graphics.Colour;
import main.engine.graphics.Renderer2D;
import main.engine.math.Vector2;
import main.hex.Game;

/**
 * Represent a piece of text in the user interface. It contains the information
 * used to draw it and which string is drawn.
 * @author andreas
 *
 */
public class Text extends UIElement {

	private float x, y;
	private float height;
	private BitmapFont font;
	private String text;
	private Colour colour;
	private AnchorPoint anchor;
	
	public Text(float x, float y, BitmapFont font, String text, float height) {
		this (x, y, font, text, height, Colour.White);
	}

	public Text(float x, float y, BitmapFont font, String text, float height, Colour colour) {
		setPosition(x, y);
		setFont(font);
		setText(text);
		setHeight(height);
		setColour(colour);
		setAnchorPoint(AnchorPoint.Center);
	}

	@Override
	public float getX() {
		return x;
	}
	
	@Override
	public float getY() {
		return y;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void setHeight(float h) {
		if (h < 0.0f)
			throw new EngineException("Height was negative");
		height = h;
	}
	
	public BitmapFont getFont() {
		return font;
	}
	
	public void setFont(BitmapFont font) {
		if (font == null)
			throw new EngineException("Font was null");
		this.font = font;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		if (text == null)
			throw new EngineException("Text was null");
		this.text = text;
	}
	
	@Override
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Colour getColour() {
		return colour;
	}
	
	public void setColour(Colour c) {
		if (c == null)
			throw new EngineException("Colour was null");
		colour = c;
	}
	
	public void setAnchorPoint(AnchorPoint anchor) {
		if (anchor == null)
			throw new EngineException("Anchor was null");
		this.anchor = anchor;
	}

	@Override
	public void update(TimeRecord elapsed) {
		
	}
	
	@Override
	protected void draw(Renderer2D renderer, float offsetX, float offsetY, Colour c) {
		assert anchor != null;
		float anchoredX = x + offsetX;
		if (anchor != AnchorPoint.Center) {
			anchoredX += renderer.getRenderedStringWidth(font, text, height) / 2.0f *
					(anchor == AnchorPoint.Left ? 1.0f : -1.0f);
		}
		renderer.drawString(font, text, anchoredX, y + offsetY, height, Colour.multiply(colour, c));
	}
}
