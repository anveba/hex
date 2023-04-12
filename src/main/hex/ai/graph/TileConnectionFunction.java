package main.hex.ai.graph;


import main.hex.board.Board;
import main.hex.board.TileColour;

/*
Author Nikolaj

An interface for functions that takes two tiles and connects them based on their variables
This is useful since just about any graph based board heuristic would look to connect all neighbouring tiles
But the weight for these connections may vary

 */
public interface TileConnectionFunction {



    Board connectTiles(GridGraph gridGraph, Board board, int fromX, int fromY, int toX, int toY, TileColour agentColour);
}
