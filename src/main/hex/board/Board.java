package main.hex.board;

import main.engine.*;
import main.engine.graphics.*;
import main.engine.math.Vector2;
import main.engine.math.Vector3;
import main.hex.GameCustomisation;
import main.hex.HexException;

public class Board implements IBoard {
	
    private Tile[][] board;
    private static BoardRenderer2D renderer2D;
    private static BoardRenderer2D getRenderer2D() {
    	if (renderer2D == null)
    		renderer2D = new BoardRenderer2D();
    	return renderer2D;
    }
    
    private static BoardRenderer3D renderer3D;
    private static BoardRenderer3D getRenderer3D() {
    	if (renderer3D == null)
    		renderer3D = new BoardRenderer3D();
    	return renderer3D;
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
                if(t.getColour() == TileColour.PLAYER2){
                    System.out.print("R");
                }
                if(t.getColour() == TileColour.PLAYER1){
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

	public void draw2D(Renderer2D r, GameCustomisation gameCustomisation) {
		getRenderer2D().draw(r, this, gameCustomisation);
	}
	
	public void draw3D(Renderer3D r, GameCustomisation gameCustomisation) {
		getRenderer3D().draw(r, this, gameCustomisation);
	}

	public Point2 screenToTile2D(float x, float y) {
		return getRenderer2D().screenToTile(x, y, size());
	}

	public Vector2 tileToScreen2D(int x, int y) {
		return getRenderer2D().tileToScreen(x, y, size());
	}
	
	public Point2 screenToTile3D(float x, float y) {
		return getRenderer3D().screenToTile(x, y, size());
	}

	public Vector3 tileToWorld(int x, int y) {
		return getRenderer3D().tileToWorld(x, y, size());
	}
}
