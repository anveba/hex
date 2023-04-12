package main.hex.ai.graph.connectionFunctions;

import main.hex.ai.graph.GridGraph;
import main.hex.ai.graph.connectionFunctions.TileConnectionFunction;
import main.hex.board.Board;
import main.hex.board.TileColour;



public class SignalBasedTileConnector implements TileConnectionFunction {

    private final double fadeConstant = 0.1;

    //Connects two tiles with fade based on their colour
    //If they both have agent colour -> 1
    //If one has agent colour, other is white -> 1 - fadeConstant
    //If both are white -> 1- 2*fadeConstant
    //If one is nonAgentColour -> No edge

    @Override
    public void connectTiles(GridGraph gridGraph, Board board, int fromX, int fromY, int toX, int toY, TileColour agentColour) {
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
        gridGraph.connectXyWithWeight(fromX,fromY,toX,toY,fade);
    }
}
