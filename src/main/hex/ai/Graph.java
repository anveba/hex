package main.hex.ai;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Optional;

public class Graph {

    private int numberOfNodes;
    private ArrayList<Edge>[] adjacencyList;

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

    public void connectWithFade(int a, int b,double fade){
        adjacencyList[a].add(new Edge(a,b,fade));
        adjacencyList[b].add(new Edge(b,a,fade));
    }

    //BFS a la CSES
    public boolean isConnected(int startNode, int endNode){
        ArrayDeque<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[numberOfNodes];

        visited[startNode] = true;

        q.add(startNode);
        while(!q.isEmpty()){
            int s = q.poll();

            for(Edge e: adjacencyList[s]){
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
                return Optional.of(e.getFade());
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
}
