package main.hex.ai.graph.heuristicFunctions;

import main.hex.ai.graph.Edge;

/*
Author Nikolaj
A pair of (int Vertex,double Key)
Used to compare vertices based on their key, for Dijkstra
 */

public class KeyedVertex implements Comparable<KeyedVertex>{

    int vertex;
    Double key;

    public KeyedVertex(int vertex,Double key){
        this.key = key;
        this.vertex = vertex;
    }

    @Override
    public int compareTo(KeyedVertex o) {
        return (int) (this.key - o.key);
    }
}
