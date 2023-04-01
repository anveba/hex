package main.engine.ui;

/**
 * Immutable.
 * @author andreas
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
