package main.engine.ui.callback;

/**
 * Immutable.
 * @author andreas
 *
 */
public class ClickArgs {
	private float x, y;
	
	public ClickArgs(float x, float y) {
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
