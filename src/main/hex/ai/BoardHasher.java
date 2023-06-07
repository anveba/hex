package main.hex.ai;

import main.hex.Move;
import main.hex.board.Board;
import main.hex.board.TileColour;

import java.util.Random;

public class BoardHasher {


    private static BoardHasher instance;

    int boardSize;

    int[][][] zobristTable;

    public BoardHasher(int boardSize){
        this.boardSize = boardSize;
        zobristTable = new int[boardSize][boardSize][2];
        initializeZobristTable();
    }

    public static BoardHasher getInstance(int boardSize) {
        if(instance == null || instance.boardSize != boardSize){
            instance = new BoardHasher(boardSize);
            System.out.println("Made new instance of boardhasher");
        }
        return instance;
    }

    private void initializeZobristTable(){
        Random random = new Random();

        for(int x = 0; x<boardSize;x++){
            for(int y = 0;y<boardSize;y++){
                zobristTable[x][y][1] = random.nextInt();
                zobristTable[x][y][0] = random.nextInt();
            }
        }

    }

    public void printZobristTable(){
        for(int[][] x: zobristTable){
            for(int[] y :x){
                for(int i : y){
                    System.out.println(i);
                }
            }
        }
    }



    public int hash(Board b) {
        int hash = 0;
        for(int x = 0; x<boardSize;x++){
            for(int y = 0;y<boardSize;y++){
                TileColour c = b.getTileAtPosition(x,y).getColour();
                if(c == TileColour.WHITE){
                    continue;
                }
                if(c == TileColour.PLAYER1){
                    hash = hash ^ zobristTable[x][y][0];
                }
                if(c == TileColour.PLAYER2){
                    hash = hash ^ zobristTable[x][y][1];
                }
            }
        }
        return hash;
    }

    public int toggleMoveToBoardHash(int hash, Move m, TileColour colour){
        if(colour == TileColour.WHITE){
            return  hash;
        }
        if(colour == TileColour.PLAYER1){
            return hash ^zobristTable[m.getX()][m.getY()][0];
        }
        if(colour == TileColour.PLAYER2){
            return hash ^zobristTable[m.getX()][m.getY()][1];
        }
        //Catchall, that should never be reached
        //Note that the Colour = white at the top is still added, since it's the most common case
        assert false;
        return hash;

    }

}
