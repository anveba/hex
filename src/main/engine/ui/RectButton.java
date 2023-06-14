package main.engine.ui;

import main.engine.EngineException;
import main.engine.TimeRecord;
import main.engine.font.BitmapFont;
import main.engine.graphics.Colour;
import main.engine.graphics.Renderer2D;
import main.engine.graphics.Texture;
import main.engine.input.ControlsArgs;
import main.engine.ui.callback.ButtonCallback;
import main.engine.ui.callback.ButtonCallbackArgs;
import main.engine.ui.callback.ClickArgs;
import main.engine.ui.callback.HoverArgs;
import main.engine.ui.callback.TextInputArgs;

/**
 * Represents a button in the UI with an image and text.
 * @author Andreas - s214971
 *
 */
public class RectButton extends RectElement implements Clickable {
	
	private Image image;
	private Text text;
	private ButtonCallback clickCallback, hoverEnterCallback, hoverExitCallback;
	private boolean isHovering, isDown;
	private Colour colour;
	private boolean isDisabled;
	
	private static final float downedOffset = -0.01f;
	
	public RectButton(float x, float y, float width, float height,
			Texture imageTexture, float imageWidth, float imageHeight,
			int sourceX, int sourceY, int sourceWidth, int sourceHeight, 
			BitmapFont font, String displayedString, float textHeight,
			ButtonCallback clickCallback, 
			ButtonCallback onHoverEnterCallback,
			ButtonCallback onHoverExitCallback) {
		super(x, y, width, height);
		image = new Image(0.0f, 0.0f, imageWidth, imageHeight, imageTexture, 
				sourceX, sourceY, sourceWidth, sourceHeight);
		text = new Text(0.0f, 0.0f, font, displayedString, textHeight);
		setClickCallback(clickCallback);
		setHoverEnterCallback(onHoverEnterCallback);
		setHoverExitCallback(onHoverExitCallback);
		setWidth(width);
		setHeight(height);
		setPosition(x, y);
		isDisabled = false;
		isHovering = false;
		isDown = false;
		setColour(Colour.White);
	}

	public RectButton(float x, float y, float width, float height,
					  Texture imageTexture, BitmapFont font, String displayedString, float textHeight,
					  ButtonCallback clickCallback,
					  ButtonCallback onHoverEnterCallback,
					  ButtonCallback onHoverExitCallback) {
		this(x, y, width, height, imageTexture, width, height, 
				0, 0, imageTexture.width(), imageTexture.height(), 
				font, displayedString, textHeight, 
				clickCallback, onHoverEnterCallback, onHoverExitCallback);
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
	public void processClickRelease(ClickArgs args) {
		if (isDisabled)
			return;
		isDown = false;
		if (containsPosition(args.getX(), args.getY()) && clickCallback != null) {
			clickCallback.call(new ButtonCallbackArgs());
		}
	}
	
	@Override
	public void processClickDown(ClickArgs args) {
		if (isDisabled)
			return;
		if (containsPosition(args.getX(), args.getY()))
			isDown = true;
	}

	@Override
	public void updateCursorPosition(HoverArgs args) {
		if (isDisabled)
			return;
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
	
	public Colour getColour() {
		return colour;
	}

	public void setColour(Colour colour) {
		if (colour == null)
			throw new EngineException("Colour was null");
		this.colour = colour;
	}
	
	public void enable() {
		isDisabled = false;
	}
	
	public void disable() {
		isDisabled = true;
	}

	@Override
	protected void draw(Renderer2D renderer, float offsetX, float offsetY, Colour colour) {
		if (isDown)
			offsetY += downedOffset;
		if (isDisabled)
			colour = Colour.multiply(Colour.Grey, colour);
		Colour highlight = isHovering ? Colour.White : Colour.LightGrey;
		Colour col = Colour.multiply(Colour.multiply(colour, highlight), getColour());
		image.draw(renderer, offsetX + getX(), offsetY + getY(), col);
		text.draw(renderer, offsetX + getX(), offsetY + getY() + text.getHeight() / 5.0f, col);
	}
}
