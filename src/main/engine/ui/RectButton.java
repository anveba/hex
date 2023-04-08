package main.engine.ui;

import main.engine.TimeRecord;
import main.engine.font.BitmapFont;
import main.engine.graphics.Colour;
import main.engine.graphics.Renderer2D;
import main.engine.graphics.Texture;
import main.engine.input.ControlsArgs;
import main.engine.math.Vector2;

public class RectButton extends RectElement implements Clickable {
	
	private Image image;
	private Text text;
	private ButtonCallback clickCallback, hoverEnterCallback, hoverExitCallback;
	private boolean isHovering;
	
	public RectButton(float x, float y, float width, float height,
			Texture imageTexture, float imageWidth, float imageHeight,
			int sourceX, int sourceY, int sourceWidth, int sourceHeight, 
			BitmapFont font, String displayedString, float textHeight,
			ButtonCallback clickCallback, 
			ButtonCallback onHoverEnterCallback,
			ButtonCallback onHoverExitCallback) {
		super(x, y, width, height);
		image = new Image(x, y, imageWidth, imageHeight, imageTexture, 
				sourceX, sourceY, sourceWidth, sourceHeight);
		text = new Text(x, y, font, displayedString, textHeight);
		setClickCallback(clickCallback);
		setHoverEnterCallback(onHoverEnterCallback);
		setHoverExitCallback(onHoverExitCallback);
		setWidth(width);
		setHeight(height);
		setPosition(x, y);
		isHovering = false;
	}

	public RectButton(float x, float y, float width, float height,
					  Texture imageTexture, BitmapFont font, String displayedString, float textHeight,
					  ButtonCallback clickCallback,
					  ButtonCallback onHoverEnterCallback,
					  ButtonCallback onHoverExitCallback) {
		super(x, y, width, height);
		image = new Image(x, y, width, height, imageTexture);
		text = new Text(x, y, font, displayedString, textHeight);
		setClickCallback(clickCallback);
		setHoverEnterCallback(onHoverEnterCallback);
		setHoverExitCallback(onHoverExitCallback);
		setWidth(width);
		setHeight(height);
		setPosition(x, y);
		isHovering = false;
	}

	public void setClickCallback(ButtonCallback callback) {
		this.clickCallback = callback;
	}
	
	public void setHoverEnterCallback(ButtonCallback callback) {
		this.hoverEnterCallback = callback;
	}
	
	public void setHoverExitCallback(ButtonCallback callback) {
		this.hoverExitCallback = callback;
	}
	
	@Override
	public void processClick(ClickArgs args) {
		if (containsPosition(args.getX(), args.getY()) && clickCallback != null) {
			clickCallback.call(new ButtonCallbackArgs());
		}
	}

	@Override
	public void updateCursorPosition(HoverArgs args) {
		boolean containsPos = containsPosition(args.getX(), args.getY());
		if (!isHovering && containsPos) {
			isHovering = true;
			if (hoverEnterCallback != null)
				hoverEnterCallback.call(new ButtonCallbackArgs());
		}
		else if (isHovering && !containsPos) {
			isHovering = false;
			if (hoverExitCallback != null)
				hoverExitCallback.call(new ButtonCallbackArgs());
		}
	}
	
	@Override
	public boolean containsPosition(float x, float y) {
		return this.getX() - getWidth() / 2.0f <= x 
				&& this.getY() - getHeight() / 2.0f <= y 
				&& this.getX() + getWidth() / 2.0f >= x
				&& this.getY() + getHeight() / 2.0f >= y;
	}

	@Override
	public void processTextInput(TextInputArgs args) {
	
	}
	
	@Override
	public void processControlsInput(ControlsArgs args) {
		
	}
	
	@Override
	public void update(TimeRecord elapsed) {
		
	}
	
	public float getImageWidth() {
		return image.getWidth();
	}
	
	public float getImageHeight() {
		return image.getHeight();
	}
	
	public float getImageSourceX() {
		return image.getSourceX();
	}
	
	public float getImageSourceY() {
		return image.getSourceY();
	}
	
	public float getImageSourceWidth() {
		return image.getSourceWidth();
	}
	
	public float getImageSourceHeight() {
		return image.getSourceHeight();
	}

	public Texture getTexture() {
		return image.getTexture();
	}
	
	public BitmapFont getFont() {
		return text.getFont();
	}
	
	public String getDisplayedString() {
		return text.getText();
	}
	
	public float getTextHeight() {
		return text.getHeight();
	}

	public void updateImageTexture(Texture texture) {
		image.setTexture(texture);
	}

	@Override
	void draw(Renderer2D renderer, float offsetX, float offsetY, Colour colour) {
		Colour highlight = isHovering ? Colour.White : Colour.LightGrey;
		image.draw(renderer, offsetX, offsetY, Colour.multiply(colour, highlight));
		text.draw(renderer, offsetX, offsetY, Colour.multiply(colour, highlight));
	}
}
