package main.hex.ai;

import main.hex.Tile;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Graph2 {

    private ArrayList<Edge>[] adjacencyList;
    private int noOfNodes;
    private int boardSize;

    private Tile[][] board;

    private final Tile.Colour verticalColour;

    private final Tile.Colour horizontalColour;

    private final double fadeConstant = 0.1;


    public Graph2(Tile[][] board, Tile.Colour verticalColour, Tile.Colour horizontalColour){
        noOfNodes = board.length * board.length +2;
        boardSize = board.length;
        this.board = board;

        this.verticalColour = verticalColour;
        this.horizontalColour = horizontalColour;

        adjacencyList = new ArrayList[noOfNodes];
    }

    public void clearAdjacencyList(){
        for(int i = 0; i < adjacencyList.length; i++){
            adjacencyList[i] = new ArrayList<Edge>();
        }
    }

    public void connect(int a,int b,double fade){
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
            fade -= fadeConstant;
        }
        if(t2Colour.equals(Tile.Colour.WHITE)){
            fade -= fadeConstant;
        }
        connectXy(x1,y1,x2,y2,fade);


    }


    /*
    Connects the nodes, to prepare for evaluation of the vertical players heuristic
    Connects all neighbouring tiles, with colour resistance
    Connects all the topmost tiles to a startnode, and all the bottommost tiles to an endnode
     */
    public void connectVerticalEvaluation(){
        connectStartAndEndNodesVertical();
        connectNeighbouringTilesWithResistance(verticalColour);
    }


    /*
    Connects the nodes, to prepare for evaluation of the horizontal players heuristic
    Connects all neighbouring tiles, with colour resistance
    Connects all the leftmost tiles to a startnode, and all the rightmost tiles to an endnode
     */
    public void connectHorizontalEvaluation(){

        connectStartAndEndNodesHorizontal();
        connectNeighbouringTilesWithResistance(horizontalColour);
    }

    
    /*
    Calls connect with colour resistance, for all neighbouring tiles
     */
    private void connectNeighbouringTilesWithResistance(Tile.Colour verticalColour) {
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

    

    //The main function for the board heuristic
    //Positive float: horizontal is favoured
    //Negative float: vertical is favoured
    //Returns -+ inf if a player has won
    public double boardEvaluation(){
        clearAdjacencyList();
        connectHorizontalEvaluation();
        double horizontalSignal = computeSignalHeuristic(noOfNodes-1,noOfNodes-2);

        clearAdjacencyList();
        connectVerticalEvaluation();
        double verticalSignal = computeSignalHeuristic(noOfNodes-1,noOfNodes-2);

        if(hasWonHorizontally()){
            return Double.NEGATIVE_INFINITY;
        }
        if(hasWonVertically()){
            return  Double.POSITIVE_INFINITY;
        }
        return horizontalSignal-verticalSignal;
    }

    public double computeSignalHeuristic(int startNode,int endNode){
        ArrayDeque<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[noOfNodes];
        double[] signal = new double[noOfNodes];

        visited[startNode] = true;

        for(int i = 0; i< noOfNodes; i++){
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

    public void connectIfBothTilesHaveColour(int x1, int y1, int x2, int y2, Tile.Colour colour){
        Tile.Colour t1Colour = board[x1][y1].getColour();
        Tile.Colour t2Colour = board[x2][y2].getColour();
        if(t1Colour.equals(colour) && t2Colour.equals(colour)){
            connectXy(x1,y1,x2,y2,1);
        }
    }


    private void connectStartAndEndNodesHorizontal(){
        for(int x = 0; x < boardSize; x++){
            connectXy(x,0, boardSize, boardSize -1,1);
            connectXy(x, boardSize -1, boardSize +1, boardSize -1,1);
        }
    }

    private void connectStartAndEndNodesVertical(){
        for(int y = 0; y < boardSize; y++){
            connectXy(0,y, boardSize, boardSize -1,1);
            connectXy(boardSize -1,y, boardSize +1, boardSize -1,1);
        }
    }

    private void connectNeighboursIfTheyHaveGivenColour(Tile.Colour c) {
        for(int x = 0; x< boardSize; x++){
            for(int y = 0; y< boardSize; y++){
                if(y+1 < boardSize){
                    connectIfBothTilesHaveColour(x,y,x,y+1, c);
                }
                if(y+1 < boardSize && x-1 >= 0){
                    connectIfBothTilesHaveColour(x,y,x-1,y+1,c);
                }
                if(x+1 < boardSize){
                    connectIfBothTilesHaveColour(x,y,x+1,y, c);
                }
            }
        }
    }

    public boolean hasWonHorizontally(){
        clearAdjacencyList();
        connectStartAndEndNodesHorizontal();
        connectNeighboursIfTheyHaveGivenColour(horizontalColour);
        return IsConnected(noOfNodes - 2,noOfNodes-1);
    }

    public boolean hasWonVertically(){
        clearAdjacencyList();
        connectStartAndEndNodesVertical();
        connectNeighboursIfTheyHaveGivenColour(verticalColour);
        return IsConnected(noOfNodes - 2,noOfNodes-1);
    }
    



    //BFS a la CSES
    public boolean IsConnected(int startNode, int endNode){
        ArrayDeque<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[noOfNodes];

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
}
