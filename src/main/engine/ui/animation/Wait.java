package main.engine.ui.animation;

import main.engine.EngineException;

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
