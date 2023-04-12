package main.hex.ai.graph.heuristicFunctions;

import main.hex.ai.graph.Edge;

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
