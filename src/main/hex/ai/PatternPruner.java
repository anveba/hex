package main.hex.ai;

import main.hex.board.Board;
import main.hex.board.TileColour;
import main.hex.player.Player;

import java.util.ArrayList;

public class PatternPruner {


    public static ArrayList<AIMove> pruneByPatterns(ArrayList<AIMove> moves, Board b, TileColour agent){
        return pruneBorderBridges(moves,b,agent);
    }

    private static ArrayList<AIMove> pruneBorderBridges(ArrayList<AIMove> moves, Board b, TileColour agent){
        ArrayList<AIMove> unprunedMoves = new ArrayList<>();

        for (AIMove m: moves
             ) { if(!borderBridgePredicate(m,b,agent)){
                 unprunedMoves.add(m);
        }

        }

        return unprunedMoves;
    }



    private static boolean borderBridgePredicate(AIMove m, Board b, TileColour agent){
        int x = m.getX();
        int y = m.getY();
        if(agent.winsByVerticalConnection()){
           if(y == 0){
                if(x+1 < b.size() &&
                        b.getTileAtPosition(x+1,y).getColour() == TileColour.WHITE &&
                        b.getTileAtPosition(x+1,y+1).getColour() == TileColour.WHITE
                ){
                    return true;
                }
                if(x-1 >= 0 &&
                        b.getTileAtPosition(x-1,y).getColour() == TileColour.WHITE &&
                        b.getTileAtPosition(x,y+1).getColour() == TileColour.WHITE
                ){
                    return true;
                }

           }
           if(y == b.size() -1){
               if(x+1 < b.size() &&
                       b.getTileAtPosition(x+1,y).getColour() == TileColour.WHITE &&
                       b.getTileAtPosition(x,y-1).getColour() == TileColour.WHITE
               ){
                   return true;
               }
               if(x-1 >= 0 &&
                       b.getTileAtPosition(x-1,y).getColour() == TileColour.WHITE &&
                       b.getTileAtPosition(x-1,y-1).getColour() == TileColour.WHITE
               ){
                   return true;
               }
           }
        }
        else{
            if(x == 0){
                if(y+1 < b.size() &&
                        b.getTileAtPosition(x,y+1).getColour() == TileColour.WHITE &&
                        b.getTileAtPosition(x+1,y+1).getColour() == TileColour.WHITE
                ){
                    return true;
                }
                if(y-1 >= 0 &&
                        b.getTileAtPosition(x,y-1).getColour() == TileColour.WHITE &&
                        b.getTileAtPosition(x+1,y).getColour() == TileColour.WHITE
                ){
                    return true;
                }

            }
            if(x == b.size() -1){
                if(y+1 < b.size() &&
                        b.getTileAtPosition(x,y+1).getColour() == TileColour.WHITE &&
                        b.getTileAtPosition(x-1,y).getColour() == TileColour.WHITE
                ){
                    return true;
                }
                if(y-1 >= 0 &&
                        b.getTileAtPosition(x,y-1).getColour() == TileColour.WHITE &&
                        b.getTileAtPosition(x-1,y-1).getColour() == TileColour.WHITE
                ){
                    return true;
                }
            }
        }
        return false;
    }

}
