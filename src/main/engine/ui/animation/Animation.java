package main.engine.ui.animation;

import main.engine.EngineException;
import main.engine.TimeRecord;
import main.engine.ui.UIElement;

/**
 * The base class for all UI animations.
 * @author Andreas - s214971
 *
 */
public abstract class Animation {
	
	private Runnable onEndAction;
	
	public Animation() {
		
	}
	
	//The given time record shound be relative to when the animation starts.
	protected abstract void animate(float time);
	
	protected abstract float blocksFor();
	
	protected abstract boolean done();
	
	public final void setOnEndAction(Runnable action) {
		onEndAction = action;
	}
	
	final Runnable getOnEndAction() {
		return onEndAction;
	}
}
