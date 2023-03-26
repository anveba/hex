package main.engine.ui;

import main.engine.*;
import main.engine.graphics.*;

/**
 * Represents a menu in the user interface. Only one frame is active at a time
 * and they are self-contained.
 * @author Andreas
 *
 */
public class Frame {
	
	private UIElement root;
	
	public Frame() {
	}
	
	public void setRoot(UIElement root) {
		this.root = root;
	}
	
	public UIElement getRoot() {
		return root;
	}
	
	public void clickAt(float x, float y) {
		if (!(root instanceof Clickable))
			return;
		Clickable c = (Clickable)root;
		var args = new ClickArgs(x, y);
		c.onClick(args);
	}

	public void hoverAt(float x, float y) {
		if (!(root instanceof Clickable))
			return;
		Clickable c = (Clickable)root;
		var args = new HoverArgs(x, y);
		c.updateCursorPosition(args);
	}
	
	protected void draw(Renderer2D renderer) {
		if (root != null)
			root.draw(renderer, 0, 0, Colour.White);
	}
}
