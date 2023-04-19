package main.hex.ai.graph.heuristicFunctions;

import main.hex.ai.graph.Edge;
import main.hex.ai.graph.Graph;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.SortedMap;

public class DijkstraGraphHeuristic implements GraphHeuristicFunction {

    @Override
    public double computeGraphHeuristic(Graph g) {
        return Double.MAX_VALUE - shortestPath(g, g.getNumberOfNodes()-1,g.getNumberOfNodes()-2);
    }

    //Dijkstra a la CSES
    public double shortestPath(Graph g, int startNode, int endNode) {
        //g.printAdjacencies();

        PriorityQueue<KeyedVertex> priorityQ = new PriorityQueue<>();
        ArrayList<Edge>[] adj = g.getAdjacencyList();
        double[] distance = new double[g.getNumberOfNodes()];
        boolean[] processed = new boolean[g.getNumberOfNodes()];
        for(int i = 0; i < g.getNumberOfNodes(); i++){
            distance[i] = Double.POSITIVE_INFINITY;
            processed[i] = false;
        }
        distance[startNode] = 0.0;
        priorityQ.add(new KeyedVertex(startNode,0.0));

        while(!priorityQ.isEmpty()){
            int a = priorityQ.poll().vertex;
            if(processed[a]){
                continue;
            }
            for(Edge e : adj[a]){
                double weight = e.getWeight();
                int b = e.getTo();
                if(distance[a] + weight < distance[b]){
                    distance[b] = distance[a] + weight;
                    priorityQ.add(new KeyedVertex(b,distance[b]));

                }
            }
        }

        return distance[endNode];

    }
}
