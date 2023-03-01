package test.hex;

import main.hex.*;

import static org.junit.Assert.*;
import org.junit.*;

public class BoardTest {

    private Board board;

    @Before
    public void setup() {
        board = new Board(11);
    }

    @Test
    public void constructor_positive_success() {
        assertEquals(11, board.getBoardSize());
    }

    @Test
    public void constructor_negative_error() {
        try {
            Board b = new Board(-10);
            fail();
        } catch (HexException e) {

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void constructor_zero_error() {
        try {
            Board b = new Board(0);
            fail();
        } catch (HexException e) {

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getTileAtPosition_inBounds_success() {
        assertNotEquals(null, board.getTileAtPosition(1, 1));
    }

    @Test
    public void getTileAtPosition_outOfBoundsX_failure() {
        try {
            board.getTileAtPosition(10000, 1);
        } catch (HexException e) {

        } catch (Exception e) {
            fail();
        }
    }
    
    @Test
    public void getTileAtPosition_xNegative_failure() {
        try {
            board.getTileAtPosition(-3, 1);
        } catch (HexException e) {

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getTileAtPosition_outOfBoundsY_failure() {
        try {
            board.getTileAtPosition(1, 10000);
        } catch (HexException e) {

        } catch (Exception e) {
            fail();
        }
    }
    
    @Test
    public void getTileAtPosition_yNegative_failure() {
        try {
            board.getTileAtPosition(1, -6);
        } catch (HexException e) {

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getTileAtPosition_tileColourSet_success() {
        board.getTileAtPosition(1, 1).setColour(Tile.Colour.RED);
        assertEquals(board.getTileAtPosition(1, 1).getColour(), Tile.Colour.RED);
    }
}
