package main.engine.ui.animation;

import main.engine.EngineException;

/**
 * An animation with the sole purpose of blocking (waiting) for a
 * certain amount of time. 
 * @author Andreas - s214971
 *
 */
public class Wait extends Animation {

	private final float waitTime;
	private boolean isDone;
	
	public Wait(float time) {
		if (time < 0.0f)
			throw new EngineException("Time cannot be negative");
		this.waitTime = time;
		isDone = false;
	}
	
	@Override
	protected void animate(float time) {
		if (time >= waitTime)
			isDone = true;
	}

	@Override
	protected float blocksFor() {
		return waitTime;
	}

	@Override
	protected boolean done() {
		return isDone;
	}

}
