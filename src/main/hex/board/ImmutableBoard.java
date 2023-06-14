package main.hex.board;

/**
 * Wraps a board object to make it immutable.
 * @author Andreas - s214971
 *
 */
public class ImmutableBoard implements IBoard {

	private Board wrappedBoard;
	
	private ImmutableBoard(Board board) {
		this.wrappedBoard = board;
	}
	
	public static ImmutableBoard ofBoard(Board board) {
		return new ImmutableBoard(board);
	}
	
	@Override
	public int size() {
		return wrappedBoard.size();
	}

	@Override
	public Tile getTileAtPosition(int x, int y) {
		return wrappedBoard.getTileAtPosition(x, y);
	}
}
