package main.engine.ui;

import java.util.*;

import main.engine.EngineException;
import main.engine.Vector2;
import main.engine.graphics.Colour;
import main.engine.graphics.Renderer2D;

/**
 * Represents a group of UI elements that itself is a UI element.
 * @author Andreas
 *
 */
public class UIGroup extends UIElement implements Clickable {

	private float x, y;
	private List<UIElement> children;
	
	public UIGroup(float x, float y) {
		children = new ArrayList<>();
		setPosition(x, y);
	}
	
	@Override
	public float getX() {
		return x;
	}
	
	@Override
	public float getY() {
		return y;
	}
	
	public void addChild(UIElement e) {
		if (e == null)
			throw new EngineException("null element given");
		if (e == this)
			throw new EngineException("A group cannot be a child of itself");
		if (containsChild(e))
			throw new EngineException("Element already child of group");
		children.add(e);
	}
	
	public boolean removeChild(UIElement e) {
		return children.remove(e);
	}
	
	public boolean containsChild(UIElement e) {
		return children.contains(e);
	}
	
	@Override
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean containsPosition(float x, float y) {
		for (var c : children) {
			if (c.containsPosition(x - getX(), y - getY()))
				return true;
		}
		return false;
	}

	@Override
	public void draw(Renderer2D renderer, float offsetX, float offsetY, Colour colour) {
		for (var c : children)
			c.draw(renderer, offsetX + getX(), offsetY + getY(), colour);
	}

	@Override
	public void onClick(ClickArgs args) {
		args = new ClickArgs(args.getX() - getX(), args.getY() - getY());
		for (var child : children) {
			if (!(child instanceof Clickable))
				continue;
			Clickable clickable = (Clickable)child;
			if (child.containsPosition(args.getX(), args.getY())) {
				clickable.onClick(args);
				//break; //Has to be revisited. When layering UIGroups, this will break the functionality.
			}
		}
	}

	@Override
	public void updateCursorPosition(HoverArgs args) {
		args = new HoverArgs(args.getX() - getX(), args.getY() - getY());
		for (var child : children) {
			if (!(child instanceof Clickable))
				continue;
			Clickable clickable = (Clickable)child;
			clickable.updateCursorPosition(args);
		}
	}
}
