package main.hex.ai.graph.heuristicFunctions;

import main.hex.ai.graph.Edge;
import main.hex.ai.graph.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class SignalGraphHeuristic implements GraphHeuristicFunction{


    public double computeGraphHeuristic(Graph g){
        int startNode = g.getNumberOfNodes() -1;
        int endNode = g.getNumberOfNodes() -2 ;


        int numberOfNodes = g.getNumberOfNodes();
        ArrayList<Edge>[] adjacencyList = g.getAdjacencyList();

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
