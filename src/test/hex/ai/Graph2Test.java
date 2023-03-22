package test.hex.ai;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Graph2Test {

    /*
    @Test
    public void graphConstructorWorks(){
        int k = 5;
        Tile[][] board = new Tile[k][k];
        for(int i = 0; i<k;i++){
            for(int j = 0; j<k;j++){
                board[i][j] = new Tile(Tile.Colour.WHITE);
            }
        }
        Graph2 g = new Graph2(board,Tile.Colour.BLUE,Tile.Colour.RED);
    }


    @Test
    public void differentBoardStatesGetEvaluatedCorrectly(){
        int k = 5;
        Tile[][] board = new Tile[k][k];
        for(int i = 0; i<k;i++){
            for(int j = 0; j<k;j++){
                board[i][j] = new Tile(Tile.Colour.WHITE);
            }
        }

        Graph2 g = new Graph2(board,Tile.Colour.BLUE,Tile.Colour.RED);
        double e1 = g.boardEvaluation();


        Tile[][] board2 = new Tile[k][k];
        for(int i = 0; i<k;i++){
            for(int j = 0; j<k;j++){
                board2[i][j] = new Tile(Tile.Colour.WHITE);
                board2[i][j].setColour(Tile.Colour.BLUE);
            }
        }

        Graph2 g2 = new Graph2(board2,Tile.Colour.BLUE,Tile.Colour.RED);
        double e2 = g2.boardEvaluation();

        Tile[][] board3 = new Tile[k][k];
        for(int i = 0; i<k;i++){
            for(int j = 0; j<k;j++){
                board3[i][j] = new Tile(Tile.Colour.WHITE);
                board3[i][j].setColour(Tile.Colour.RED);
            }
        }

        Graph2 g3 = new Graph2(board3,Tile.Colour.BLUE,Tile.Colour.RED);
        double e3 = g3.boardEvaluation();


        assertTrue(e1 == 0.0);
        assertTrue(e2 > 0);
        assertTrue(e3 < 0);

    }


    @Test
    public void gameWonVertically_whenConnectedVertically(){
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

        Graph2 g = new Graph2(board,Tile.Colour.BLUE,Tile.Colour.RED);
        assertTrue(g.hasWonVertically());

    }

    @Test
    public void gameNotWonVertically_whenNotConnectedVertically(){
        int k = 5;
        Tile[][] board = new Tile[k][k];
        for(int i = 0; i<k;i++){

            for(int j = 0; j<k;j++){
                board[i][j] = new Tile(Tile.Colour.WHITE);
            }

        }


        Graph2 g = new Graph2(board,Tile.Colour.BLUE,Tile.Colour.RED);
        assertFalse(g.hasWonVertically());

    }

    @Test
    public void gameWonHorizontally_whenConnectedHorizontally(){
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

        Graph2 g = new Graph2(board, Tile.Colour.BLUE,Tile.Colour.RED);
        assertTrue(g.hasWonHorizontally());

    }

    @Test
    public void gameNotWonHorizontally_whenNotConnectedHorizontally(){
        int k = 5;
        Tile[][] board = new Tile[k][k];
        for(int i = 0; i<k;i++){

            for(int j = 0; j<k;j++){
                board[i][j] = new Tile(Tile.Colour.WHITE);
            }

        }

        Graph2 g = new Graph2(board, Tile.Colour.BLUE,Tile.Colour.RED);
        assertFalse(g.hasWonHorizontally());

    }

    */

}
