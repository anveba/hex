package main.hex.ai.graph.connectionFunctions;


import main.hex.ai.Bridge;
import main.hex.ai.graph.GridGraph;
import main.hex.board.Board;
import main.hex.board.Tile;
import main.hex.board.TileColour;

/*
Author Nikolaj

An interface for functions that takes two tiles and connects them based on their variables
This is useful since just about any graph based board heuristic would look to connect all neighbouring tiles
But the rules for how neighbours are connected, and with which weight, depending on colour, can vary

 */
public abstract class TileConnectionFunction {




    public abstract void connectTiles(GridGraph gridGraph, Board board, int fromX, int fromY, int toX, int toY, TileColour agentColour);



    public abstract  void connectBridge(GridGraph gridGraph, Bridge b);

    public abstract void connectEnds(GridGraph gridGraph, int x1, int y1, int x2, int y2, TileColour tileColour, TileColour agentColour);
}
