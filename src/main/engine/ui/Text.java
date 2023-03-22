package main.engine.ui;

import main.engine.*;
import main.engine.font.BitmapFont;
import main.engine.graphics.Colour;
import main.engine.graphics.Renderer2D;

public class Text extends UIElement {

	private float x, y;
	private float height;
	private BitmapFont font;
	private String text;
	private Colour colour;
	
	public Text(float x, float y, BitmapFont font, String text, float height) {
		setPosition(x, y);
		setFont(font);
		setText(text);
		setHeight(height);
		setColour(Colour.White);
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

	@Override
	public void draw(Renderer2D renderer, float offsetX, float offsetY, Colour c) {
		renderer.drawString(font, text, x + offsetX, y + offsetY, height, Colour.multiply(colour, c));
	}
}
