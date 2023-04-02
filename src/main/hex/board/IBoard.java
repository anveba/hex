package main.hex.board;

public interface IBoard {
	int size();
	Tile getTileAtPosition(int x, int y);
}
