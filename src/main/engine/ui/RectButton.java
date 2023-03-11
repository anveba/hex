package main.engine.ui;

import main.engine.Vector2;
import main.engine.font.BitmapFont;
import main.engine.graphics.Renderer2D;
import main.engine.graphics.Texture;

public class RectButton extends RectElement implements Clickable {
	
	Image image;
	Text text;
	ButtonCallback clickCallback, hoverCallback;
	
	public RectButton(float x, float y, float width, float height,
			Texture imageTexture, float imageWidth, float imageHeight,
			int sourceX, int sourceY, int sourceWidth, int sourceHeight, 
			BitmapFont font, String displayedString, float textHeight,
			ButtonCallback clickCallback, ButtonCallback hoverCallback) {
		super(x, y, width, height);
		image = new Image(x, y, imageWidth, imageHeight, imageTexture, 
				sourceX, sourceY, sourceWidth, sourceHeight);
		text = new Text(x, y, font, displayedString, textHeight);
		setClickCallback(clickCallback);
		setHoverCallback(hoverCallback);
		setWidth(width);
		setHeight(height);
		setPosition(x, y);
	}

	public void setClickCallback(ButtonCallback callback) {
		this.clickCallback = callback;
	}
	
	public void setHoverCallback(ButtonCallback callback) {
		this.hoverCallback = callback;
	}
	
	@Override
	public void onClick(ClickArgs args) {
		if (clickCallback != null) {
			clickCallback.call(new ButtonCallbackArgs());
		}
	}

	@Override
	public void onHover(HoverArgs args) {
		if (hoverCallback != null) {
			hoverCallback.call(new ButtonCallbackArgs());
		}
	}

	@Override
	void draw(Renderer2D renderer, float offsetX, float offsetY) {
		image.draw(renderer, offsetX, offsetY);
		text.draw(renderer, offsetX, offsetY);
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
}
