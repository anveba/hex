package main.engine.ui.animation;

import main.engine.EngineException;
import main.engine.Utility;
import main.engine.ui.UIElement;
import main.engine.ui.animation.easing.EasingFunction;

/**
 * Represents an easing animation that moves a UI element according to a
 * given easing function.
 * @author Andreas - s214971
 * @see EasingFunction
 *
 */
public class Ease extends Animation {

	private final UIElement element;
	private final float startX, startY;
	private final float endX, endY;
	private final float duration;
	private boolean isDone;
	private EasingFunction easingFunction;
	
	public Ease(UIElement element, 
			EasingFunction easingFunction,
			float startX, float startY,
			float endX, float endY, 
			float duration) {
		if (element == null)
			throw new EngineException("Element was null");
		if (duration < 0.0f)
			throw new EngineException("Duration was negative");
		if (easingFunction == null)
			throw new EngineException("Easing function was null");
		this.element = element;
		this.easingFunction = easingFunction;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.duration = duration;
		isDone = false;
	}

	@Override
	protected void animate(float time) {
		if (time >= duration) {
			isDone = true;
			time = duration;
		}
		float t = easingFunction.ease(time / duration);
		t = Utility.clamp(t, 0.0f, 1.0f);
		float x = Utility.lerp(t, startX, endX);
		float y = Utility.lerp(t, startY, endY);
		element.setPosition(x, y);
	}

	@Override
	protected float blocksFor() {
		return 0.0f;
	}
	
	@Override
	protected boolean done() {
		return isDone;
	}
}
