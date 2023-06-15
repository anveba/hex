package main.engine.ui.animation.easing;

public class CubicIn implements EasingFunction {

	@Override
	public float ease(float t) {
		return t * t * t;
	}

}
