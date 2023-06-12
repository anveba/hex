package main.engine.ui;

import java.util.*;

import main.engine.EngineException;
import main.engine.TimeRecord;
import main.engine.graphics.Colour;
import main.engine.graphics.Renderer2D;
import main.engine.input.ControlsArgs;
import main.engine.ui.callback.ClickArgs;
import main.engine.ui.callback.HoverArgs;
import main.engine.ui.callback.TextInputArgs;

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
		if (e.getParent() != null)
			throw new EngineException("Element already has a parent");
		children.add(e);
		e.setParent(this);
	}
	
	public boolean removeChild(UIElement e) {
		assertChildParentRelationship(e);
		boolean removed = children.remove(e);
		if (removed && e.getParent() == this)
			e.removeParent();
		return removed;
	}
	
	public boolean containsChild(UIElement e) {
		assertChildParentRelationship(e);
		return children.contains(e);
	}
	
	private void assertChildParentRelationship(UIElement e) {
		// Checks: this is parent of e <=> e is child of this
		assert !(e.getParent() == this) || children.contains(e);
		assert !(children.contains(e)) || e.getParent() == this;
	}

	public void removeAllChildren() {
		for (var c : children)
			removeChild(c);
	}

	@Override
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean containsPosition(float x, float y) {
		for (var c : children) {
			if (!(c instanceof Clickable))
				continue;
			Clickable clickable = (Clickable)c;
			if (clickable.containsPosition(x - getX(), y - getY()))
				return true;
		}
		return false;
	}

	@Override
	protected void draw(Renderer2D renderer, float offsetX, float offsetY, Colour colour) {
		for (var c : children)
			if (!c.isHidden())
				c.draw(renderer, offsetX + getX(), offsetY + getY(), colour);
	}

	@Override
	public void processClickDown(ClickArgs args) {
		args = new ClickArgs(args.getX() - getX(), args.getY() - getY());
		for (var child : children) {
			if (child.isHidden())
				continue;
			if (!(child instanceof Clickable))
				continue;
			Clickable clickable = (Clickable)child;
			clickable.processClickDown(args);
		}
	}
	
	@Override
	public void processClickRelease(ClickArgs args) {
		args = new ClickArgs(args.getX() - getX(), args.getY() - getY());
		for (var child : children) {
			if (child.isHidden())
				continue;
			if (!(child instanceof Clickable))
				continue;
			Clickable clickable = (Clickable)child;
			clickable.processClickRelease(args);
		}
	}

	@Override
	public void updateCursorPosition(HoverArgs args) {
		args = new HoverArgs(args.getX() - getX(), args.getY() - getY());
		for (var child : children) {
			if (child.isHidden())
				continue;
			if (!(child instanceof Hoverable))
				continue;
			Hoverable hoverable = (Hoverable)child;
			hoverable.updateCursorPosition(args);
		}
	}

	@Override
	public void processTextInput(TextInputArgs args) {
		args = new TextInputArgs(args.getCharacter());
		for (var child : children) {
			if (child.isHidden())
				continue;
			if (!(child instanceof Clickable))
				continue;
			Clickable clickable = (Clickable)child;
			clickable.processTextInput(args);
		}
	}
	
	@Override
	public void processControlsInput(ControlsArgs args) {
		args = new ControlsArgs(args.getControls());
		for (var child : children) {
			if (child.isHidden())
				continue;
			if (!(child instanceof Clickable))
				continue;
			Clickable clickable = (Clickable)child;
			clickable.processControlsInput(args);
		}
	}
	
	@Override
	public void update(TimeRecord elapsed) {
		for (var child : children) {
			if (child.isHidden())
				continue;
			child.update(elapsed);
		}
	}
}
