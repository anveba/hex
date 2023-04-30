package test.hex;

import main.engine.*;
import main.engine.math.Vector2;
import main.hex.*;
import main.hex.board.Board;
import main.hex.board.BoardRenderer2D;
import main.hex.board.Tile;
import main.hex.board.TileColour;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.*;

public class BoardTest {

	private Board board;

    @Before
    public void setup() {
        board = new Board(11);
    }

    @Test
    public void constructor_positive_success() {
        assertEquals(11, board.size());
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
    public void isOutOfBounds_inBounds_false() {
        try {
            assertFalse(board.isOutOfBounds(1,1));
        } catch (HexException e) {

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void isOutOfBounds_outOfBoundsXPositive_true() {
        try {
            assertTrue(board.isOutOfBounds(10000, 1));
        } catch (HexException e) {

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void isOutOfBounds_outOfBoundsXNegative_true() {
        try {
            assertTrue(board.isOutOfBounds(-3, 1));
        } catch (HexException e) {

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void isOutOfBounds_outOfBoundsYPositive_true() {
        try {
            assertTrue(board.isOutOfBounds(1, 10000));
        } catch (HexException e) {

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void isOutOfBounds_outOfBoundsYNegative_true() {
        try {
            assertTrue(board.isOutOfBounds(1, -6));
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
    public void tileToScreen_screenToTileCorrectedCoordinates_Equals() {
    	Vector2 original = new Vector2(0.34f, 0.64f);
    	Point2 tileSpacePosition = board.screenToTile2D(
    			original.getX(), original.getY());
    	Vector2 screenSpacePosition = board.tileToScreen2D(
    			tileSpacePosition.getX(), tileSpacePosition.getY());
    	assertTrue(Math.abs(original.getX() - screenSpacePosition.getX())
    			< BoardRenderer2D.tileSize / 2.0f + 0.001f
    			&& Math.abs(original.getY() - screenSpacePosition.getY())
    			< BoardRenderer2D.tileSize / 2.0f + 0.001f);
    }
    
    @Test
    public void screenToTile_tileToScreenCorrectedCoordinates_Equals() {
    	Point2 original = new Point2(2, 5);
    	Vector2 screenSpacePosition = board.tileToScreen2D(
    			original.getX(), original.getY());
    	Point2 tileSpacePosition = board.screenToTile2D(
    			screenSpacePosition.getX(), screenSpacePosition.getY());
    	assertEquals(original, tileSpacePosition);
    }
     



    @Test
    public void setTileAtPosition_tileColourSet_colourSet() {
    	int x = 1, y = 1;
        assertEquals(TileColour.WHITE, board.getTileAtPosition(x, y).getColour());
        board.setTileAtPosition(new Tile(TileColour.PLAYER2), x, y);
        assertEquals(TileColour.PLAYER2, board.getTileAtPosition(x, y).getColour());
    }
    
    @Test(expected = HexException.class)
    public void nullTileThrowsExceptionWhenSettingTile() {
    	int x = 1, y = 1;
        board.setTileAtPosition(null, x, y);
    }
    
    @Test(expected = HexException.class)
    public void outOfBoundsPositionThrowsExceptionWhenSettingTile() {
    	int x = -1, y = -1;
        board.setTileAtPosition(mock(Tile.class), x, y);
    }
}
