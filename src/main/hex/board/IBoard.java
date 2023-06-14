package main.hex.board;

/**
 * 
 * @author Andreas - s214971
 *
 */
public interface IBoard {
	int size();
	Tile getTileAtPosition(int x, int y);
}
