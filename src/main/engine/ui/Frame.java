package main.engine.ui;

import main.engine.*;
import main.engine.graphics.*;

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
		if (c != null) {
			var args = new ClickArgs(x, y);
			c.onClick(args);
		}
	}
	
	void draw(Renderer2D renderer) {
		if (root != null)
			root.draw(renderer, 0, 0);
	}
}
