package test.hex.ai;

import main.hex.Board;
import main.hex.Tile;
import main.hex.ai.BoardEvaluator;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardEvaluatorTest {


    @Test
    public void boardEvaluatorConstructorWorks(){

        Board board = new Board(5);
        BoardEvaluator b = new BoardEvaluator(board,Tile.Colour.BLUE,Tile.Colour.RED);
    }


    @Test
    public void agentColouredTilesAreConnectedWithMaxFadeAfterColourConnection(){
        Board board = new Board(5);
        board.getTileAtPosition(1,1).setColour(Tile.Colour.RED);
        board.getTileAtPosition(1,2).setColour(Tile.Colour.RED);
        BoardEvaluator  b = new BoardEvaluator(board,Tile.Colour.BLUE,Tile.Colour.RED);
        b.connectByColour(1,1,1,2, Tile.Colour.RED);
        assertEquals(1.0, b.fadeOfAdjacencyXY(1, 1, 1, 2).get(), 0.0);
    }


    /*
    Ranking of fade should be:
    AGENT COLOUR-AGENT COLOUR -> 1
    AGENT COLOUR-WHITE
    WHITE-WHITE
    Any that contains non-agentcolour -> No connection
    */

    @Test
    public void colourPairsGetConnectedSuchThatTheirFadeTheyRankCorrectly(){
        int k = 7;
        Board board = new Board(k);

        board.getTileAtPosition(2,0).setColour(Tile.Colour.RED);
        board.getTileAtPosition(3,0).setColour(Tile.Colour.RED);
        board.getTileAtPosition(4,0).setColour(Tile.Colour.BLUE);
        board.getTileAtPosition(5,0).setColour(Tile.Colour.BLUE);

        BoardEvaluator  b = new BoardEvaluator(board,Tile.Colour.BLUE,Tile.Colour.RED);

        for(int i = 0; i<k-1;i++){
            b.connectByColour(i,0,i+1,0, Tile.Colour.RED);
        }

        double c1 = b.fadeOfAdjacencyXY(0, 0, 1, 0).get();
        double c2 = b.fadeOfAdjacencyXY(1, 0, 2, 0).get();
        double c3 = b.fadeOfAdjacencyXY(2, 0, 3, 0).get();


        assertTrue(c1 < c2);
        assertTrue(c2 < c3);

        assertTrue( b.fadeOfAdjacencyXY(3, 0, 4, 0).isEmpty());
        assertTrue( b.fadeOfAdjacencyXY(4, 0, 5, 0).isEmpty());
        assertTrue( b.fadeOfAdjacencyXY(5, 0, 6, 0).isEmpty());


    }


    @Test
    public void AgentColouredNeighboursHaveMaxFadeConnectionAfterHorizontalEvaluationConnection(){
        int k = 2;
        Board board = new Board(k);
        for(int i = 0; i<k;i++){
            for(int j = 0; j<k;j++){
                board.getTileAtPosition(i,j).setColour(Tile.Colour.RED);
            }
        }
        BoardEvaluator  b = new BoardEvaluator(board,Tile.Colour.BLUE,Tile.Colour.RED);
        b.connectHorizontalEvaluation();
        assertTrue(b.fadeOfAdjacencyXY(0,0,0,1).isPresent());
        assertEquals(1.0, b.fadeOfAdjacencyXY(0, 0, 0, 1).get(), 0.0);
    }

    @Test
    public void AgentColouredNeighboursHaveMaxFadeConnectionAfterVerticalEvaluationConnection(){
        int k = 2;
        Board board = new Board(k);
        for(int i = 0; i<k;i++){
            for(int j = 0; j<k;j++){
                board.getTileAtPosition(i,j).setColour(Tile.Colour.BLUE);
            }
        }
        BoardEvaluator  b = new BoardEvaluator(board,Tile.Colour.BLUE,Tile.Colour.RED);
        b.connectVerticalEvaluation();
        assertTrue(b.fadeOfAdjacencyXY(0,0,0,1).isPresent());
        assertEquals(1.0, b.fadeOfAdjacencyXY(0, 0, 0, 1).get(), 0.0);
    }



    @Test
    public void differentBoardStatesGetEvaluatedCorrectlyRelativeToEachOther(){
        int k = 5;
        Board board = new Board(k);

        BoardEvaluator b = new BoardEvaluator(board,Tile.Colour.BLUE,Tile.Colour.RED);
        double e1 = b.evaluateBoard();


        Board board2 = new Board(k);
        for(int i = 0; i<k;i++){
            for(int j = 0; j<k;j++){
                board2.getTileAtPosition(i,j).setColour(Tile.Colour.BLUE);
            }
        }

        BoardEvaluator b2 = new BoardEvaluator(board2,Tile.Colour.BLUE,Tile.Colour.RED);
        double e2 = b2.evaluateBoard();

        Board board3 = new Board(k);
        for(int i = 0; i<k;i++){
            for(int j = 0; j<k;j++){
                board3.getTileAtPosition(i,j).setColour(Tile.Colour.RED);
            }
        }

        BoardEvaluator b3 = new BoardEvaluator(board3,Tile.Colour.BLUE,Tile.Colour.RED);
        double e3 = b3.evaluateBoard();


        assertTrue(e1 == 0.0);
        assertTrue(e2 > 0);
        assertTrue(e3 < 0);

    }
    @Test
    public void gameWonVertically_whenConnectedVertically(){
        int k = 5;
        Board board = new Board(k);
        for(int i = 0; i<k; i++){
            board.getTileAtPosition(3,i).setColour(Tile.Colour.BLUE);
        }

        BoardEvaluator g = new BoardEvaluator(board,Tile.Colour.BLUE,Tile.Colour.RED);
        assertTrue(g.hasWonVertically());

    }

    @Test
    public void gameNotWonVertically_whenNotConnectedVertically(){
        int k = 5;
        Board board = new Board(k);


        BoardEvaluator b = new BoardEvaluator(board,Tile.Colour.BLUE,Tile.Colour.RED);
        assertFalse(b.hasWonVertically());

    }

    @Test
    public void gameWonHorizontally_whenConnectedHorizontally(){
        int k = 5;
        Board board = new Board(k);
        for(int i = 0; i<k; i++){
            board.getTileAtPosition(i,3).setColour(Tile.Colour.RED);
        }

        BoardEvaluator b = new BoardEvaluator(board, Tile.Colour.BLUE,Tile.Colour.RED);
        assertTrue(b.hasWonHorizontally());

    }

    @Test
    public void gameNotWonHorizontally_whenNotConnectedHorizontally(){
        int k = 5;
        Board board = new Board(k);

        BoardEvaluator b = new BoardEvaluator(board, Tile.Colour.BLUE,Tile.Colour.RED);
        assertFalse(b.hasWonHorizontally());

    }


}
