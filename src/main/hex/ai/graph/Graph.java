package main.hex.ai.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Optional;


/*
Author Nikolaj
A weighted graph
 */


public class Graph {

    private final int numberOfNodes;
    ArrayList<Edge>[] adjacencyList;



    public Graph(int numberOfNodes){
        this.numberOfNodes = numberOfNodes;
        adjacencyList = new ArrayList[numberOfNodes];
        resetAdjacencyList();

    }

    public void resetAdjacencyList(){
        for(int i = 0; i < adjacencyList.length; i++){
            adjacencyList[i] = new ArrayList<Edge>();
        }
    }

    public void connect(int a, int b){
        adjacencyList[a].add(new Edge(a,b,1.0));
        adjacencyList[b].add(new Edge(b,a,1.0));
    }

    public void connectWithWeight(int a, int b, double fade){
        adjacencyList[a].add(new Edge(a,b,fade));
        adjacencyList[b].add(new Edge(b,a,fade));
    }

    //BFS a la CSES
    public boolean isConnectedWithMaxFade(int startNode, int endNode){
        ArrayDeque<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[numberOfNodes];

        visited[startNode] = true;

        q.add(startNode);
        while(!q.isEmpty()){
            int s = q.poll();

            for(Edge e: adjacencyList[s]){
                if(e.getWeight() < 1){
                    continue;
                }
                int neighbour = e.getTo();
                if(visited[neighbour]){
                    continue;
                }
                q.add(neighbour);
                visited[neighbour] = true;
            }
        }
        return visited[endNode];
    }



    public Optional<Double> fadeOfAdjacency(int from, int to){
        for(Edge e: adjacencyList[from]){
            if(e.getTo() == to){
                return Optional.of(e.getWeight());
            }
        }
        return Optional.empty();
    }

    public boolean adjacencyExists(int from, int to){
        for(Edge e: adjacencyList[from]){
            if(e.getTo() == to){
                return true;
            }
        }
        return false;
    }

    public int getNumberOfNodes(){
        return numberOfNodes;
    }

    public void printAdjacencies(){
        for(int i = 0; i<adjacencyList.length; i++){
            System.out.println("Node: "+i);
            for (Edge e: adjacencyList[i]
                 ) {
                System.out.println(e.getFrom() + ", F:" + e.getWeight() + ", "+e.getTo());
            }
            System.out.println();
        }
    }



}
