package main.engine.ui.animation;

import main.engine.EngineException;
import main.engine.TimeRecord;
import main.engine.ui.UIElement;

public abstract class Animation {
	
	public Animation() {
		
	}
	
	//The given time record shound be relative to when the animation starts.
	protected abstract void animate(float time);
	
	protected abstract float blocksFor();
	
	protected abstract boolean done();
}
