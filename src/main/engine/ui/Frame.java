package main.engine.ui;

import main.engine.EngineException;
import main.engine.TimeRecord;
import main.engine.graphics.Colour;
import main.engine.graphics.Renderer2D;
import main.engine.input.ControlsArgs;
import main.engine.ui.animation.Animator;
import main.engine.ui.callback.ClickArgs;
import main.engine.ui.callback.HoverArgs;
import main.engine.ui.callback.TextInputArgs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents a menu in the user interface. Only one frame is active at a time.
 * They are self-contained.
 * @author Andreas - s214971
 *
 */
public class Frame {
	
	private UIElement root;
	private Collection<Animator> animators;
	
	public Frame() {
		animators = new ArrayList<>();
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
		if (root != null)
			root.update(elapsed);
	}
	
	protected void draw(Renderer2D renderer) {
		if (root != null && !root.isHidden())
			root.draw(renderer, 0, 0, Colour.White);
	}
	
	public void addAnimator(Animator animator) {
		if (animators.contains(animator))
			throw new EngineException("Animator is already contained");
		if (animator == null)
			throw new EngineException("Animator is null");
		animators.add(animator);
	}
	
	public boolean removeAnimator(Animator animator) {
		return animators.remove(animator);
	}
	
	protected void animate(TimeRecord time) {
		List<Animator> finishedAnimators = new ArrayList<>();
		for (var animator : animators) {
			if (animator.done()) {
				finishedAnimators.add(animator);
				continue;
			}
			animator.animate(time);
		}
		
		for (var finished : finishedAnimators) {
			animators.remove(finished);
		}
	}
}
