package test.engine.math;

import static main.engine.Utility.*;
import static org.junit.Assert.*;

import org.junit.*;

import main.engine.math.Point2;
import main.engine.math.Vector2;

public class Vector2Test {

    @Test
    public void contructor_empty_xYEqualsZero() {
        Vector2 v = new Vector2();
        assertTrue(floatEquals(v.getX(), 0.0f));
        assertTrue(floatEquals(v.getY(), 0.0f));
    }

    @Test
    public void contructor_validCoords_xYReturned() {
        float x = 55.0f, y = 6842.12f;
        Vector2 v = new Vector2(x, y);
        assertTrue(floatEquals(v.getX(), x));
        assertTrue(floatEquals(v.getY(), y));
    }

    @Test
    public void getSetX_validNumber_xReturned() {
        Vector2 v = new Vector2();
        float x = 32.87f;
        v.setX(x);
        assertTrue(floatEquals(v.getX(), x));
    }
    
    @Test
    public void getSetY_validNumber_yReturned() {
        Vector2 v = new Vector2();
        float y = 32.87f;
        v.setY(y);
        assertTrue(floatEquals(v.getY(), y));
    }
    
    @Test
    public void toStringReturnsStringWithCoordinates() {
    	float x = 3.30f, y = 8.54f;
    	Vector2 v = new Vector2(x, y);
    	assertEquals("(" + x + ", " + y + ")", v.toString());
    }

    @Test
    public void equals_equalPoint_true() {
    	float x = -5.65f, y = 9.43f;
    	Vector2 v1 = new Vector2(x, y);
    	Vector2 v2 = new Vector2(x, y);
    	assertTrue(v1.equals(v2));
    }
    
    @Test
    public void equals_nonequalPoint_false() {
    	int x = -3, y = 7;
    	Vector2 v1 = new Vector2(x, y);
    	Vector2 v2 = new Vector2(x - 23.4f, y);
    	assertFalse(v1.equals(v2));
    }
    
    @Test
    public void equals_null_false() {
    	float x = 43.43f, y = 65.10f;
    	Vector2 v = new Vector2(x, y);
    	assertFalse(v.equals(null));
    }
    
    @Test
    public void equalPointsReturnSameHashCode() {
    	float x = -532.43f, y = 644.12f;
    	Vector2 v1 = new Vector2(x, y);
    	Vector2 v2 = new Vector2(x, y);
    	assertTrue(v1.hashCode() == v2.hashCode());
    }
    
    @Test
    public void nonequalPointsReturnDifferentHashCodes() {
    	float x = -532.32f, y = 644.86f;
    	Vector2 v1 = new Vector2(x, y);
    	Vector2 v2 = new Vector2(x, y + 0.6f);
    	assertTrue(v1.hashCode() != v2.hashCode());
    }
}
