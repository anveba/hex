package test.hex;

import main.hex.*;

import static org.junit.Assert.*;

import org.junit.*;

public class GameLogicTest {
    private Player player1, player2;
    private GameLogic gameLogic;

    @Before
    public void setup() {
        Board board = new Board(11);
        player1 = new Player(Tile.Colour.BLUE);
        player2 = new Player(Tile.Colour.RED);
        gameLogic = new GameLogic(board, player1, player2);
    }

    @Test
    public void createPlayers_firstPlayerTurn_player1() {
        assertEquals(player1, gameLogic.getPlayerTurn());
    }

    @Test
    public void nextPlayer_currentlyPlayer1_player2() {
        gameLogic.nextPlayer();
        assertEquals(player2, gameLogic.getPlayerTurn());
    }

    @Test
    public void nextPlayer_runTwiceCurrentlyPlayer1_player1() { // Checks that players are looped
        gameLogic.nextPlayer();
        gameLogic.nextPlayer();
        assertEquals(player1, gameLogic.getPlayerTurn());
    }
}