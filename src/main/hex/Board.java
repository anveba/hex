package main.hex;

import java.util.Arrays;

import main.engine.*;
import main.engine.graphics.*;

public class Board implements Drawable2D{
	
    private Tile[][] board;
    private static BoardRenderer2D renderer;
    private static BoardRenderer2D getRenderer() {
    	if (renderer == null)
    		renderer = new BoardRenderer2D();
    	return renderer;
    }

    public Board(int size){
        if(size <= 0){ throw new HexException("Nonpositive board size was given");}

        board = new Tile[size][size];

        for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
                board[x][y] = new Tile(Tile.Colour.WHITE);
            }
        }
    }

    public Board clone() {
    	Board b = new Board(size());
    	for (int i = 0; i < size(); i++)
    		for (int j = 0; j < size(); j++)
    			b.getTileAtPosition(i, j).setColour(getTileAtPosition(i, j).getColour());
    	return b;
    }

    
    public int size(){
        return board.length;
    }

    public Tile getTileAtPosition(int x, int y){
        if(isOutOfBounds(x, y)){
            throw new HexException("Out of bounds of board");
        }
        return board[x][y];
    }

    public boolean isOutOfBounds(int x, int y) {
        return x >= size() || y >= size() || x < 0 || y < 0;
    }

	@Override
	public void draw(Renderer2D r) {
		getRenderer().draw(r, this);
	}

	public Point2 screenToTile(float x, float y) {
		return getRenderer().screenToTile(x, y, size());
	}

	public Vector2 tileToScreen(int x, int y) {
		return getRenderer().tileToScreen(x, y, size());
	}
}
