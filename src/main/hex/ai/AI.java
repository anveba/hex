package main.hex.ai;

import main.hex.HexException;
import main.hex.ai.graph.BoardEvaluator;
import main.hex.ai.graph.connectionFunctions.DijkstraBasedTileConnector;
import main.hex.ai.graph.connectionFunctions.TileConnectionFunction;
import main.hex.ai.graph.heuristicFunctions.DijkstraGraphHeuristic;
import main.hex.ai.graph.heuristicFunctions.GraphHeuristicFunction;
import main.hex.board.Board;
import main.hex.board.TileColour;
import main.hex.player.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.*;


/*
Author: Nikolaj
A class to find "good" moves, by searching the tree of possible moves,
using the minimax algorithm (negamax variant)
 */

public class AI {

    private boolean doMoveSorting;
    private final TileColour verticalColour;
    private final TileColour horizontalColour;

    private TileConnectionFunction tileConnectionFunction;
    private GraphHeuristicFunction graphHeuristicFunction;
    
    private final BoardHashTable memoizationTable;

    private BoardChildGenerator boardChildGenerator;

    private final Board board;
    private final Player player;


    public AI(Board state, Player player){
        doMoveSorting = true;
    	this.board = state.clone();
        this.player = player;
        board.doFullHash();
    	
    	this.memoizationTable = new BoardHashTable();
        this.boardChildGenerator = new BoardChildGenerator();

        this.graphHeuristicFunction = new DijkstraGraphHeuristic();
        this.tileConnectionFunction = new DijkstraBasedTileConnector();

        if(player.winsByVerticalConnection()){
            verticalColour = player.getColour();
            horizontalColour = TileColour.opposite(player.getColour());
        }
        else {
            verticalColour = TileColour.opposite(player.getColour());
            horizontalColour = player.getColour();
        }
    }

    //Wrapper call for the negamax algorithm, where you only need to specify the depth you want to search with
    public AIMove getBestMoveWithDepth(int depth){
        //We allow to use get without the presence check, because None is only returned if the time limit is reached
        return negamaxAB(board,depth,player.getColour(),Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,-1).get();
    }

    public AIMove getBestMoveWithTimeLimit(long timeLimitInSeconds){
        AIMove bestMove = getBestMoveWithDepth(1);
        long timeLimitInMillis = timeLimitInSeconds*1000 + System.currentTimeMillis();
        int depth = 1;
        while(true){
            depth++;
            Optional<AIMove> searchAtNewDepth = negamaxAB(board,depth,player.getColour(),Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,timeLimitInMillis);
            if(searchAtNewDepth.isEmpty()){
                break;
            }
            bestMove = searchAtNewDepth.get();
        }

        System.out.println(depth);
        return bestMove;
    }

    //Wrapper call for negamax algorithm, where we use iterative deepening until a time limit is reached
    //Meaning that we start by searching at depth 1, then 2... until the time limit is reached
    public AIMove getBestMoveWithTimeLimitParallel(long timeLimitInSeconds){
        AIMove bestMove = getBestMoveWithDepth(1);

        long timeLimitMillis = timeLimitInSeconds * 1000;

        AITimedDepthRunnable runnable = new AITimedDepthRunnable(this);
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<?> future = service.submit(runnable);

        try {
            future.get(timeLimitMillis, TimeUnit.MILLISECONDS);
        } catch (Exception ignored){

        }
        finally {
            service.shutdown();
        }
        if (runnable.getBestMove() != null){
            bestMove = runnable.getBestMove();
        }
        return bestMove;
    }





    /*

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
            state.makeMove(child,agentColour);
            child.setValue(-negamax(state, depth - 1, TileColour.opposite(agentColour)).getValue());
            state.unMakeMove(child,agentColour);
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
    */

    private Optional<AIMove> negamaxAB(Board state, int depth, TileColour agentColour, double alpha, double beta, long endTime){
        if(endTime != -1 && endTime <= System.currentTimeMillis()){
            return Optional.empty();
        }

        //If we've already processed this board state, no need to process it again.
        //All transpositions will be at the same depth, so there is no loss of accuracy.
        Optional<AIMove> bestChildLastTime = Optional.empty();

        if(memoizationTable.containsKey(state)){
            bestChildLastTime = Optional.of(memoizationTable.getBoard(state).get());
            if(bestChildLastTime.get().getDepth() >= depth){
                return bestChildLastTime;
            }

        }

        //We evaluate the current state of the board
        BoardEvaluator g = new BoardEvaluator(state,verticalColour,horizontalColour,tileConnectionFunction,graphHeuristicFunction);

        double eval = 0;
        if(g.hasWonHorizontally()){
            eval = Double.NEGATIVE_INFINITY;
        }

        if(g.hasWonVertically()){
            eval = Double.POSITIVE_INFINITY;
        }

        if(depth == 0){
            eval = g.evaluateBoard();
            //System.out.println(eval);
        }

        if(agentColour != verticalColour){
            eval *= -1;
        }
        //System.out.println(eval);
        //If the board state is win/loss, or we've run out of depth, we return no move, but just the value of this state
        if(depth == 0 || eval == Double.POSITIVE_INFINITY || eval == Double.NEGATIVE_INFINITY){
            AIMove newMove = new AIMove(-1,-1, eval,0);
            memoizationTable.putBoard(state,newMove);
            return Optional.of(newMove);
        }

        //Next part is about finding the highest value child.

        //First we set the max value to -inf, and the best move to None
        double maxValue = Double.NEGATIVE_INFINITY;
        Optional<AIMove> maxMove = Optional.empty();

        //We create a list of valid moves, that being the locations on board, currently white
        ArrayList<AIMove> children = boardChildGenerator.createChildren(state);

        //Search the move that was best last time first
        if(bestChildLastTime.isPresent() && bestChildLastTime.get().getX() != -1){
            bestChildLastTime.get().setValue(Double.POSITIVE_INFINITY);
            children.add(bestChildLastTime.get());
        }
        children = PatternPruner.pruneByPatterns(children,board,agentColour);


        //Sort the children based on previous evaluations of the board state if they exist, if not by running the board evaluation heuristic
        if (doMoveSorting){
            sortChildren(children, state, agentColour);
        }


        //For each valid move, we insert the agent colour, and evaluate recursively, to find the maximum value move
        //Note that we multiply the child values by -1, as the recursive call, will try to minimize
        for (AIMove child : children) {
            state.makeMove(child,agentColour);
            Optional<AIMove> childEvaluation = negamaxAB(state, depth - 1, TileColour.opposite(agentColour), -beta, -alpha, endTime);
            if(childEvaluation.isEmpty()){
                return Optional.empty();
            }
            child.setValue(-childEvaluation.get().getValue());

            //child.setValue(childEvaluation.get().getValue());
            state.unMakeMove(child,agentColour);
            if (child.getValue() >= maxValue) {
                maxValue = child.getValue();
                maxMove = Optional.of(child);

            }

            alpha = Double.max(alpha,maxValue);

            if(alpha >= beta){
                break;
            }

        }
        //Throw an exception if no child moves were found
        if(maxMove.isEmpty()){
            throw new HexException("No move was returned by AI");
        }

        //Put the found move into the memoization table, and then return it.
        AIMove bestMove = maxMove.get();
        AIMove newMove = new AIMove(bestMove.getX(),bestMove.getY(),bestMove.getValue(),depth);
        memoizationTable.putBoard(state,newMove);
        return maxMove;
    }

    private void sortChildren(ArrayList<AIMove> children, Board parentState, TileColour agentColour){
        for (AIMove move: children
             ) {
            parentState.makeMove(move,agentColour);
            if(memoizationTable.containsKey(parentState)){
                move.setValue(memoizationTable.getBoard(parentState).get().getValue());
            }
            else {
                move.setValue(0);
            }
            parentState.unMakeMove(move,agentColour);
        }
        children.sort(new AIMoveComparator());
    }


    public void setDoMoveSorting(boolean b){
        doMoveSorting = b;
    }
/*
    private Board moveToBoard(Board currentState, AIMove move, TileColour colourToPlay){
        Board childState = currentState.clone();
        childState.setTileAtPosition(new Tile(colourToPlay), move.getX(), move.getY());
        return childState;
    }

 */
}
