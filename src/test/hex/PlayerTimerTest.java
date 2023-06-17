package test.hex;

import main.engine.TimeRecord;
import main.hex.board.Board;
import main.hex.board.TileColour;
import main.hex.logic.GameLogic;
import main.hex.player.PlayerTimer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PlayerTimerTest {

    private test.hex.TestPlayerClass player1, player2;
    private PlayerTimer playerTimer;
    private Board board;
    private GameLogic gameLogic;
    private double initialDuration = 10.0;
    @Before
    public void setup() {
        player1 = new test.hex.TestPlayerClass(TileColour.PLAYER1, initialDuration);
        player2 = new test.hex.TestPlayerClass(TileColour.PLAYER2, initialDuration);

        playerTimer = new PlayerTimer(initialDuration);

        board = new Board(11);

        gameLogic = new GameLogic(board, player1, player2, false);
        gameLogic.start();
    }

    @Test
    public void constructorWorksCorrectly() {
        assertEquals(initialDuration, playerTimer.getRemainingTime(), 0.00);
        assertTrue(playerTimer.getIsPaused());
    }

    @Test
    public void timerStartsCorrectly() {
        assertTrue(playerTimer.getIsPaused());
        playerTimer.startTimer();
        assertFalse(playerTimer.getIsPaused());
    }

    @Test
    public void timerStartedTwiceHasNoDifferentEffect() {
        assertTrue(playerTimer.getIsPaused());
        playerTimer.startTimer();
        playerTimer.startTimer();
        assertFalse(playerTimer.getIsPaused());
    }

    @Test
    public void timerPausesCorrectly() {
        playerTimer.startTimer();
        assertFalse(playerTimer.getIsPaused());
        playerTimer.pauseTimer();
        assertTrue(playerTimer.getIsPaused());
    }

    @Test
    public void timerPausedTwiceHasNoDifferentEffect() {
        playerTimer.startTimer();
        assertFalse(playerTimer.getIsPaused());
        playerTimer.pauseTimer();
        playerTimer.pauseTimer();
        assertTrue(playerTimer.getIsPaused());
    }

    @Test
    public void differentTimersForDifferentPlayers() {
        assertNotEquals(player1.getTimer(), player2.getTimer());
    }

    @Test
    public void timerTextFormattedCorrectly() {
        assertEquals("00:10", player1.getTimer().getFormattedTime());
    }

    @Test
    public void callBackRunWhenPlayer1RunsOutOfTime() {
        Runnable callback = mock(Runnable.class);
        player1.getTimer().setCallback(callback);

        player1.getTimer().startTimer();
        player1.getTimer().update(new TimeRecord(15.0f, 20.0f));

        verify(callback, times(1)).run();
    }

    @Test
    public void callBackRunWhenPlayer2RunsOutOfTime() {
        Runnable callback = mock(Runnable.class);
        player2.getTimer().setCallback(callback);

        player2.getTimer().startTimer();
        player2.getTimer().update(new TimeRecord(15.0f, 20.0f));

        verify(callback, times(1)).run();
    }

    @Test
    public void addTimeMethodAddsTimeToTimer() {
        assertEquals(initialDuration, player1.getTimer().getRemainingTime(), 0.0);

        player1.getTimer().addTime(3);

        assertEquals(initialDuration + 3, player1.getTimer().getRemainingTime(), 0.0);
    }
}
