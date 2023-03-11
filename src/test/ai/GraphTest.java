package test.ai;

import main.ai.Graph;
import main.hex.Tile;
import org.junit.Test;

public class GraphTest {

    @Test
    public void createGraph(){
        int k = 5;
        Tile[][] board = new Tile[k][k];
        for(int i = 0; i<k;i++){
            for(int j = 0; j<k;j++){
                board[i][j] = new Tile(Tile.Colour.WHITE);
            }
        }
        Graph g = new Graph(board,Tile.Colour.BLUE,Tile.Colour.RED);


    }


    @Test
    public void graphHeuristics(){
        int k = 5;
        Tile[][] board = new Tile[k][k];
        for(int i = 0; i<k;i++){
            for(int j = 0; j<k;j++){
                board[i][j] = new Tile(Tile.Colour.WHITE);
            }
        }

        Graph g = new Graph(board,Tile.Colour.BLUE,Tile.Colour.RED);
        float e1 = g.boardEvaluation();


        Tile[][] board2 = new Tile[k][k];
        for(int i = 0; i<k;i++){
            for(int j = 0; j<k;j++){
                board2[i][j] = new Tile(Tile.Colour.WHITE);
                board2[i][j].setColour(Tile.Colour.BLUE);
            }
        }

        Graph g2 = new Graph(board2,Tile.Colour.BLUE,Tile.Colour.RED);
        float e2 = g2.boardEvaluation();

        Tile[][] board3 = new Tile[k][k];
        for(int i = 0; i<k;i++){
            for(int j = 0; j<k;j++){
                board3[i][j] = new Tile(Tile.Colour.WHITE);
                board3[i][j].setColour(Tile.Colour.RED);
            }
        }

        Graph g3 = new Graph(board3,Tile.Colour.BLUE,Tile.Colour.RED);
        float e3 = g3.boardEvaluation();

        System.out.println(e1);
        System.out.println(e2);
        System.out.println(e3);


        assert(e1 == 0);
        assert(e2 > 0);
        assert(e3 < 0);

    }

    @Test
    public void gameWonVertically(){
        int k = 5;
        Tile[][] board = new Tile[k][k];
        for(int i = 0; i<k;i++){

            for(int j = 0; j<k;j++){
                board[i][j] = new Tile(Tile.Colour.WHITE);
            }

        }
        for(int i = 0; i<k; i++){
            board[3][i].setColour(Tile.Colour.BLUE);
        }


        Graph g = new Graph(board,Tile.Colour.BLUE,Tile.Colour.RED);
        assert(g.checkWinVertical());

    }

    @Test
    public void gameWonHorizontally(){
        int k = 5;
        Tile[][] board = new Tile[k][k];
        for(int i = 0; i<k;i++){

            for(int j = 0; j<k;j++){
                board[i][j] = new Tile(Tile.Colour.WHITE);
            }

        }
        for(int i = 0; i<k; i++){
            board[i][3].setColour(Tile.Colour.RED);
        }

        Graph g = new Graph(board, Tile.Colour.BLUE,Tile.Colour.RED);
        assert(g.checkWinHorizontal());

    }
}