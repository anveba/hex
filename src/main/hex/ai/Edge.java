package main.hex.ai;

public class Edge {
    private int from;
    private int to;
    private Double fade;

    public Edge(int from, int to, Double fade){
        this.from = from;
        this.to = to;
        this.fade = fade;
    }

    public int getFrom(){
        return from;
    }
    public  int getTo(){
        return to;
    }

    public double getFade(){
        return fade;
    }
}
