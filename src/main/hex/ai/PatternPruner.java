package main.hex.ai;

import main.hex.board.Board;
import main.hex.board.TileColour;

import java.util.ArrayList;
import java.util.Optional;

/*
Author Nikolaj
Class to prune moves, by looking for patterns on the board
The mathematical term is inferior cell analysis
The code in some of the contained functions isn't super readable,
but that is hard to avoid, as we are essentially checking for a bunch of possible combinations
 */

public class PatternPruner {


    //Checks all moves against various prunable predicates
    public static ArrayList<AIMove> pruneByPatterns(ArrayList<AIMove> moves, Board b, TileColour agent){
        ArrayList<AIMove> unPrunedMoves = new ArrayList<>();
        for (AIMove m: moves
        ) { if(
                !(borderBridgePredicate(m,b,agent)
                        || deadCellCagePredicate(m,b)
                        || deadCellOpposingPairsPredicate(m,b)
                ))
        {
            unPrunedMoves.add(m);
        }

        }
        //Should we somehow have removed all moves, we backtrack
        //This is not supposed to happen, but is added as a fail-safe
        if(unPrunedMoves.size() == 0){return moves;}

        return unPrunedMoves;
    }


    //Searchs for bridges near all borders

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


    private static final int[] neighbourXCoordinateOffsets =  { 0, 1, 1, 0,-1,-1};
    private static final int[] neighbourYCoordinateOffsets =  {-1,0, 1, 1, 0, -1};



    //If 4 of the moves neighbours are in a same coloured group, the move can be pruned
    private static boolean deadCellCagePredicate(AIMove m, Board b){
        int x = m.getX();
        int y = m.getY();

        Optional<TileColour> currentChainColour = Optional.empty();
        int lengthOfCurrentChain = 0;

        for(int i = 0; i <= neighbourXCoordinateOffsets.length; i++){
            int j = i% neighbourXCoordinateOffsets.length; //We need to run a second time on the first cell

            //If the neighbour is out of bounds, it counts as one neighbour coloured
            if(x+ neighbourXCoordinateOffsets[j] < 0 || x+ neighbourXCoordinateOffsets[j] >= b.size()  || y+ neighbourYCoordinateOffsets[j] <0 || y+ neighbourYCoordinateOffsets[j] >= b.size() ){
                lengthOfCurrentChain = 1;
                currentChainColour = Optional.empty();
                continue;
            }
            if((b.getTileAtPosition(x+ neighbourXCoordinateOffsets[j],y+ neighbourYCoordinateOffsets[j]).getColour() == TileColour.WHITE)){
                lengthOfCurrentChain = 0;
                continue;
            }
            if(currentChainColour.isEmpty()){
                lengthOfCurrentChain++;
                currentChainColour = Optional.of(b.getTileAtPosition(x+ neighbourXCoordinateOffsets[j],y+ neighbourYCoordinateOffsets[j]).getColour());
            }
            else if(b.getTileAtPosition(x+ neighbourXCoordinateOffsets[j],y+ neighbourYCoordinateOffsets[j]).getColour() == currentChainColour.get()){
                lengthOfCurrentChain++;

            }
            else {
                lengthOfCurrentChain = 1;
            }
            if(lengthOfCurrentChain >= 4){
                return true;
            }

        }
        return false;
    }

    //If a cells neighbour contains two opposing pairs of same colour, we can prune it
    private static boolean deadCellOpposingPairsPredicate(AIMove m, Board b){
        int x = m.getX();
        int y = m.getY();

        //Check that all neighbours are inside the board
        for(int i = 0; i < neighbourXCoordinateOffsets.length;i++){
            if(neighbourIsOutOfBounds(b,i,x,y)){
                return false;
            }
        }


        for(int i = 0; i < neighbourXCoordinateOffsets.length /2; i++){
            int firstPairFirstIndex = i % neighbourXCoordinateOffsets.length;
            int firstPairSecondIndex = (i+1) % neighbourXCoordinateOffsets.length;

            int secondPairFirstIndex = (i+3) % neighbourXCoordinateOffsets.length;
            int secondPairSecondIndex = (i+4) % neighbourXCoordinateOffsets.length;

            //Check for opposing pairs
            if(
                    getColorOfNeighbour(b,firstPairFirstIndex,x,y) == getColorOfNeighbour(b,firstPairSecondIndex,x,y)
                    && getColorOfNeighbour(b,firstPairFirstIndex,x,y) != TileColour.WHITE
                    && getColorOfNeighbour(b,firstPairFirstIndex,x,y) == TileColour.opposite(getColorOfNeighbour(b,secondPairFirstIndex,x,y))
                    && getColorOfNeighbour(b,secondPairFirstIndex,x,y) == getColorOfNeighbour(b,secondPairSecondIndex,x,y)
            ){
                return true;
            }
        }

        return false;
    }

    private static boolean neighbourIsOutOfBounds(Board b, int neighbourIndex, int x, int y){
        return outOfBounds(b,x+neighbourXCoordinateOffsets[neighbourIndex],y+neighbourYCoordinateOffsets[neighbourIndex]);
    }

    private static TileColour getColorOfNeighbour(Board b, int neighbourIndex,int x, int y){
        return b.getTileAtPosition(x+neighbourXCoordinateOffsets[neighbourIndex],y+neighbourYCoordinateOffsets[neighbourIndex]).getColour();
    }

    private static boolean outOfBounds(Board b, int x, int y){
        return x < 0 || y < 0 || x >= b.size() || y >= b.size();
    }

}
