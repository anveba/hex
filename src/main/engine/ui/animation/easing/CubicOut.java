package main.engine.ui.animation.easing;

public class CubicOut implements EasingFunction {

	@Override
	public float ease(float t) {
		return 1.0f - (float)Math.pow(1.0f - t, 3);
	}

}
