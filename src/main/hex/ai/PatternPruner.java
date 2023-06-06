package main.hex.ai;

import main.hex.board.Board;
import main.hex.board.TileColour;

import java.util.ArrayList;
import java.util.Optional;

public class PatternPruner {


    public static ArrayList<AIMove> pruneByPatterns(ArrayList<AIMove> moves, Board b, TileColour agent){
        ArrayList<AIMove> unprunedMoves = new ArrayList<>();

        for (AIMove m: moves
        ) { if(
                !(borderBridgePredicate(m,b,agent)
                        || deadCellCagePredicate(m,b)
                        || deadCellOpposingPairsPredicate(m,b)
                ))
        {
            unprunedMoves.add(m);
        }

        }
        //if(unprunedMoves.size() == 0){return moves;}

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


    private static final int[] neighbourXCoordinateOffsets =  { 0, 1, 1, 0,-1,-1};
    private static final int[] neighbourYCoordinateOffsets =  {-1,-1, 0, 1, 1, 0};



    //If 4 of the moves neighbours are same-coloured neighbours, the move can be pruned
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

        Optional<TileColour> primaryTileColour = Optional.empty();
        Optional<TileColour> opposingTileColour = Optional.empty();
        int chainCount = 0;

        for(int i = 0; i <= neighbourXCoordinateOffsets.length;i++){
            int primaryNeighbourIndex = i%neighbourXCoordinateOffsets.length;
            //The opposing neighbour to neighbour in
            int opposingNeighbourIndex = (i+3)%neighbourXCoordinateOffsets.length;

            if(neighbourIsOutOfBounds(b,primaryNeighbourIndex,x,y) || neighbourIsOutOfBounds(b,opposingNeighbourIndex,x,y)){
                chainCount = 0;
                primaryTileColour = Optional.empty();
                opposingTileColour = Optional.empty();
                continue;
            }
            boolean opposingTileOutOfBounds = neighbourIsOutOfBounds(b,opposingNeighbourIndex,x,y);



            if(primaryTileColour.isEmpty() && getColorOfNeighbour(b,primaryNeighbourIndex,x,y) != TileColour.WHITE){
                primaryTileColour = Optional.of(getColorOfNeighbour(b,primaryNeighbourIndex,x,y));
            }
            else if(primaryTileColour.isPresent() && getColorOfNeighbour(b,primaryNeighbourIndex,x,y) == primaryTileColour.get()){

            }
            else {
                chainCount = 0;
                primaryTileColour = Optional.empty();
                opposingTileColour = Optional.empty();
                continue;
            }


            if(opposingTileColour.isEmpty() && getColorOfNeighbour(b,opposingNeighbourIndex,x,y) != TileColour.WHITE
                    && (primaryTileColour.isEmpty() || getColorOfNeighbour(b,opposingNeighbourIndex,x,y) != primaryTileColour.get()) ){
                    chainCount++;
                    opposingTileColour = Optional.of(getColorOfNeighbour(b,opposingNeighbourIndex,x,y));
            }

            else if(opposingTileColour.isPresent() && getColorOfNeighbour(b,opposingNeighbourIndex,x,y) == opposingTileColour.get() && primaryTileColour.isEmpty()
                    || primaryTileColour.isPresent() && primaryTileColour.get() == TileColour.opposite(getColorOfNeighbour(b,opposingNeighbourIndex,x,y))){
                    chainCount++;
            }
            else {
                chainCount = 0;
                primaryTileColour = Optional.empty();
                opposingTileColour = Optional.empty();
                continue;
            }
            if(chainCount >= 2){
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
