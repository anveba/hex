package main.engine.ui.animation;

import main.engine.EngineException;
import main.engine.ui.UIElement;

/**
 * Animation that shows a UI element.
 * @author Andreas - s214971
 *
 */
public class Show extends Animation {

	private boolean isDone;
	private final UIElement element;
	
	public Show(UIElement element) {
		if (element == null)
			throw new EngineException("Element was null");
		this.element = element;
		isDone = false;
	}
	
	@Override
	protected void animate(float time) {
		if (isDone || time == 0.0f)
			return;
		element.show();
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
