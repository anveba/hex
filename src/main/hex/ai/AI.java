package main.hex.ai;

import main.hex.HexException;
import main.hex.Player;
import main.hex.ai.graph.BoardEvaluator;
import main.hex.board.Board;
import main.hex.board.Tile;
import main.hex.board.TileColour;

import java.util.ArrayList;
import java.util.Optional;


/*
Author: Nikolaj
An artificial intelligence class, using the minimax algorithm
 */

public class AI {
	
    private TileColour verticalColour;
    private TileColour horizontalColour;
    
    private BoardHashTable memoizationTable;

    private Board board;
    private Player player;

    public AI(Board state, Player player){
    	this.board = state;
        this.player = player;
    	
    	this.memoizationTable = new BoardHashTable();

        if(player.winsByVerticalConnection()){
            verticalColour = player.getColour();
            horizontalColour = TileColour.opposite(player.getColour());
        }
        else {
            verticalColour = TileColour.opposite(player.getColour());
            horizontalColour = player.getColour();
        }
    }

    public AIMove getBestMove(int depth){
        return negamax(board,depth,player.getColour());
    }


    private AIMove minimax(Board state, int depth, boolean maximizingPlayer){
    	if(memoizationTable.containsKey(state)){
            return memoizationTable.getBoard(state).get();
        }
    	
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
                child.setValue(minimax(moveToBoard(state,child,player.getColour()), depth - 1, false).getValue());
                if (child.getValue() > maxValue) {
                    maxValue = child.getValue();
                    maxMove = Optional.of(child);
                }

            }
            if(maxMove.isEmpty()){
                throw new HexException("No move was returned by AI");
            }
            memoizationTable.putBoard(state,maxMove.get());
            return maxMove.get();
        }

        else {

            double minValue = Double.POSITIVE_INFINITY;
            Optional<AIMove> minMove = Optional.empty();

            ArrayList<AIMove> children = createChildren(state);
            for (AIMove child : children) {

                child.setValue(minimax(moveToBoard(state,child,TileColour.opposite(player.getColour())), depth - 1, true).getValue());

                if (child.getValue() < minValue) {
                    minValue = child.getValue();
                    minMove = Optional.of(child);
                }
            }
            if(minMove.isEmpty()){
                throw new HexException("No move was returned by AI");
            }
            memoizationTable.putBoard(state,minMove.get());
            return minMove.get();
        }


    }

    private AIMove negamax(Board state, int depth, TileColour agentColour){
        if(memoizationTable.containsKey(state)){
            return memoizationTable.getBoard(state).get();
        }
        BoardEvaluator g = new BoardEvaluator(state,verticalColour,horizontalColour);
        double eval = g.evaluateBoard();

        if(depth == 0 || eval == Double.POSITIVE_INFINITY || eval == Double.NEGATIVE_INFINITY){
            return new AIMove(-1,-1, eval);
        }

        double maxValue = Double.NEGATIVE_INFINITY;
        Optional<AIMove> maxMove = Optional.empty();

        ArrayList<AIMove> children = createChildren(state);

        for (AIMove child : children) {
            child.setValue(-negamax(moveToBoard(state,child,agentColour), depth - 1, TileColour.opposite(agentColour)).getValue());
            if (child.getValue() >= maxValue) {
                maxValue = child.getValue();
                maxMove = Optional.of(child);
            }

        }
        if(maxMove.isEmpty()){
            throw new HexException("No move was returned by AI");
        }
        memoizationTable.putBoard(state,maxMove.get());
        return maxMove.get();
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

        if(parentState.getTileAtPosition(x,y).getColour() != TileColour.WHITE){
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
