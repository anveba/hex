package hex;


import engine.graphics.Renderer2D;

import java.util.Arrays;

public class Board implements Drawable2D{
    private Tile[][] board;

    public Board(int size){
        if(size <= 0){ throw new HexException();}


        board = new Tile[size][size];

        for(int x = 0; x < size; x++){
            for(int y = 0; y < size; y++){
                board[x][y] = new Tile();
            }
        }

    }

    public int getBoardSize(){
        return board.length;
    }

    public Tile getTileAtPosition(int x, int y){
        if(x >= getBoardSize() || y >= getBoardSize() || x < 0 || y < 0){
            throw new HexException();
        }
        return board[x][y];
    }


    @Override
    public void draw(Renderer2D renderer) {
        //TODO
    }
}
