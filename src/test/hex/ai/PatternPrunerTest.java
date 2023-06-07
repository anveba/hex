package test.hex.ai;

import main.hex.ai.AIMove;
import main.hex.ai.BoardChildGenerator;
import main.hex.ai.PatternPruner;
import main.hex.ai.graph.Graph;
import main.hex.board.Board;
import main.hex.board.Tile;
import main.hex.board.TileColour;
import main.hex.player.Player;
import org.junit.Test;
import test.hex.TestPlayerClass;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class PatternPrunerTest {

    @Test
    public void borderBridgeDominatedCellsGetRemovedFromEmptyBoardWithHorizontalPlayer(){
        Board b = new Board(5);

        BoardChildGenerator bcg = new BoardChildGenerator();
        Player agent = new TestPlayerClass(TileColour.PLAYER1);



        ArrayList<AIMove> moves = bcg.createChildren(b);

        moves = PatternPruner.pruneByPatterns(moves,b,agent.getColour());
        for (AIMove m: moves
             ) { assertTrue("Failed with values: x: "+m.getX() + " y: "+m.getY(),m.getX() != 0 && m.getX() != b.size()-1);

        }

    }

    @Test
    public void borderBridgeDominatedCellsGetRemovedFromEmptyBoardWithVerticalPlayer(){
        Board b = new Board(5);
        PatternPruner p = new PatternPruner();
        BoardChildGenerator bcg = new BoardChildGenerator();
        Player agent = new TestPlayerClass(TileColour.PLAYER2);


        ArrayList<AIMove> moves = bcg.createChildren(b);
        moves = p.pruneByPatterns(moves,b,agent.getColour());
        for (AIMove m: moves
        ) { assertTrue("Failed with values: x: "+m.getX() + " y: "+m.getY(),m.getY() != 0 && m.getY() != b.size()-1);

        }
        assertTrue(moves.size() > 0);
    }

    @Test
    public void deadCellByCageGetsRemoved(){
        Board b = new Board(8);

        BoardChildGenerator bcg = new BoardChildGenerator();
        Player agent = new TestPlayerClass(TileColour.PLAYER2);

        b.setTileAtPosition(new Tile(TileColour.PLAYER2),5,5);
        b.setTileAtPosition(new Tile(TileColour.PLAYER2),6,5);
        b.setTileAtPosition(new Tile(TileColour.PLAYER2),6,6);
        b.setTileAtPosition(new Tile(TileColour.PLAYER2),5,7);

        ArrayList<AIMove> moves = bcg.createChildren(b);
        moves = PatternPruner.pruneByPatterns(moves,b,agent.getColour());
        for (AIMove m: moves
        ) { assertTrue("Failed with values: x: "+m.getX() + " y: "+m.getY(),m.getX() != 5 || m.getY() !=6 );

        }
        assertTrue(moves.size() > 0);
    }

    @Test
    public void deadCellByCageOpposingPairsGetRemoved(){
        Board b = new Board(8);

        BoardChildGenerator bcg = new BoardChildGenerator();
        Player agent = new TestPlayerClass(TileColour.PLAYER2);

        b.setTileAtPosition(new Tile(TileColour.PLAYER2),5,5);
        b.setTileAtPosition(new Tile(TileColour.PLAYER2),6,5);
        b.setTileAtPosition(new Tile(TileColour.PLAYER1),4,7);
        b.setTileAtPosition(new Tile(TileColour.PLAYER1),5,7);

        ArrayList<AIMove> moves = bcg.createChildren(b);
        moves = PatternPruner.pruneByPatterns(moves,b,agent.getColour());
        for (AIMove m: moves
        ) { assertTrue("Failed with values: x: "+m.getX() + " y: "+m.getY(),m.getX() != 5 || m.getY() !=6 );

        }
        assertTrue(moves.size() > 0);
    }
}
