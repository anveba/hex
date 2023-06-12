package main.hex.ai.graph.connectionFunctions;

import main.hex.ai.Bridge;
import main.hex.ai.graph.GridGraph;
import main.hex.ai.graph.connectionFunctions.TileConnectionFunction;
import main.hex.board.Board;
import main.hex.board.Tile;
import main.hex.board.TileColour;

/*
Author Nikolaj
A tile connection function to use for SignalBased Graph evaluation
A heuristic we tried to use in the beginning of the project

Connects two tiles with fade based on their colour
If they both have agent colour -> 1
If one has agent colour, other is white -> 1 - fadeConstant
If both are white -> 1- 2*fadeConstant
If one is nonAgentColour -> No edge
 */

public class SignalBasedTileConnector extends TileConnectionFunction {

    private final double fadeConstant = 0.2;


    public double startEndWeight = 1;

    @Override
    public void connectBridge(GridGraph gridGraph, Bridge b) {
        gridGraph.connectXyWithWeight(b.getX1(),b.getY1(), b.getX2(),b.getY2(),0.9);
    }

    @Override
    public void connectEnds(GridGraph gridGraph, int x1, int y1, int x2, int y2, TileColour tileColour, TileColour agentColour) {
        double fade = 1;
        if(tileColour.equals(TileColour.opposite(agentColour))){
            return;
        }
        if(tileColour == TileColour.WHITE){
            fade -= fadeConstant;
        }
        gridGraph.connectXyWithWeight(x1,y1,x2,y2,fade);
    }


        @Override
    public void connectTiles(GridGraph gridGraph, Board board, int fromX, int fromY, int toX, int toY, TileColour agentColour) {
        TileColour nonAgentColour = TileColour.opposite(agentColour);

        TileColour t1Colour = board.getTileAtPosition(fromX,fromY).getColour();
        TileColour t2Colour = board.getTileAtPosition(toX,toY).getColour();

        if(t1Colour.equals(nonAgentColour) || t2Colour.equals(nonAgentColour)){
            return;
        }
        double fade = 1;
        if(t1Colour.equals(TileColour.WHITE)){
            fade -= fadeConstant;
        }
        if(t2Colour.equals(TileColour.WHITE)){
            fade -= fadeConstant;
        }
        gridGraph.connectXyWithWeight(fromX,fromY,toX,toY,fade);
    }
}
