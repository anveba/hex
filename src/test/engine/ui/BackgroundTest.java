package test.engine.ui;

import main.engine.TimeRecord;
import main.engine.graphics.Colour;
import main.engine.graphics.Texture;
import main.engine.ui.Background;
import org.junit.Before;
import org.junit.Test;

import static main.engine.Utility.floatEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BackgroundTest {

    private final float x = 0.0f;
    private final float y = 0.0f;
    private final float xSpeed = 0.05f;
    private final float ySpeed = 0.025f;
    private Texture texture;
    private Colour colour;

    @Before
    public void setup() {
        texture= mock(Texture.class);
        colour = mock(Colour.class);
    }

    @Test
    public void constructorSetsValuesCorrectly() {
        Background background = new Background(x, y, xSpeed, ySpeed, texture, colour);
        assertTrue(floatEquals(x, background.getX()));
        assertTrue(floatEquals(y, background.getY()));
        assertTrue(floatEquals(xSpeed, background.getXSpeed()));
        assertTrue(floatEquals(ySpeed, background.getYSpeed()));
        assertTrue(floatEquals(texture.width()/800.0f, background.getBackgroundTileWidth()));
        assertTrue(floatEquals(texture.height()/800.0f, background.getBackgroundTileHeight()));
        assertEquals(texture, background.getTexture());
        assertEquals(colour, background.getColour());
    }

    @Test
    public void setsPositionCorrectly() {
        Background background = new Background(999.0f, 999.0f, xSpeed, ySpeed, texture, colour);

        background.setPosition(0.1f, 0.2f);

        assertTrue(floatEquals(0.1f, background.getX()));
        assertTrue(floatEquals(0.2f, background.getY()));
    }

    @Test
    public void correctNumberOfTilesWhenMovingRight() {
        when(texture.width()).thenReturn(1878);
        when(texture.height()).thenReturn(2168);

        Background background = new Background(x, y, xSpeed, 0.0f, texture, colour);
        background.setAspectRatio(1.0f);

        assertEquals(1, background.getNoOfTiles());

        TimeRecord timeRecord = mock(TimeRecord.class);
        when(timeRecord.elapsedSeconds()).thenReturn(8.0f);
        background.updateElement(timeRecord);

        assertEquals(2, background.getNoOfTiles());

        when(timeRecord.elapsedSeconds()).thenReturn(72.0f);
        background.updateElement(timeRecord);

        assertEquals(2, background.getNoOfTiles());
    }

    @Test
    public void correctNumberOfTilesWhenMovingLeft() {
        when(texture.width()).thenReturn(1878);
        when(texture.height()).thenReturn(2168);

        Background background = new Background(x, y, -xSpeed, 0.0f, texture, colour);
        background.setAspectRatio(1.0f);

        assertEquals(1, background.getNoOfTiles());

        TimeRecord timeRecord = mock(TimeRecord.class);
        when(timeRecord.elapsedSeconds()).thenReturn(8.0f);
        background.updateElement(timeRecord);

        assertEquals(2, background.getNoOfTiles());

        when(timeRecord.elapsedSeconds()).thenReturn(72.0f);
        background.updateElement(timeRecord);

        assertEquals(2, background.getNoOfTiles());
    }

    @Test
    public void correctNumberOfTilesWhenMovingUp() {
        when(texture.width()).thenReturn(1878);
        when(texture.height()).thenReturn(2168);

        Background background = new Background(x, y, 0.0f, ySpeed, texture, colour);
        background.setAspectRatio(1.0f);

        assertEquals(1, background.getNoOfTiles());

        TimeRecord timeRecord = mock(TimeRecord.class);
        when(timeRecord.elapsedSeconds()).thenReturn(16.0f);
        background.updateElement(timeRecord);

        assertEquals(2, background.getNoOfTiles());

        when(timeRecord.elapsedSeconds()).thenReturn(144.0f);
        background.updateElement(timeRecord);

        assertEquals(2, background.getNoOfTiles());
    }

    @Test
    public void correctNumberOfTilesWhenMovingDown() {
        when(texture.width()).thenReturn(1878);
        when(texture.height()).thenReturn(2168);

        Background background = new Background(x, y, 0.0f, -ySpeed, texture, colour);
        background.setAspectRatio(1.0f);

        assertEquals(1, background.getNoOfTiles());

        TimeRecord timeRecord = mock(TimeRecord.class);
        when(timeRecord.elapsedSeconds()).thenReturn(16.0f);
        background.updateElement(timeRecord);

        assertEquals(2, background.getNoOfTiles());

        when(timeRecord.elapsedSeconds()).thenReturn(144.0f);
        background.updateElement(timeRecord);

        assertEquals(2, background.getNoOfTiles());
    }
}