package main.engine.ui;

import java.util.*;

import main.engine.EngineException;
import main.engine.Vector2;
import main.engine.graphics.Renderer2D;

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
			if (c.containsPosition(x, y))
				return true;
		}
		return false;
	}

	@Override
	public void draw(Renderer2D renderer, float offsetX, float offsetY) {
		for (var c : children)
			c.draw(renderer, offsetX + getX(), offsetY + getY());
	}

	@Override
	public void onClick(ClickArgs args) {
		for (var child : children) {
			if (!(child instanceof Clickable))
				continue;
			Clickable clickable = (Clickable)child;
			if (child.containsPosition(args.getX(), args.getY())) {
				clickable.onClick(args);
				break;
			}
		}
	}

	@Override
	public void onHover(HoverArgs args) {
		//TODO
	}
}
