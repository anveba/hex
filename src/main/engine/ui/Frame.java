package main.engine.ui;

import main.engine.*;
import main.engine.graphics.*;
import main.engine.input.Controls;
import main.engine.input.ControlsArgs;

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
		c.processClick(args);
	}

	public void hoverAt(float x, float y) {
		if (!(root instanceof Clickable))
			return;
		Clickable c = (Clickable)root;
		var args = new HoverArgs(x, y);
		c.updateCursorPosition(args);
	}
	
	public void processTextInput(char ch) {
		if (!(root instanceof Clickable))
			return;
		Clickable c = (Clickable)root;
		var args = new TextInputArgs(ch);
		c.processTextInput(args);
	}
	
	public void processControlsInput(ControlsArgs args) {
		if (!(root instanceof Clickable))
			return;
		Clickable clickable = (Clickable)root;
		clickable.processControlsInput(new ControlsArgs(args.getControls()));
	}
	
	public void update(TimeRecord elapsed) {
		if (root != null)
			root.update(elapsed);
	}
	
	protected void draw(Renderer2D renderer) {
		if (root != null)
			root.draw(renderer, 0, 0, Colour.White);
	}
}
