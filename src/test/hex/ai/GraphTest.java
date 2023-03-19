package test.hex.ai;

import main.hex.Board;
import main.hex.Tile;
import main.hex.ai.Graph;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GraphTest {

    @Test
    public void createGraph(){
    	Board board = new Board(5);
        Graph g = new Graph(board,Tile.Colour.BLUE,Tile.Colour.RED);
    }


    @Test
    public void graphHeuristics(){
    	Board board = new Board(5);
        Graph g = new Graph(board,Tile.Colour.BLUE,Tile.Colour.RED);
        double e1 = g.boardEvaluation();

        board = new Board(5);
        for(int i = 0; i<board.size();i++)
            for(int j = 0; j<board.size();j++)
                board.getTileAtPosition(i, j).setColour(Tile.Colour.BLUE);
        Graph g2 = new Graph(board,Tile.Colour.BLUE,Tile.Colour.RED);
        double e2 = g2.boardEvaluation();

        board = new Board(5);
        for(int i = 0; i<board.size();i++)
            for(int j = 0; j<board.size();j++)
                board.getTileAtPosition(i, j).setColour(Tile.Colour.RED);

        Graph g3 = new Graph(board,Tile.Colour.BLUE,Tile.Colour.RED);
        double e3 = g3.boardEvaluation();

        System.out.println(e1);
        System.out.println(e2);
        System.out.println(e3);
        assertTrue(e1 == 0.0);
        assertTrue(e2 > 0);
        assertTrue(e3 < 0);

    }


    @Test
    public void gameWonVertically(){
        Board board = new Board(5);
        for(int i = 0; i<board.size(); i++)
            board.getTileAtPosition(3, i).setColour(Tile.Colour.BLUE);

        Graph g = new Graph(board,Tile.Colour.BLUE,Tile.Colour.RED);
        assertTrue(g.checkWinVertical());

    }

    @Test
    public void gameWonHorizontally(){
    	Board board = new Board(5);
        for(int i = 0; i<board.size(); i++)
            board.getTileAtPosition(i, 3).setColour(Tile.Colour.RED);

        Graph g = new Graph(board, Tile.Colour.BLUE,Tile.Colour.RED);
        assertTrue(g.checkWinHorizontal());
    }
}
