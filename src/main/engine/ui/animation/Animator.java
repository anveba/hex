package main.engine.ui.animation;

import main.engine.EngineException;
import main.engine.TimeRecord;

/**
 * Class responsible for playing an animation. It can only play one animation once.
 * @author andreas
 *
 */
public class Animator {
	
	private final Animation animation;
	private float time;
	
	public Animator(Animation animation) {
		if (animation == null)
			throw new EngineException("Animation was null");
		this.animation = animation;
	}
	
	public float getTime() {
		return time;
	}
	
	public Animation getAnimation() {
		return animation;
	}
	
	public void animate(TimeRecord elapsed) {
		time += elapsed.elapsedSeconds();
		if (animation != null && !animation.done()) {
			animation.animate(time);
			if (animation.done() && animation.getOnEndAction() != null)
				animation.getOnEndAction().run();
		}
	}
	
	public boolean done() {
		return animation != null && animation.done();
	}
	
}
