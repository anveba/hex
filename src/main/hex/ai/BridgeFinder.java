package main.hex.ai;

import main.hex.board.Board;
import main.hex.board.TileColour;

import java.util.ArrayList;

public class BridgeFinder {


    public static ArrayList<Bridge> findLevelOneBridges(Board b, TileColour playerColour){
        ArrayList<Bridge> bridges = new ArrayList<>();
        for(int x = 0; x < b.size(); x++){
            for(int y = 0; y < b.size();y++){
                if(verticalBridgeExistsFromTile(x,y,b,playerColour)){
                    bridges.add(new Bridge(x,y,x+1,y+2));
                }
                if(horizontalUpwardsBridgeExistsFromTile(x,y,b,playerColour)){
                    bridges.add(new Bridge(x,y,x+1,y-1));
                }
                if(horizontalDownwardsBridgeExistsFromTile(x,y,b,playerColour)){
                    bridges.add(new Bridge(x,y,x+2,y+1));

                }

            }
        }
        return bridges;
    }

    //Does not presently find borderBridges
    private static boolean verticalBridgeExistsFromTile(int x1, int y1, Board board, TileColour playerColour){
        int y2 = y1 +2;
        int x2 = x1 +1;
        //Make sure all coordinates are within borders
        if( coordinatePairIsOutOfBounds(x1,y1,board) || coordinatePairIsOutOfBounds(x2,y2,board)){
            return false;
        }

        //Make sure both cells are agent-coloured
        if(board.getTileAtPosition(x1,y1).getColour() != playerColour || board.getTileAtPosition(x2,y2).getColour() != playerColour ){
            return false;
        }


        //Make sure the two cells between our cells, are white
        return board.getTileAtPosition(x1, y1 + 1).getColour() == TileColour.WHITE && board.getTileAtPosition(x1 + 1, y1 + 1).getColour() == TileColour.WHITE;

    }

    private static boolean horizontalUpwardsBridgeExistsFromTile(int x1,int y1, Board board, TileColour playerColour){
        int x2 = x1 + 1;
        int y2 = y1 - 1;
        //Make sure all coordinates are within borders
        if( coordinatePairIsOutOfBounds(x1,y1,board) || coordinatePairIsOutOfBounds(x2,y2,board)){
            return false;
        }

        //Make sure both cells are agent-coloured
        if(board.getTileAtPosition(x1,y1).getColour() != playerColour || board.getTileAtPosition(x2,y2).getColour() != playerColour ){
            return false;
        }


        //Make sure the two cells between our cells, are white
        return board.getTileAtPosition(x1, y1 -1).getColour() == TileColour.WHITE && board.getTileAtPosition(x1 + 1, y1).getColour() == TileColour.WHITE;

    }
    private static boolean horizontalDownwardsBridgeExistsFromTile(int x1,int y1, Board board, TileColour playerColour){
        int x2 = x1 + 2;
        int y2 = y1 + 1;
        //Make sure all coordinates are within borders
        if( coordinatePairIsOutOfBounds(x1,y1,board) || coordinatePairIsOutOfBounds(x2,y2,board)){
            return false;
        }

        //Make sure both cells are agent-coloured
        if(board.getTileAtPosition(x1,y1).getColour() != playerColour || board.getTileAtPosition(x2,y2).getColour() != playerColour ){
            return false;
        }


        //Make sure the two cells between our cells, are white
        return board.getTileAtPosition(x1+1, y1).getColour() == TileColour.WHITE && board.getTileAtPosition(x1+1 , y1+1).getColour() == TileColour.WHITE;

    }

    private static boolean coordinatePairIsOutOfBounds(int x, int y, Board b){
        return (x<0 || x >= b.size() || y<0 || y>= b.size());
    }


}
