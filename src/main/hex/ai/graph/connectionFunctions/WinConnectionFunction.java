package main.hex.ai.graph.connectionFunctions;

import main.hex.ai.Bridge;
import main.hex.ai.graph.GridGraph;
import main.hex.board.Board;
import main.hex.board.TileColour;


/*
Author Nikolaj

A tile connection function that has the simple rule that only agent-coloured pairs get connected.
Used to setup for bfs to check for game win
 */
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
    public void connectBridge(GridGraph gridGraph, Bridge b) {
    }

    @Override
    public void connectEnds(GridGraph gridGraph, int x1, int y1, int x2, int y2, TileColour tileColour, TileColour agentColour) {
        gridGraph.connectXyWithWeight(x1,y1,x2,y2,1);

    }
}
