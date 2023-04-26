package main.hex.ai;

import main.hex.HexException;
import main.hex.Player;
import main.hex.ai.graph.BoardChildGenerator;
import main.hex.ai.graph.BoardEvaluator;
import main.hex.ai.graph.connectionFunctions.DijkstraBasedTileConnector;
import main.hex.ai.graph.connectionFunctions.SignalBasedTileConnector;
import main.hex.ai.graph.heuristicFunctions.DijkstraGraphHeuristic;
import main.hex.ai.graph.heuristicFunctions.SignalGraphHeuristic;
import main.hex.board.Board;
import main.hex.board.Tile;
import main.hex.board.TileColour;

import java.util.ArrayList;
import java.util.Optional;


/*
Author: Nikolaj
An artificial intelligence class, using the minimax/negamax algorithm
 */

public class AI {
	
    private final TileColour verticalColour;
    private final TileColour horizontalColour;
    
    private final BoardHashTable memoizationTable;

    private BoardChildGenerator boardChildGenerator;

    private final Board board;
    private final Player player;

    public AI(Board state, Player player){
    	this.board = state;
        this.player = player;
    	
    	this.memoizationTable = new BoardHashTable();
        this.boardChildGenerator = new BoardChildGenerator();

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
        //System.out.println("Called with depth: "+depth);
        AIMove m =  negamax(board,depth,player.getColour());
        //AIMove m =  minimax(board,depth, player.winsByVerticalConnection());
        //System.out.println(m.getX() + " "+ m.getY());
        return m;
    }


    private AIMove minimax(Board state, int depth, boolean maximizingPlayer){
    	if(memoizationTable.containsKey(state)){
            return memoizationTable.getBoard(state).get();
        }
    	
        BoardEvaluator g = new BoardEvaluator(state,verticalColour,horizontalColour,new SignalBasedTileConnector(), new SignalGraphHeuristic());
        double eval = g.evaluateBoard();

        if(depth == 0 || eval == Double.POSITIVE_INFINITY || eval == Double.NEGATIVE_INFINITY){
            AIMove move = new AIMove(-1,-1, eval);
            return move;
        }


        if (maximizingPlayer){
            double maxValue = Double.NEGATIVE_INFINITY;
            Optional<AIMove> maxMove = Optional.empty();

            ArrayList<AIMove> children = boardChildGenerator.createChildren(state);
            for (AIMove child : children) {
                child.setValue(minimax(moveToBoard(state,child,verticalColour), depth - 1, false).getValue());
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

            ArrayList<AIMove> children = boardChildGenerator.createChildren(state);
            for (AIMove child : children) {

                child.setValue(minimax(moveToBoard(state,child,horizontalColour), depth - 1, true).getValue());

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

        //If we've already processed this board state, no need to process it again.
    	//All transpositions will be at the same depth, so there is no loss of accuracy.
        if(memoizationTable.containsKey(state)){
            return memoizationTable.getBoard(state).get();
        }

        //We evaluate the current state of the board
        BoardEvaluator g = new BoardEvaluator(state,verticalColour,horizontalColour,new DijkstraBasedTileConnector(), new DijkstraGraphHeuristic());
        double eval = g.evaluateBoard();
        if(agentColour != verticalColour){
            eval *= -1;
        }


        //If the board state is win/loss, or we've run out of depth, we return no move, but just the value of this state
        if(depth == 0 || eval == Double.POSITIVE_INFINITY || eval == Double.NEGATIVE_INFINITY){
            return new AIMove(-1,-1, eval);
        }

        //Next part is about finding the highest value child.

        //First we set the max value to -inf, and the best move to None
        double maxValue = Double.NEGATIVE_INFINITY;
        Optional<AIMove> maxMove = Optional.empty();

        //We create a list of valid moves, that being the locations on board, currently white
        ArrayList<AIMove> children = boardChildGenerator.createChildren(state);

        //For each valid move, we insert the agent colour, and evaluate recursively, to find the maximum value move
        //Note that we multiply the child values by -1, as the recursive call, will try to minimize
        for (AIMove child : children) {
            child.setValue(-negamax(moveToBoard(state,child,agentColour), depth - 1, TileColour.opposite(agentColour)).getValue());

            if (child.getValue() >= maxValue) {
                maxValue = child.getValue();
                maxMove = Optional.of(child);
            }

        }
        //Throw an exception if no child moves were found
        if(maxMove.isEmpty()){
            throw new HexException("No move was returned by AI");
        }

        //Put the found move into the memoization table, and then return it.
        memoizationTable.putBoard(state,maxMove.get());
        return maxMove.get();
    }




    private Board moveToBoard(Board currentState, AIMove move, TileColour colourToPlay){
        Board childState = currentState.clone();
        childState.setTileAtPosition(new Tile(colourToPlay), move.getX(), move.getY());
        return childState;
    }
}
