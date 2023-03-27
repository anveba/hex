package main.hex.ai;

import main.hex.Board;
import main.hex.HexException;
import main.hex.Player;
import main.hex.Tile;

import java.util.ArrayList;
import java.util.Optional;

public class AI {
    private final Board currentState;


    private Tile.Colour agentColour;

    private boolean agentPlaysVertical;
    private Tile.Colour verticalColour;
    private Tile.Colour horizontalColour;

    public AI(Board state, Player player){
        this.currentState = state.clone();
        this.agentColour = player.getPlayerColour();
        this.agentPlaysVertical = player.winsByVerticalConnection();

        if(agentPlaysVertical){
            verticalColour = agentColour;
            horizontalColour = Tile.opposite(agentColour);
        }
        else {
            verticalColour = Tile.opposite(agentColour);
            horizontalColour = agentColour;
        }
    }

    public Move getBestMove(int depth,Player player){
        return minimax(currentState,depth,player.winsByVerticalConnection());
    }


    private Move minimax(Board state, int depth, boolean maximizingPlayer){
        BoardEvaluator g = new BoardEvaluator(state,verticalColour,horizontalColour);
        double eval = g.evaluateBoard();

        if(depth == 0 || eval == Double.POSITIVE_INFINITY || eval == Double.NEGATIVE_INFINITY){
            Move move = new Move(-1,-1);
            move.setValue(eval);
            return move;
        }


        if (maximizingPlayer){
            double maxValue = Double.NEGATIVE_INFINITY;
            Optional<Move> maxMove = Optional.empty();

            ArrayList<Move> children = createChildren(state);
            for (Move child : children) {
                child.setValue(minimax(moveToBoard(state,child,agentColour), depth - 1, false).getValue());
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
            Optional<Move> minMove = Optional.empty();

            ArrayList<Move> children = createChildren(state);
            for (Move child : children) {

                child.setValue(minimax(moveToBoard(state,child,Tile.opposite(agentColour)), depth - 1, true).getValue());

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

    private ArrayList<Move> createChildren(Board parentState){
        int max_no_of_children = parentState.size()*parentState.size();
        ArrayList<Move> children = new ArrayList<>((parentState.size()*parentState.size())/2);
        for(int i = 0; i<max_no_of_children; i++){
            Optional<Move> child = createChildMove(parentState,i);
            child.ifPresent(children::add);
        }
        return children;
    }

    private Optional<Move> createChildMove(Board parentState, int childNo){

        if(childNo > parentState.size() * parentState.size()){
            throw new AIException("Created too many children");
        }

        int x = childNo % parentState.size();
        int y = childNo / parentState.size();

        if(currentState.getTileAtPosition(x,y).getColour() != Tile.Colour.WHITE){
            return Optional.empty();
        }

        return Optional.of(new Move(x,y));
    }


    private Board moveToBoard(Board currentState, Move move, Tile.Colour colourToPlay){
        Board childState = currentState.clone();
        childState.getTileAtPosition(move.getX(), move.getY()).setColour(colourToPlay);
        return childState;
    }





}
