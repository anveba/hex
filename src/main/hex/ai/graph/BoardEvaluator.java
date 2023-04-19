package main.hex.ai.graph;

import java.util.Optional;

import main.hex.ai.graph.connectionFunctions.DijkstraBasedTileConnector;
import main.hex.ai.graph.connectionFunctions.TileConnectionFunction;
import main.hex.ai.graph.connectionFunctions.WinConnectionFunction;
import main.hex.ai.graph.heuristicFunctions.DijkstraGraphHeuristic;
import main.hex.ai.graph.heuristicFunctions.GraphHeuristicFunction;
import main.hex.ai.graph.connectionFunctions.SignalBasedTileConnector;
import main.hex.ai.graph.heuristicFunctions.SignalGraphHeuristic;
import main.hex.board.Board;
import main.hex.board.TileColour;

public class BoardEvaluator {

    private GridGraph gridGraph;
    private TileColour verticalColour;
    private TileColour horizontalColour;

    private Board board;

    private int boardSize;

    private TileConnectionFunction tileConnectionFunction;
    private GraphHeuristicFunction graphHeuristicFunction;


    /*
    Author: Nikolaj

    Board evaluator is made to evaluate a given board state for AI purposes
    Also has capabilities to check whether a player has won

     */

    public BoardEvaluator(Board board, TileColour verticalColour, TileColour horizontalColour,TileConnectionFunction t, GraphHeuristicFunction h) {
        gridGraph = new GridGraph(board.size());
        this.verticalColour = verticalColour;
        this.horizontalColour = horizontalColour;
        this.board = board;
        this.boardSize = board.size();
        this.tileConnectionFunction = t;
        this.graphHeuristicFunction = h;
    }


    //Evaluates current boardstate, using signal heuristics
    //Positive number -> Vertical is favoured
    //Negative number -> Horizontal is favoured
    public double evaluateBoard() {
        gridGraph.resetAdjacencyList();;
        gridGraph.connectStartAndEndNodesVertical(tileConnectionFunction.startEndWeight);
        connectNeighboursWithColourWeight(verticalColour,tileConnectionFunction);
        double verticalEvaluation = graphHeuristicFunction.computeGraphHeuristic(gridGraph);


        gridGraph.resetAdjacencyList();
        gridGraph.connectStartAndEndNodesHorizontal(tileConnectionFunction.startEndWeight);
        connectNeighboursWithColourWeight(horizontalColour,tileConnectionFunction);
        double horizontalEvaluation = graphHeuristicFunction.computeGraphHeuristic(gridGraph);

        if(hasWonHorizontally()){
            return Double.NEGATIVE_INFINITY;

        }

        if(hasWonVertically()){
            return  Double.POSITIVE_INFINITY;
        }

        return verticalEvaluation - horizontalEvaluation;
    }


    public void connectByColour(int x1, int y1, int x2, int y2, TileColour agentColour) {
        tileConnectionFunction.connectTiles(gridGraph,board,x1,y1,x2,y2,agentColour);
    }


    public boolean hasWonVertically() {

        gridGraph.resetAdjacencyList();
        gridGraph.connectStartAndEndNodesVertical(tileConnectionFunction.startEndWeight);
        connectNeighboursWithColourWeight(verticalColour,new WinConnectionFunction());
        return gridGraph.startAndEndAreConnected();
    }

    public boolean hasWonHorizontally() {
        gridGraph.resetAdjacencyList();
        gridGraph.connectStartAndEndNodesHorizontal(tileConnectionFunction.startEndWeight);
        connectNeighboursWithColourWeight(horizontalColour, new WinConnectionFunction());
        return gridGraph.startAndEndAreConnected();
    }

    public Optional<Double> fadeOfAdjacencyXY(int fromX, int fromY, int toX, int toY) {
        return gridGraph.fadeOfAdjacencyXY(fromX,fromY,toX,toY);
    }

    public void connectHorizontalEvaluation() {
        connectNeighboursWithColourWeight(horizontalColour, tileConnectionFunction);
        gridGraph.connectStartAndEndNodesHorizontal(tileConnectionFunction.startEndWeight);

    }

    public void connectVerticalEvaluation() {
        connectNeighboursWithColourWeight(verticalColour,tileConnectionFunction);
        gridGraph.connectStartAndEndNodesVertical(tileConnectionFunction.startEndWeight);

    }


    //Connects all neighbours based on their colours
    public void connectNeighboursWithColourWeight(TileColour agentColour,TileConnectionFunction t){
        for(int x = 0; x< boardSize; x++){
            for(int y = 0; y< boardSize; y++){
                if(y+1 < boardSize){
                    t.connectTiles(gridGraph,board,x,y,x,y+1,agentColour);
                }
                if(y+1 < boardSize && x+1 < boardSize){
                    t.connectTiles(gridGraph,board,x,y,x+1,y+1,agentColour);
                }
                if(x+1 < boardSize){
                    t.connectTiles(gridGraph,board,x,y,x+1,y,agentColour);
                }
            }
        }

    }
}