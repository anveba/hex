package main.engine.ui.animation;

import main.engine.EngineException;
import main.engine.ui.UIElement;

/**
 * Animation that sets the position of a UI element.
 * @author Andreas - s214971
 *
 */
public class SetPosition extends Animation {

	private final UIElement element;
	private final float x, y;
	private boolean isDone;
	
	public SetPosition(UIElement element, float x, float y) {
		if (element == null)
			throw new EngineException("Element was null");
		this.element = element;
		this.x = x;
		this.y = y;
		isDone = false;
	}
	
	@Override
	protected void animate(float time) {
		element.setPosition(x, y);
		isDone = true;
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
