package main.hex.board;

import main.engine.*;
import main.engine.graphics.*;
import main.hex.Drawable2D;
import main.hex.HexException;
import main.hex.ui.GameCustomization;

public class Board implements Drawable2D, IBoard {
	
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
                board[x][y] = new Tile(TileColour.WHITE);
            }
        }
    }

    public Board clone() {
    	Board b = new Board(size());
    	for (int i = 0; i < size(); i++)
    		for (int j = 0; j < size(); j++)
    			b.setTileAtPosition(new Tile(getTileAtPosition(i, j).getColour()), i, j);
    	return b;
    }

    public void printBoard(){
        for (Tile[] row: board
             ) {
            for(Tile t : row){
                if(t.getColour() == TileColour.RED){
                    System.out.print("R");
                }
                if(t.getColour() == TileColour.BLUE){
                    System.out.print("B");
                }
                if(t.getColour() == TileColour.WHITE){
                    System.out.print("W");
                }

            }
            System.out.println();
        }
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
    
    public void setTileAtPosition(Tile t, int x, int y){
        if(isOutOfBounds(x, y))
            throw new HexException("Out of bounds of board");
        if (t == null)
        	throw new HexException("No tile was given (a null pointer given)");
        board[x][y] = t;
    }

    public boolean isOutOfBounds(int x, int y) {
        return x >= size() || y >= size() || x < 0 || y < 0;
    }

	@Override
	public void draw(Renderer2D r, GameCustomization gameCustomization) {
		getRenderer().draw(r, this, gameCustomization);
	}

	public Point2 screenToTile(float x, float y) {
		return getRenderer().screenToTile(x, y, size());
	}

	public Vector2 tileToScreen(int x, int y) {
		return getRenderer().tileToScreen(x, y, size());
	}
}
