package main.hex.ai;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Optional;

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

    public void connectWithFade(int a, int b,double fade){
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
                if(e.getFade() < 1){
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

    public double computeSignalHeuristic(){

        ArrayDeque<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[numberOfNodes];
        double[] signal = new double[numberOfNodes];

        int startNode = numberOfNodes -1 ;
        int endNode = numberOfNodes -2;

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
                signal[neighbour] += signal[s]*e.getFade();

                if(visited[neighbour]){
                    continue;
                }
                q.add(neighbour);
                visited[neighbour] = true;
            }
        }
        return signal[endNode];

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

    public int getNumberOfNodes(){
        return numberOfNodes;
    }

    public void printAdjacencies(){
        for(int i = 0; i<adjacencyList.length; i++){
            System.out.println("Node: "+i);
            for (Edge e: adjacencyList[i]
                 ) {
                System.out.println(e.getFrom() + ", F:" + e.getFade() + ", "+e.getTo());
            }
            System.out.println();
        }
    }
}
