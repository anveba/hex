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

    public BoardEvaluator(Tile[][] board, Tile.Colour verticalColour, Tile.Colour horizontalColour) {
        gridGraph = new GridGraph(board.length * board.length);
        this.verticalColour = verticalColour;
        this.horizontalColour = horizontalColour;
        this.board = board;
        this.boardSize = board.length;
    }


    public double evaluateBoard() {
        return 0;
    }

    public boolean hasWonVertically() {
        return true;
    }

    public boolean hasWonHorizontally() {
        return true;
    }

    public Optional<Double> fadeOfAdjacencyXY(int fromX, int fromY, int toX, int toY) {
        return gridGraph.fadeOfAdjacencyXY(fromX,fromY,toX,toY);
    }

    public void connectHorizontalEvaluation() {

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
                    connectByColour(x,y,x,y+1, verticalColour);
                }
                if(y+1 < boardSize && x-1 >= 0){
                    connectByColour(x,y,x-1,y+1, verticalColour);
                }
                if(x+1 < boardSize){
                    connectByColour(x,y,x+1,y, verticalColour);
                }
            }
        }

    }
}
