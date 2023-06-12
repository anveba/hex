package main.engine.ui.animation.easing;

public class CubicInOut implements EasingFunction {

	@Override
	public float ease(float t) {
		return (t < 0.5f ? 4.0f * t * t * t : 1.0f - (float)Math.pow(-2.0f * t + 2.0f, 3) / 2.0f);
	}

}
