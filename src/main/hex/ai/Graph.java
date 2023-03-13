package main.hex.ai;

import main.hex.Tile;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Graph {

    private ArrayList<Edge>[] adjacencyList;
    private int noOfNodes;
    private int boardSize;

    private Tile[][] board;

    private final Tile.Colour verticalColour;

    private final Tile.Colour horizontalColour;


    public Graph(Tile[][] board,Tile.Colour verticalColour, Tile.Colour horizontalColour){
        noOfNodes = board.length * board.length +2;
        boardSize = board.length;
        this.board = board;

        this.verticalColour = verticalColour;
        this.horizontalColour = horizontalColour;

        adjacencyList = new ArrayList[noOfNodes];
    }

    public void connect(int a,int b,float fade){
        adjacencyList[a].add(new Edge(a,b,fade));
        adjacencyList[b].add(new Edge(b,a,fade));
    }

    public int xyToNum(int x, int y){
        return y * boardSize + x;
    }

    public void connectXy(int x1, int y1, int x2, int y2, float fade){
        connect(xyToNum(x1,y1), xyToNum(x2,y2),fade);
    }


    //Tiles are connected with different fade values, based on their colour
    //If one tile is opponents colour, no connection is made
    //If both tiles are own colour, a connection with 1 fade (no loss) is made
    //If one tile is non-coloured, we get 0.9 fade
    //If both tiles are non-coloured, we get 0.8 fade
    public void connectWithColourResistance(int x1, int y1, int x2, int y2, Tile.Colour nonAgentColour){
        Tile.Colour t1Colour = board[x1][y1].getColour();
        Tile.Colour t2Colour = board[x2][y2].getColour();

        if(t1Colour.equals(nonAgentColour) || t2Colour.equals(nonAgentColour)){
            return;
        }
        float fade = 1;
        if(t1Colour.equals(Tile.Colour.WHITE)){
            fade -= 0.1;
        }
        if(t2Colour.equals(Tile.Colour.WHITE)){
            fade -= 0.1;
        }
        connectXy(x1,y1,x2,y2,fade);


    }

    public void connectVerticalEvaluation(){
        for(int i = 0; i < adjacencyList.length; i++){
            adjacencyList[i] = new ArrayList<Edge>();
        }

        for(int x = 0; x < boardSize; x++){
            connectXy(x,0, boardSize, boardSize -1,1);
            connectXy(x, boardSize -1, boardSize +1, boardSize -1,1);
        }

        for(int x = 0; x< boardSize; x++){
            for(int y = 0; y< boardSize; y++){
                if(y+1 < boardSize){
                    connectWithColourResistance(x,y,x,y+1, verticalColour);
                }
                if(y+1 < boardSize && x-1 >= 0){
                    connectWithColourResistance(x,y,x-1,y+1, verticalColour);
                }
                if(x+1 < boardSize){
                    connectWithColourResistance(x,y,x+1,y, verticalColour);
                }
            }
        }
    }

    public void connectHorizontalEvaluation(){
        for(int i = 0; i < adjacencyList.length; i++){
            adjacencyList[i] = new ArrayList<Edge>();
        }

        //Current version assumes you want to move from top to bottom
        for(int y = 0; y < boardSize; y++){
            connectXy(0,y, boardSize, boardSize -1,1);
            connectXy(boardSize -1,y, boardSize +1, boardSize -1,1);
        }

        for(int x = 0; x< boardSize; x++){
            for(int y = 0; y< boardSize; y++){
                if(y+1 < boardSize){
                    connectWithColourResistance(x,y,x,y+1, horizontalColour);
                }
                if(y+1 < boardSize && x-1 >= 0){
                    connectWithColourResistance(x,y,x-1,y+1, horizontalColour);
                }
                if(x+1 < boardSize){
                    connectWithColourResistance(x,y,x+1,y, horizontalColour);
                }
            }
        }
    }




    //Positive float: horizontal is favoured
    //Negative float: vertical is favoured
    public double boardEvaluation(){
        connectHorizontalEvaluation();
        double horizontalSignal = boardEvaluationBfs();

        connectVerticalEvaluation();
        float verticalSignal = boardEvaluationBfs();

        if(checkWinHorizontal()){
            return Double.NEGATIVE_INFINITY;
        }
        if(checkWinVertical()){
            return  Double.POSITIVE_INFINITY;
        }



        return horizontalSignal-verticalSignal;
    }

    public float boardEvaluationBfs(){
        ArrayDeque<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[noOfNodes];
        float[] signal = new float[noOfNodes];

        visited[noOfNodes -2] = true;

        for(int i = 0; i< noOfNodes; i++){
            signal[i]=0;
        }

        signal[noOfNodes -2] = 1;
        q.add(noOfNodes -2);
        while(!q.isEmpty()){
            int s = q.poll();

            for(Edge e: adjacencyList[s]){
                int neighbour = e.to;
                signal[neighbour] += signal[s]*e.fade;

                if(visited[neighbour]){
                    continue;
                }
                q.add(neighbour);
                visited[neighbour] = true;
            }
        }
        return signal[noOfNodes -1];

    }

    public void connectIfColour(int x1, int y1, int x2, int y2, Tile.Colour colour){

        Tile.Colour t1Colour = board[x1][y1].getColour();
        Tile.Colour t2Colour = board[x2][y2].getColour();
        if(t1Colour.equals(colour) && t2Colour.equals(colour)){
            connectXy(x1,y1,x2,y2,1);
        }
    }


    private void connectNeighboursIfColour(Tile.Colour c) {
        for(int i = 0; i < adjacencyList.length; i++){
            adjacencyList[i] = new ArrayList<Edge>();
        }


        if(c == verticalColour){
            for(int x = 0; x < boardSize; x++){
                connectXy(x,0, boardSize, boardSize -1,1);
                connectXy(x, boardSize -1, boardSize +1, boardSize -1,1);
            }
        }
        else {
            for(int y = 0; y < boardSize; y++){
                connectXy(0,y, boardSize, boardSize -1,1);
                connectXy(boardSize -1,y, boardSize +1, boardSize -1,1);
            }
        }



        for(int x = 0; x< boardSize; x++){
            for(int y = 0; y< boardSize; y++){
                if(y+1 < boardSize){
                    connectIfColour(x,y,x,y+1, c);
                }
                if(y+1 < boardSize && x-1 >= 0){
                    connectIfColour(x,y,x-1,y+1,c);
                }
                if(x+1 < boardSize){
                    connectIfColour(x,y,x+1,y, c);
                }
            }
        }
    }

    public boolean checkWinHorizontal(){
        connectNeighboursIfColour(horizontalColour);
        return bfs(noOfNodes - 2,noOfNodes-1);
    }

    public boolean checkWinVertical(){
        connectNeighboursIfColour(verticalColour);
        return bfs(noOfNodes - 2,noOfNodes-1);
    }

    public boolean bfs(int startNode,int endNode){
        ArrayDeque<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[noOfNodes];

        visited[startNode] = true;

        q.add(startNode);
        while(!q.isEmpty()){
            int s = q.poll();

            for(Edge e: adjacencyList[s]){
                int neighbour = e.to;
                if(visited[neighbour]){
                    continue;
                }
                q.add(neighbour);
                visited[neighbour] = true;
            }
        }
        return visited[endNode];
    }
}
