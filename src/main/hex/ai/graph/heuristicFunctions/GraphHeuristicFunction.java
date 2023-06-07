package main.hex.ai.graph.heuristicFunctions;

import main.hex.ai.graph.Graph;

/*
Author Nikolaj
Interface for graph heuristic functions i.e. functions that evaluate graphs, to estimate which player is ahead
 */

public interface GraphHeuristicFunction {
    public double computeGraphHeuristic(Graph g);
}
