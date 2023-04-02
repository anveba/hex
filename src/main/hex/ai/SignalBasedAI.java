package main.hex.ai;

import main.hex.HexException;
import main.hex.Player;
import main.hex.board.Board;
import main.hex.board.Tile;
import main.hex.board.TileColour;

import java.util.ArrayList;
import java.util.Optional;

public class SignalBasedAI extends AI {
	
    private TileColour verticalColour;
    private TileColour horizontalColour;

    public SignalBasedAI(Board state, Player player){
    	super(state, player);

        if(player.winsByVerticalConnection()){
            verticalColour = getPlayer().getColour();
            horizontalColour = TileColour.opposite(getPlayer().getColour());
        }
        else {
            verticalColour = TileColour.opposite(getPlayer().getColour());
            horizontalColour = getPlayer().getColour();
        }
    }

    public AIMove getBestMove(int depth){
        return minimax(getBoard(),depth,getPlayer().winsByVerticalConnection());
    }


    private AIMove minimax(Board state, int depth, boolean maximizingPlayer){
        BoardEvaluator g = new BoardEvaluator(state,verticalColour,horizontalColour);
        double eval = g.evaluateBoard();

        if(depth == 0 || eval == Double.POSITIVE_INFINITY || eval == Double.NEGATIVE_INFINITY){
            AIMove move = new AIMove(-1,-1, eval);
            return move;
        }


        if (maximizingPlayer){
            double maxValue = Double.NEGATIVE_INFINITY;
            Optional<AIMove> maxMove = Optional.empty();

            ArrayList<AIMove> children = createChildren(state);
            for (AIMove child : children) {
                child.setValue(minimax(moveToBoard(state,child,getPlayer().getColour()), depth - 1, false).getValue());
                if (child.getValue() > maxValue) {
                    maxValue = child.getValue();
                    maxMove = Optional.of(child);
                }

            }
            if(maxMove.isEmpty()){
                throw new HexException("No move was returned by AI");
            }
            return maxMove.get();
        }

        else {

            double minValue = Double.POSITIVE_INFINITY;
            Optional<AIMove> minMove = Optional.empty();

            ArrayList<AIMove> children = createChildren(state);
            for (AIMove child : children) {

                child.setValue(minimax(moveToBoard(state,child,TileColour.opposite(getPlayer().getColour())), depth - 1, true).getValue());

                if (child.getValue() < minValue) {
                    minValue = child.getValue();
                    minMove = Optional.of(child);
                }
            }
            if(minMove.isEmpty()){
                throw new HexException("No move was returned by AI");
            }
            return minMove.get();
        }


    }

    private ArrayList<AIMove> createChildren(Board parentState){
        int max_no_of_children = parentState.size()*parentState.size();
        ArrayList<AIMove> children = new ArrayList<>((parentState.size()*parentState.size())/2);
        for(int i = 0; i<max_no_of_children; i++){
            Optional<AIMove> child = createChildMove(parentState,i);
            child.ifPresent(children::add);
        }
        return children;
    }

    private Optional<AIMove> createChildMove(Board parentState, int childNo){

        if(childNo > parentState.size() * parentState.size()){
            throw new AIException("Created too many children");
        }

        int x = childNo % parentState.size();
        int y = childNo / parentState.size();

        if(getBoard().getTileAtPosition(x,y).getColour() != TileColour.WHITE){
            return Optional.empty();
        }

        return Optional.of(new AIMove(x,y,0.0));
    }


    private Board moveToBoard(Board currentState, AIMove move, TileColour colourToPlay){
        Board childState = currentState.clone();
        childState.setTileAtPosition(new Tile(colourToPlay), move.getX(), move.getY());
        return childState;
    }
}
