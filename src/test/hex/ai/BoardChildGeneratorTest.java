package test.hex.ai;

import main.hex.AIPlayer;
import main.hex.Player;
import main.hex.ai.AI;
import main.hex.ai.AIMove;
import main.hex.ai.graph.BoardChildGenerator;
import main.hex.ai.graph.BoardEvaluator;
import main.hex.ai.graph.connectionFunctions.SignalBasedTileConnector;
import main.hex.ai.graph.heuristicFunctions.SignalGraphHeuristic;
import main.hex.board.Board;
import main.hex.board.Tile;
import main.hex.board.TileColour;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BoardChildGeneratorTest {


    @Test
    //Subtest for one of the AI tests, to check whether a wanted child was even created
    public void ChildGeneratorFindsCorrectChildren(){
        Board board = new Board(3);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 0,0);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 1,1);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 0,1);
        board.setTileAtPosition(new Tile(TileColour.PLAYER1), 1,0);

        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 2,2);
        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 2,1);
        board.setTileAtPosition(new Tile(TileColour.PLAYER2), 1,2);

        BoardChildGenerator b = new BoardChildGenerator();

        assert(b.createChildren(board).stream().anyMatch(m -> m.getX() == 2 && m.getY() == 0));
        //System.out.println(agent.winsByVerticalConnection());


    }
}
