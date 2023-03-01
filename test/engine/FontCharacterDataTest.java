package test.engine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import engine.font.FontCharacterData;
import static engine.Utility.floatEquals;

class FontCharacterDataTest {
    
    @Test
    void constructor_ValidValues_ValuesSet() {
        int x0 = 2, x1 = 12, y0 = 20, y1 = 40;
        float advance = 18.1f, xOffset = 3.2f, yOffset = 2.0f;
        FontCharacterData d = new FontCharacterData(x0, x1, y0, y1, advance, xOffset, yOffset);
        assertEquals(x0, d.x0());
        assertEquals(x1, d.x1());
        assertEquals(y0, d.y0());
        assertEquals(y1, d.y1());
        assertTrue(floatEquals(advance, d.advance()));
        assertTrue(floatEquals(xOffset, d.xOffset()));
        assertTrue(floatEquals(yOffset, d.yOffset()));
    }
}
