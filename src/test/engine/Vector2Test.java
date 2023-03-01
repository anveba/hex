package test.engine;

import static main.engine.Utility.*;
import static org.junit.Assert.*;

import org.junit.*;

import main.engine.Vector2;

public class Vector2Test {

    @Test
    public void contructor_Empty_XYEqualsZero() {
        Vector2 v = new Vector2();
        assertTrue(floatEquals(v.getX(), 0.0f));
        assertTrue(floatEquals(v.getY(), 0.0f));
    }

    @Test
    public void contructor_ValidCoords_XYReturned() {
        float x = 55.0f, y = 6842.12f;
        Vector2 v = new Vector2(x, y);
        assertTrue(floatEquals(v.getX(), x));
        assertTrue(floatEquals(v.getY(), y));
    }

    @Test
    public void getSetX_ValidNumber_XReturned() {
        Vector2 v = new Vector2();
        float x = 32.87f;
        v.setX(x);
        assertTrue(floatEquals(v.getX(), x));
    }
    
    @Test
    public void getSetY_ValidNumber_YReturned() {
        Vector2 v = new Vector2();
        float y = 32.87f;
        v.setY(y);
        assertTrue(floatEquals(v.getY(), y));
    }
}
