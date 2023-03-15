package test.hex.ai;

import main.hex.Tile;
import main.hex.ai.Graph;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GraphTest {

    @Test
    public void constructor_works(){
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
    public void different_board_states_get_evaluated_correctly(){
        int k = 5;
        Tile[][] board = new Tile[k][k];
        for(int i = 0; i<k;i++){
            for(int j = 0; j<k;j++){
                board[i][j] = new Tile(Tile.Colour.WHITE);
            }
        }

        Graph g = new Graph(board,Tile.Colour.BLUE,Tile.Colour.RED);
        double e1 = g.boardEvaluation();


        Tile[][] board2 = new Tile[k][k];
        for(int i = 0; i<k;i++){
            for(int j = 0; j<k;j++){
                board2[i][j] = new Tile(Tile.Colour.WHITE);
                board2[i][j].setColour(Tile.Colour.BLUE);
            }
        }

        Graph g2 = new Graph(board2,Tile.Colour.BLUE,Tile.Colour.RED);
        double e2 = g2.boardEvaluation();

        Tile[][] board3 = new Tile[k][k];
        for(int i = 0; i<k;i++){
            for(int j = 0; j<k;j++){
                board3[i][j] = new Tile(Tile.Colour.WHITE);
                board3[i][j].setColour(Tile.Colour.RED);
            }
        }

        Graph g3 = new Graph(board3,Tile.Colour.BLUE,Tile.Colour.RED);
        double e3 = g3.boardEvaluation();


        assertTrue(e1 == 0.0);
        assertTrue(e2 > 0);
        assertTrue(e3 < 0);

    }


    @Test
    public void game_won_vertically_when_connected_vertically(){
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
        assertTrue(g.checkWinVertical());

    }

    @Test
    public void game_not_won_vertically_when_not_connected_vertically(){
        int k = 5;
        Tile[][] board = new Tile[k][k];
        for(int i = 0; i<k;i++){

            for(int j = 0; j<k;j++){
                board[i][j] = new Tile(Tile.Colour.WHITE);
            }

        }


        Graph g = new Graph(board,Tile.Colour.BLUE,Tile.Colour.RED);
        assertFalse(g.checkWinVertical());

    }

    @Test
    public void game_won_horizontally_when_connected_horizontally(){
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
        assertTrue(g.checkWinHorizontal());

    }

    @Test
    public void game_not_won_horizontally_when_not_connected_horizontally(){
        int k = 5;
        Tile[][] board = new Tile[k][k];
        for(int i = 0; i<k;i++){

            for(int j = 0; j<k;j++){
                board[i][j] = new Tile(Tile.Colour.WHITE);
            }

        }

        Graph g = new Graph(board, Tile.Colour.BLUE,Tile.Colour.RED);
        assertFalse(g.checkWinHorizontal());

    }



}
