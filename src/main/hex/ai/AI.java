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
import java.util.Optional;
import java.util.concurrent.*;


/*
Author: Nikolaj
A class to find "good" moves, by searching the tree of possible moves,
using the minimax algorithm (negamax variant)

We use pruning of moves by inferior cell analysis, as well as alpha beta pruning
We also use hashing of board states, so we do not need to evaluate them twice
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
        doMoveSorting = false;
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



    public AIMove getBestMoveWithTimeLimit(float timeLimitInSeconds){
    	if (timeLimitInSeconds <= 0.0f)
    		throw new AIException("Non-positive time limit given");
        //Always search with depth 1 first, so that we return something regardless of the time limit
        AIMove bestMove = getBestMoveWithDepth(1);
        long timeLimitInMillis = (long)(timeLimitInSeconds * 1000.0f + 1.0f) + System.currentTimeMillis();
        int depth = 1;

        //Keep searching until the time limit is reached
        while(depth < board.size()* board.size()){
            depth++;

            //negamax will return none if time limit is reached
            Optional<AIMove> searchAtNewDepth = negamaxAB(board,depth,player.getColour(),
            		Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,
            		timeLimitInMillis);
            if(searchAtNewDepth.isEmpty()){
                break;
            }
            bestMove = searchAtNewDepth.get();
        }

        System.out.println("Found move with depth: "+depth+ " Value: "+bestMove.getValue());
        System.out.println("X: "+bestMove.getX()+", Y: "+bestMove.getY());
        return bestMove;
    }

    //Wrapper call for negamax algorithm, where we use iterative deepening until a time limit is reached
    //Meaning that we start by searching at depth 1, then 2... until the time limit is reached
    //Should not be used, is not safe, use "getBestMoveWithTimeLimit" instead
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
    The central function of this class
    Negamax is a search like minimax, but simply with a negated recursive call, instead of two different symmetric cases

    Supports time limit in system time millis
    Can be set to -1 for no time limit
     */
    private Optional<AIMove> negamaxAB(Board state, int depth, TileColour agentColour,double alpha, double beta, long endTime){

        //Returns none if end time is reached
        if(endTime != -1 && endTime <= System.currentTimeMillis()){
            return Optional.empty();
        }

        //If we've already processed this board state, no need to process it again.
        Optional<AIMove> bestChildLastTime = Optional.empty();

        if(memoizationTable.containsKey(state)){
            AIMove foundMove = memoizationTable.getBoard(state).get();
            bestChildLastTime = Optional.of(new AIMove(foundMove.getX(), foundMove.getY(), foundMove.getValue(), foundMove.getDepth()));
            if(bestChildLastTime.get().getDepth() >= depth){
                return bestChildLastTime;
            }

        }

        //We use a board evaluator to evaluate the board
        BoardEvaluator g = new BoardEvaluator(state,verticalColour,horizontalColour,tileConnectionFunction,graphHeuristicFunction);


        double eval = 0;


        //We always check whether the board is a win/loss state
        if(g.hasWonHorizontally()){
            eval = Double.NEGATIVE_INFINITY;
        }

        if(g.hasWonVertically()){
            eval = Double.POSITIVE_INFINITY;
        }

        if(depth == 0){
            eval = g.evaluateBoard();
        }

        if(agentColour != verticalColour){
            eval *= -1;
        }


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

        //We create a list of valid moves, that being the locations on board, currently uncoloured
        ArrayList<AIMove> children = boardChildGenerator.createChildren(state);


        children = PatternMatcher.pruneByPatterns(children,board,agentColour);

        //Sort the children based on previous evaluations of the board state if they exist, otherwise by setting them to 0 (neither more nor less prioritized)
        if (doMoveSorting){
            sortChildren(children, state, agentColour);
        }


        //For each valid move, we insert the agent colour, and evaluate recursively, to find the maximum value move
        for (AIMove child : children) {

            //We add a move to the board, also changing its hash
            state.makeMove(child,agentColour);

            Optional<AIMove> childEvaluation = negamaxAB(state, depth - 1, TileColour.opposite(agentColour), -beta, -alpha, endTime);

            if(childEvaluation.isEmpty()){
                return Optional.empty();
            }

            //Note that we multiply the child values by -1, as the recursive call, will try to minimize
            child.setValue(-childEvaluation.get().getValue());


            //We undo the move we made, and undo the change of hash
            state.unmakeMove(child,agentColour);

            //We simply set maxMove = max(maxMove,child)
            if (child.getValue() >= maxValue) {
                if((maxMove.isEmpty() || maxMove.get().getDepth() < child.getDepth())){
                    maxValue = child.getValue();
                    maxMove = Optional.of(child);
                }
            }

            alpha = Double.max(alpha,maxValue);

            //If we have a play where our child's value is greater than the lowest value our opponent can guarantee,
            //Then the opponent, if playing optimally, will not allow us to take this path
            //Therefore we do not need to look at other children of this state,
            //As the other player can guarantee a worse outcome, than our best play
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

        //We note down the best move, and what depth it was found at into the memoization table
        AIMove newMove = new AIMove(bestMove.getX(),bestMove.getY(),bestMove.getValue(),depth);
        memoizationTable.putBoard(state,newMove);

        //We return the best move
        return maxMove;
    }

    //We sort the child moves, based on their values in previous searches
    //If a state hasn't been evaluated previously, we set its priority to 0
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
            parentState.unmakeMove(move,agentColour);
        }
        children.sort(new AIMoveComparator());
    }


    public void setDoMoveSorting(boolean b){
        doMoveSorting = b;
    }

}
