package main.engine.ui;

import main.engine.*;
import main.engine.graphics.*;
import main.engine.input.Controls;
import main.engine.input.ControlsArgs;
import main.engine.ui.callback.ClickArgs;
import main.engine.ui.callback.HoverArgs;
import main.engine.ui.callback.TextInputArgs;

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

	public void clickDownAt(float x, float y) {
		if (!(root instanceof Clickable) || root.isHidden())
			return;
		Clickable c = (Clickable)root;
		var args = new ClickArgs(x, y);
		c.processClickDown(args);
	}
	
	public void clickReleaseAt(float x, float y) {
		if (!(root instanceof Clickable) || root.isHidden())
			return;
		Clickable c = (Clickable)root;
		var args = new ClickArgs(x, y);
		c.processClickRelease(args);
	}

	public void hoverAt(float x, float y) {
		if (!(root instanceof Hoverable) || root.isHidden())
			return;
		Hoverable h = (Hoverable)root;
		var args = new HoverArgs(x, y);
		h.updateCursorPosition(args);
	}
	
	public void processTextInput(char ch) {
		if (!(root instanceof Clickable) || root.isHidden())
			return;
		Clickable c = (Clickable)root;
		var args = new TextInputArgs(ch);
		c.processTextInput(args);
	}
	
	public void processControlsInput(ControlsArgs args) {
		if (!(root instanceof Clickable) || root.isHidden())
			return;
		Clickable clickable = (Clickable)root;
		clickable.processControlsInput(new ControlsArgs(args.getControls()));
	}
	
	public void update(TimeRecord elapsed) {
		if (root != null && !root.isHidden())
			root.update(elapsed);
	}
	
	protected void draw(Renderer2D renderer) {
		if (root != null && !root.isHidden())
			root.draw(renderer, 0, 0, Colour.White);
	}
}
