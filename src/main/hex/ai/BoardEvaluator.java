package main.hex.ai;

import main.hex.Tile;


import java.util.Optional;

public class BoardEvaluator {

    private GridGraph gridGraph;
    private Tile.Colour verticalColour;
    private Tile.Colour horizontalColour;

    private Tile[][] board;

    private int boardSize;

    private final double fadeConstant = 0.1;

    /*
    Author: Nikolaj

    Board evaluator is made to evaluate a given board state for AI purposes
    Also has capabilities to check whether a player has won

     */

    public BoardEvaluator(Tile[][] board, Tile.Colour verticalColour, Tile.Colour horizontalColour) {
        gridGraph = new GridGraph(board.length);
        this.verticalColour = verticalColour;
        this.horizontalColour = horizontalColour;
        this.board = board;
        this.boardSize = board.length;
    }



    //Positive number -> Vertical is favoured
    //Negative number -> Horizontal is favoured
    public double evaluateBoard() {
        gridGraph.resetAdjacencyList();;
        gridGraph.connectStartAndEndNodesVertical();
        connectNeighboursWithColourResistance(verticalColour);
        double verticalEvaluation = gridGraph.computeSignalHeuristic();


        gridGraph.resetAdjacencyList();
        gridGraph.connectStartAndEndNodesHorizontal();
        connectNeighboursWithColourResistance(horizontalColour);
        double horizontalEvaluation = gridGraph.computeSignalHeuristic();

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

    public void connectByColour(int fromX, int fromY, int toX, int toY,Tile.Colour agentColour){
        Tile.Colour nonAgentColour = Tile.opposite(agentColour);

        Tile.Colour t1Colour = board[fromX][fromY].getColour();
        Tile.Colour t2Colour = board[toX][toY].getColour();

        if(t1Colour.equals(nonAgentColour) || t2Colour.equals(nonAgentColour)){
            return;
        }
        float fade = 1;
        if(t1Colour.equals(Tile.Colour.WHITE)){
            fade -= fadeConstant;
        }
        if(t2Colour.equals(Tile.Colour.WHITE)){
            fade -= fadeConstant;
        }
       gridGraph.connectXyWithFade(fromX,fromY,toX,toY,fade);
    }

    public void connectNeighboursWithColourResistance(Tile.Colour agentColour){
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
