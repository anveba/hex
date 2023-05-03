package main.hex.ai.graph.connectionFunctions;

import main.hex.ai.graph.GridGraph;
import main.hex.board.Board;
import main.hex.board.TileColour;

public class WinConnectionFunction extends TileConnectionFunction{
    @Override
    public void connectTiles(GridGraph gridGraph, Board board, int fromX, int fromY, int toX, int toY, TileColour agentColour) {


        TileColour t1Colour = board.getTileAtPosition(fromX,fromY).getColour();
        TileColour t2Colour = board.getTileAtPosition(toX,toY).getColour();

        if(t1Colour == agentColour && t2Colour == agentColour){
            gridGraph.connectXyWithWeight(fromX,fromY,toX,toY,1);
        }

    }

    @Override
    public double getStartEndWeight() {
        return 1;
    }
}
