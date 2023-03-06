package test.hex;

import main.engine.*;
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
    public void tileToScreen_screenToTileCorrectedCoordinates_Equals() {
    	Vector2 original = new Vector2(0.34f, 0.64f);
    	Point2 tileSpacePosition = board.screenToTile(
    			original.getX(), original.getY());
    	Vector2 screenSpacePosition = board.tileToScreen(
    			tileSpacePosition.getX(), tileSpacePosition.getY());
    	assertTrue(Math.abs(original.getX() - screenSpacePosition.getX())
    			< board.getTileSize() / 2.0f + 0.001f
    			&& Math.abs(original.getY() - screenSpacePosition.getY())
    			< board.getTileSize() / 2.0f + 0.001f);
    }
    
    @Test
    public void screenToTile_tileToScreenCorrectedCoordinates_Equals() {
    	Point2 original = new Point2(2, 5);
    	Vector2 screenSpacePosition = board.tileToScreen(
    			original.getX(), original.getY());
    	Point2 tileSpacePosition = board.screenToTile(
    			screenSpacePosition.getX(), screenSpacePosition.getY());
    	assertEquals(original, tileSpacePosition);
    }

    @Test
    public void getTileAtPosition_tileColourSet_success() {
        board.getTileAtPosition(1, 1).setColour(Tile.Colour.RED);
        assertEquals(board.getTileAtPosition(1, 1).getColour(), Tile.Colour.RED);
    }
}
