package main.hex;

/**
 * Represents a move a player can make.
 * @author Andreas - s214971
 *
 */
public class Move {
	private short x, y;
	
	public Move(int x, int y) {
		this.x = (short)x;
		this.y = (short)y;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
}
