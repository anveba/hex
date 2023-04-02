package main.hex.ai;

import java.util.Optional;

import main.hex.board.Board;
import main.hex.board.Tile;
import main.hex.board.TileColour;

public class BoardEvaluator {

    private GridGraph gridGraph;
    private TileColour verticalColour;
    private TileColour horizontalColour;

    private Board board;

    private int boardSize;

    private final double fadeConstant = 0.1;

    /*
    Author: Nikolaj

    Board evaluator is made to evaluate a given board state for AI purposes
    Also has capabilities to check whether a player has won

     */

    public BoardEvaluator(Board board, TileColour verticalColour, TileColour horizontalColour) {
        gridGraph = new GridGraph(board.size());
        this.verticalColour = verticalColour;
        this.horizontalColour = horizontalColour;
        this.board = board;
        this.boardSize = board.size();
    }


    //Evaluates current boardstate, using signal heuristics
    //Positive number -> Vertical is favoured
    //Negative number -> Horizontal is favoured
    public double evaluateBoard() {
        gridGraph.resetAdjacencyList();;
        gridGraph.connectStartAndEndNodesVertical();
        connectNeighboursWithColourResistance(verticalColour);
        double verticalEvaluation = gridGraph.computeSignalHeuristic(gridGraph.getNumberOfNodes()-2, gridGraph.getNumberOfNodes()-1);


        gridGraph.resetAdjacencyList();
        gridGraph.connectStartAndEndNodesHorizontal();
        connectNeighboursWithColourResistance(horizontalColour);
        double horizontalEvaluation = gridGraph.computeSignalHeuristic(gridGraph.getNumberOfNodes()-2, gridGraph.getNumberOfNodes()-1);

        if(hasWonHorizontally()){
            return Double.NEGATIVE_INFINITY;
        }
        if(hasWonVertically()){
            return  Double.POSITIVE_INFINITY;
        }

        return horizontalEvaluation - verticalEvaluation;
    }



    public boolean hasWonVertically() {
        gridGraph.resetAdjacencyList();
        gridGraph.connectStartAndEndNodesVertical();
        connectNeighboursWithColourResistance(verticalColour);
        return gridGraph.startAndEndAreConnected();
    }

    public boolean hasWonHorizontally() {
        gridGraph.resetAdjacencyList();
        gridGraph.connectStartAndEndNodesHorizontal();
        connectNeighboursWithColourResistance(horizontalColour);
        return gridGraph.startAndEndAreConnected();
    }

    public Optional<Double> fadeOfAdjacencyXY(int fromX, int fromY, int toX, int toY) {
        return gridGraph.fadeOfAdjacencyXY(fromX,fromY,toX,toY);
    }

    public void connectHorizontalEvaluation() {
        connectNeighboursWithColourResistance(horizontalColour);
        gridGraph.connectStartAndEndNodesHorizontal();

    }

    public void connectVerticalEvaluation() {
        connectNeighboursWithColourResistance(verticalColour);
        gridGraph.connectStartAndEndNodesVertical();

    }

    //Connects two tiles with fade based on their colour
    //If they both have agent colour -> 1
    //If one has agent colour, other is white -> 1 - fadeConstant
    //If both are white -> 1- 2*fadeConstant
    //If one is nonAgentColour -> No edge
    public void connectByColour(int fromX, int fromY, int toX, int toY,TileColour agentColour){
        TileColour nonAgentColour = TileColour.opposite(agentColour);

        TileColour t1Colour = board.getTileAtPosition(fromX,fromY).getColour();
        TileColour t2Colour = board.getTileAtPosition(toX,toY).getColour();

        if(t1Colour.equals(nonAgentColour) || t2Colour.equals(nonAgentColour)){
            return;
        }
        float fade = 1;
        if(t1Colour.equals(TileColour.WHITE)){
            fade -= fadeConstant;
        }
        if(t2Colour.equals(TileColour.WHITE)){
            fade -= fadeConstant;
        }
       gridGraph.connectXyWithFade(fromX,fromY,toX,toY,fade);
    }


    //Connects all neighbours based on their colours
    public void connectNeighboursWithColourResistance(TileColour agentColour){
        for(int x = 0; x< boardSize; x++){
            for(int y = 0; y< boardSize; y++){
                if(y+1 < boardSize){
                    connectByColour(x,y,x,y+1, agentColour);
                }
                if(y+1 < boardSize && x-1 >= 0){
                    connectByColour(x,y,x-1,y+1, agentColour);
                }
                if(x+1 < boardSize){
                    connectByColour(x,y,x+1,y, agentColour);
                }
            }
        }

    }
}
