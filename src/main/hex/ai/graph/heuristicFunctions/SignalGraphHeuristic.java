package main.hex.ai.graph.heuristicFunctions;

import main.hex.ai.graph.Edge;
import main.hex.ai.graph.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;

/*
Author Nikolaj

A heuristic function designed by Nikolaj to try to emulate some properties of electrical networks
while maintaining bfs runtimes

The heuristic has the characteristic that longer paths are good (Short paths on the hex board are transformed to long paths by the connection function to match this),
But also multiple paths are beneficial, as opposed to dijkstra, where we don't care whether we have 1 path of length x, or 10.
This is supposed to give value to board states that allow multiple avenues of play

 */

public class SignalGraphHeuristic implements GraphHeuristicFunction{


    public double computeGraphHeuristic(Graph g){
        int startNode = g.getNumberOfNodes() -1;
        int endNode = g.getNumberOfNodes() -2 ;


        int numberOfNodes = g.getNumberOfNodes();
        LinkedList<Edge>[] adjacencyList = g.getAdjacencyList();

        ArrayDeque<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[numberOfNodes];
        double[] signal = new double[numberOfNodes];


        visited[startNode] = true;

        for(int i = 0; i< numberOfNodes; i++){
            signal[i]=0;
        }

        signal[startNode] = 1;
        q.add(startNode);
        while(!q.isEmpty()){
            int s = q.poll();

            for(Edge e: adjacencyList[s]){
                int neighbour = e.getTo();
                signal[neighbour] += signal[s]*e.getWeight();

                if(visited[neighbour]){
                    continue;
                }
                q.add(neighbour);
                visited[neighbour] = true;
            }
        }
        return signal[endNode];

    }
}
