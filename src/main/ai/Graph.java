package main.ai;

import main.hex.Tile;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Graph {

    private ArrayList<Edge>[] adjacency_list;
    private int no_of_nodes;
    private int board_size;

    private Tile[][] board;

    private final Tile.Colour vertical_colour;

    private final Tile.Colour horizontal_colour;


    public Graph(Tile[][] board,Tile.Colour vertical_colour, Tile.Colour horizontal_colour){
        no_of_nodes = board.length * board.length +2;
        board_size = board.length;
        this.board = board;


        this.vertical_colour = vertical_colour;
        this.horizontal_colour = horizontal_colour;

        adjacency_list = new ArrayList[no_of_nodes];


    }



    public void connect(int a,int b,float fade){
        adjacency_list[a].add(new Edge(a,b,fade));
        adjacency_list[b].add(new Edge(b,a,fade));
    }

    public int xy_to_num(int x, int y){
        return y*board_size + x;
    }

    public void connect_xy(int x1, int y1, int x2, int y2,float fade){
        connect(xy_to_num(x1,y1),xy_to_num(x2,y2),fade);
    }


    //Tiles are connected with different fade values, based on their colour
    //If one tile is opponents colour, no connection is made
    //If both tiles are own colour, a connection with 1 fade (no loss) is made
    //If one tile is non-coloured, we get 0.9 fade
    //If both tiles are non-coloured, we get 0.8 fade
    public void connect_with_colour_resistance(int x1, int y1, int x2, int y2,Tile.Colour non_agent_colour){
        Tile.Colour t1Colour = board[x1][y1].getColour();
        Tile.Colour t2Colour = board[x2][y2].getColour();

        if(t1Colour.equals(non_agent_colour) || t2Colour.equals(non_agent_colour)){
            return;
        }
        float fade = 1;
        if(t1Colour.equals(Tile.Colour.WHITE)){
            fade -= 0.1;
        }
        if(t2Colour.equals(Tile.Colour.WHITE)){
            fade -= 0.1;
        }
        connect_xy(x1,y1,x2,y2,fade);


    }

    public void connect_vertical_evaluation(){
        for(int i = 0; i < adjacency_list.length; i++){
            adjacency_list[i] = new ArrayList<Edge>();
        }

        for(int x = 0; x < board_size; x++){
            connect_xy(x,0,board_size,board_size-1,1);
            connect_xy(x,board_size-1,board_size+1,board_size-1,1);
        }

        for(int x = 0; x<board_size; x++){
            for(int y = 0; y<board_size; y++){
                if(y+1 < board_size){
                    connect_with_colour_resistance(x,y,x,y+1,vertical_colour);
                }
                if(y+1 < board_size && x-1 >= 0){
                    connect_with_colour_resistance(x,y,x-1,y+1,vertical_colour);
                }
                if(x+1 < board_size ){
                    connect_with_colour_resistance(x,y,x+1,y,vertical_colour);
                }
            }
        }
    }

    public void connect_horizontal_evaluation(){
        for(int i = 0; i < adjacency_list.length; i++){
            adjacency_list[i] = new ArrayList<Edge>();
        }

        //Current version assumes you want to move from top to bottom
        for(int y = 0; y < board_size; y++){
            connect_xy(0,y,board_size,board_size-1,1);
            connect_xy(board_size-1,y,board_size+1,board_size-1,1);
        }

        for(int x = 0; x<board_size; x++){
            for(int y = 0; y<board_size; y++){
                if(y+1 < board_size){
                    connect_with_colour_resistance(x,y,x,y+1,horizontal_colour);
                }
                if(y+1 < board_size && x-1 >= 0){
                    connect_with_colour_resistance(x,y,x-1,y+1,horizontal_colour);
                }
                if(x+1 < board_size ){
                    connect_with_colour_resistance(x,y,x+1,y,horizontal_colour);
                }
            }
        }
    }




    //Positive float: horizontal is favoured
    //Negative float: vertical is favoured
    public float board_evaluation(){
        connect_horizontal_evaluation();
        float horizontal_signal = board_evaluation_bfs();
        connect_vertical_evaluation();
        float vertical_signal = board_evaluation_bfs();

        return horizontal_signal-vertical_signal;
    }

    public float board_evaluation_bfs(){
        ArrayDeque<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[no_of_nodes];
        float[] signal = new float[no_of_nodes];

        visited[no_of_nodes-2] = true;

        for(int i = 0; i<no_of_nodes;i++){
            signal[i]=0;
        }

        signal[no_of_nodes-2] = 1;
        q.add(no_of_nodes-2);
        while(!q.isEmpty()){
            int s = q.poll();

            for(Edge e:adjacency_list[s]){
                int neighbour = e.to;
                signal[neighbour] += signal[s]*e.fade;

                if(visited[neighbour]){
                    continue;
                }
                q.add(neighbour);
                visited[neighbour] = true;
            }
        }
        return signal[no_of_nodes-1];

    }

    public void connect_if_colour(int x1,int y1,int x2,int y2,Tile.Colour colour){
        for(int i = 0; i < adjacency_list.length; i++){
            adjacency_list[i] = new ArrayList<Edge>();
        }
        Tile.Colour t1Colour = board[x1][y1].getColour();
        Tile.Colour t2Colour = board[x2][y2].getColour();
        if(t1Colour.equals(colour) && t2Colour.equals(colour)){
            connect_xy(x1,y1,x2,y2,1);
        }
    }

    public void connect_win_check_horizontal(){
        for(int i = 0; i < adjacency_list.length; i++){
            adjacency_list[i] = new ArrayList<Edge>();
        }
        for(int y = 0; y < board_size; y++){
            connect_xy(0,y,board_size,board_size-1,1);
            connect_xy(board_size-1,y,board_size+1,board_size-1,1);
        }

        for(int x = 0; x<board_size; x++){
            for(int y = 0; y<board_size; y++){
                if(y+1 < board_size){
                    connect_if_colour(x,y,x,y+1,horizontal_colour);
                }
                if(y+1 < board_size && x-1 >= 0){
                    connect_if_colour(x,y,x-1,y+1,horizontal_colour);
                }
                if(x+1 < board_size ){
                    connect_if_colour(x,y,x+1,y,horizontal_colour);
                }
            }
        }
    }

    private void connect_win_check_vertical() {
        for(int i = 0; i < adjacency_list.length; i++){
            adjacency_list[i] = new ArrayList<Edge>();
        }
        for(int x = 0; x < board_size; x++){
            connect_xy(x,0,board_size,board_size-1,1);
            connect_xy(x,board_size-1,board_size+1,board_size-1,1);
        }

        for(int x = 0; x<board_size; x++){
            for(int y = 0; y<board_size; y++){
                if(y+1 < board_size){
                    connect_if_colour(x,y,x,y+1,vertical_colour);
                }
                if(y+1 < board_size && x-1 >= 0){
                    connect_if_colour(x,y,x-1,y+1,vertical_colour);
                }
                if(x+1 < board_size ){
                    connect_if_colour(x,y,x+1,y,vertical_colour);
                }
            }
        }
    }

    public boolean check_win_horizontal(){
        connect_win_check_horizontal();
        return bfs(no_of_nodes-2);
    }

    public boolean check_win_vertical(){
        connect_win_check_vertical();
        return bfs(no_of_nodes-2);
    }




    public boolean bfs(int startNode){
        ArrayDeque<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[no_of_nodes];

        visited[startNode] = true;

        q.add(startNode);
        while(!q.isEmpty()){
            int s = q.poll();

            for(Edge e:adjacency_list[s]){
                int neighbour = e.to;


                if(visited[neighbour]){
                    continue;
                }
                q.add(neighbour);
                visited[neighbour] = true;
            }
        }
        return visited[no_of_nodes-1];
    }








}
