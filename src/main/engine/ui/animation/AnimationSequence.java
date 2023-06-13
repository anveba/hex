package main.engine.ui.animation;

import java.util.*;

import main.engine.EngineException;
import main.engine.TimeRecord;

public class AnimationSequence extends Animation {
	
	private List<Animation> animations;
	
	public AnimationSequence(Animation... initial) {
		this.animations = new ArrayList<>();
		append(initial);
	}
	
	public void append(Animation... appended) {
		if (appended == null)
			throw new EngineException("Animation list was null");
		for(int i = 0; i < appended.length; i++)
			append(appended[i]);
	}
	
	public void append(Animation appended) {
		if (appended == null)
			throw new EngineException("Animation was null");
		if (appended == this)
			throw new EngineException("Cannot append itself!");
		animations.add(appended);
	}

	@Override
	protected void animate(float time) {
		float timeBlocked = 0.0f;
		for (int i = 0; i < animations.size(); i++) {
			Animation anim = animations.get(i);
			assert anim.blocksFor() >= 0.0f;
			float t = time - timeBlocked;
			if (t >= 0.0f) {				
				if (!anim.done()) {
					anim.animate(t);
					if (anim.done() && anim.getOnEndAction() != null)
						anim.getOnEndAction().run();
				}
			}
			
			timeBlocked += anim.blocksFor();
		}
	}

	@Override
	protected float blocksFor() {
		int sum = 0;
		for (var a : animations)
			sum += a.blocksFor();
		return sum;
	}

	@Override
	protected boolean done() {
		for (var anim : animations)
			if (!anim.done())
				return false;
		return true;
	}
}
