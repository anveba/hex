package main.hex.ai.graph;

/*
Author Niko
Simple representation of a directed weighted edge
 */
public class Edge {
    private int from;
    private int to;
    private Double weight;

    public Edge(int from, int to, Double fade){
        this.from = from;
        this.to = to;
        this.weight = fade;
    }

    public int getFrom(){
        return from;
    }
    public  int getTo(){
        return to;
    }

    public double getWeight(){
        return weight;
    }
}
