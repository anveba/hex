package main.engine.ui.callback;

/**
 * Immutable.
 * @author Andreas - s214971
 *
 */
public class HoverArgs {
	private float x, y;
	
	public HoverArgs(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
}
