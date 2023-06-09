package main.hex.ai.graph;

import java.util.ArrayList;
import java.util.Optional;

import main.hex.ai.Bridge;
import main.hex.ai.BridgeFinder;
import main.hex.ai.graph.connectionFunctions.TileConnectionFunction;
import main.hex.ai.graph.connectionFunctions.WinConnectionFunction;
import main.hex.ai.graph.heuristicFunctions.GraphHeuristicFunction;
import main.hex.board.Board;
import main.hex.board.TileColour;

/*
   Author: Nikolaj

   Board evaluator is made to evaluate a given board state for AI purposes
   Also has capabilities to check whether a player has won

    */
public class BoardEvaluator {


    private static int evaluationCount = 0;

    private GridGraph gridGraph;
    private TileColour verticalColour;
    private TileColour horizontalColour;

    private boolean doBridgeConnections;

    private Board board;

    private int boardSize;

    //The board evaluator can be made to use different heuristics,
    //By changing the tileConnectionFunction, we can change the rules for connecting neighbours
    private TileConnectionFunction tileConnectionFunction;
    //By changing the graphHeuristicFunction we can change how the resulting graph is evaluated
    private GraphHeuristicFunction graphHeuristicFunction;




    public BoardEvaluator(Board board, TileColour verticalColour, TileColour horizontalColour,TileConnectionFunction t, GraphHeuristicFunction h) {
        gridGraph = new GridGraph(board.size());
        this.verticalColour = verticalColour;
        this.horizontalColour = horizontalColour;
        this.board = board;
        this.boardSize = board.size();
        this.tileConnectionFunction = t;
        this.graphHeuristicFunction = h;
        this.doBridgeConnections = true;
    }



    public void setDoBridgeConnections(boolean doBridgeConnections) {
        this.doBridgeConnections = doBridgeConnections;
    }

    public void connectBridges(TileColour playerColour){
        ArrayList<Bridge> bridges = BridgeFinder.findLevelOneBridges(board,playerColour);

        for (Bridge b: bridges
             ) {
            tileConnectionFunction.connectBridge(gridGraph,b);

        }
    }

    public static int getEvaluationCount() {
        return evaluationCount;
    }
    public static void resetEvaluationCount(){
        evaluationCount = 0;
    }


    //Evaluates current board state
    //Positive number -> Vertical is favoured
    //Negative number -> Horizontal is favoured
    public double evaluateBoard() {
        evaluationCount++;
        gridGraph.resetAdjacencyList();;
        gridGraph.connectStartAndEndNodesVertical(tileConnectionFunction.getStartEndWeight());
        connectNeighboursWithColourWeight(verticalColour,tileConnectionFunction);
        if(doBridgeConnections){
            connectBridges(verticalColour);
        }
        double verticalEvaluation = graphHeuristicFunction.computeGraphHeuristic(gridGraph);



        gridGraph.resetAdjacencyList();
        gridGraph.connectStartAndEndNodesHorizontal(tileConnectionFunction.getStartEndWeight());
        connectNeighboursWithColourWeight(horizontalColour,tileConnectionFunction);
        if(doBridgeConnections){
            ArrayList<Edge>[] adj1 = gridGraph.getAdjacencyList();
            connectBridges(horizontalColour);
            ArrayList<Edge>[] adj2 = gridGraph.getAdjacencyList();
            for(int i = 0; i < adj1.length; i++){
                if(adj1[i].size() != adj2[i].size()){
                    System.out.println("Diff");
                }
            }
        }
        double horizontalEvaluation = graphHeuristicFunction.computeGraphHeuristic(gridGraph);



        if(hasWonHorizontally()){
            return Double.NEGATIVE_INFINITY;

        }

        if(hasWonVertically()){
            return  Double.POSITIVE_INFINITY;
        }

        return verticalEvaluation - horizontalEvaluation;
    }


    //Tells the chosen tileConnectionFunction to connect two tiles, based on their colours, and the agent colour
    public void connectByColour(int x1, int y1, int x2, int y2, TileColour agentColour) {
        tileConnectionFunction.connectTiles(gridGraph,board,x1,y1,x2,y2,agentColour);
    }


    public boolean hasWonVertically() {
        gridGraph.resetAdjacencyList();
        gridGraph.connectStartAndEndNodesVertical(new WinConnectionFunction().getStartEndWeight());
        connectNeighboursWithColourWeight(verticalColour,new WinConnectionFunction());
        return gridGraph.startAndEndAreConnected();
    }

    public boolean hasWonHorizontally() {
        gridGraph.resetAdjacencyList();
        gridGraph.connectStartAndEndNodesHorizontal(new WinConnectionFunction().getStartEndWeight());
        connectNeighboursWithColourWeight(horizontalColour, new WinConnectionFunction());
        return gridGraph.startAndEndAreConnected();
    }

    public Optional<Double> weightOfAdjacencyXY(int fromX, int fromY, int toX, int toY) {
        return gridGraph.weightOfAdjacencyXY(fromX,fromY,toX,toY);
    }


    public void connectHorizontalEvaluation() {
        connectNeighboursWithColourWeight(horizontalColour, tileConnectionFunction);
        gridGraph.connectStartAndEndNodesHorizontal(tileConnectionFunction.getStartEndWeight());

    }

    public void connectVerticalEvaluation() {
        connectNeighboursWithColourWeight(verticalColour,tileConnectionFunction);
        gridGraph.connectStartAndEndNodesVertical(tileConnectionFunction.getStartEndWeight());

    }

    public GridGraph getGridGraph() {
        return gridGraph;
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
