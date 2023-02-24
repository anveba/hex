package test.hex;

import hex.Board;
import hex.HexException;
import hex.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;

    @BeforeEach
    void setup() {
        board = new Board(11);
    }

    @Test
    void constructor_positive_success() {
        assertEquals(11, board.getBoardSize());
    }

    @Test
    void constructor_negative_error() {
        try {
            Board b = new Board(-10);
            fail();
        } catch (HexException e) {

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void constructor_zero_error() {
        try {
            Board b = new Board(0);
            fail();
        } catch (HexException e) {

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void getTileAtPosition_inBounds_success() {
        assertNotEquals(null, board.getTileAtPosition(1, 1));
    }

    @Test
    void getTileAtPosition_outOfBoundsX_failure() {
        try {
            board.getTileAtPosition(10000, 1);
        } catch (HexException e) {

        } catch (Exception e) {
            fail();
        }
    }
    
    @Test
    void getTileAtPosition_XNegative_failure() {
        try {
            board.getTileAtPosition(-3, 1);
        } catch (HexException e) {

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void getTileAtPosition_outOfBoundsY_failure() {
        try {
            board.getTileAtPosition(1, 10000);
        } catch (HexException e) {

        } catch (Exception e) {
            fail();
        }
    }
    
    @Test
    void getTileAtPosition_YNegative_failure() {
        try {
            board.getTileAtPosition(1, -6);
        } catch (HexException e) {

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void getTileAtPosition_tileColourSet_success() {
        board.getTileAtPosition(1, 1).setColour(Tile.Colour.BLACK);
        assertEquals(board.getTileAtPosition(1, 1).getColour(), Tile.Colour.BLACK);
    }
}
