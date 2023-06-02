package test.engine;

import static main.engine.Utility.floatEquals;
import static org.junit.Assert.*;

import org.junit.Test;

import main.engine.*;
import main.engine.math.Point2;

public class Point2Test {

    @Test
    public void contructor_empty_xYEqualsZero() {
        Point2 p = new Point2();
        assertEquals(0, p.getX());
        assertEquals(0, p.getY());
    }

    @Test
    public void contructor_validCoords_xYReturned() {
        int x = 52, y = 23;
        Point2 p = new Point2(x, y);
        assertEquals(x, p.getX());
        assertEquals(y, p.getY());
    }

    @Test
    public void getSetX_validNumber_xReturned() {
    	Point2 p = new Point2();
        int x = 12;
        p.setX(x);
        assertEquals(x, p.getX());
    }
    
    @Test
    public void getSetY_validNumber_yReturned() {
    	Point2 p = new Point2();
        int y = -6;
        p.setY(y);
        assertEquals(y, p.getY());
    }
    
    @Test
    public void toStringReturnsStringWithCoordinates() {
    	int x = 3, y = 5;
    	Point2 p = new Point2(x, y);
    	assertEquals("(" + x + ", " + y + ")", p.toString());
    }

    @Test
    public void equals_equalPoint_true() {
    	int x = -3, y = 7;
    	Point2 p1 = new Point2(x, y);
    	Point2 p2 = new Point2(x, y);
    	assertTrue(p1.equals(p2));
    }
    
    @Test
    public void equals_nonequalPoint_false() {
    	int x = -3, y = 7;
    	Point2 p1 = new Point2(x, y);
    	Point2 p2 = new Point2(x + 1, y);
    	assertFalse(p1.equals(p2));
    }
    
    @Test
    public void equals_null_false() {
    	int x = 100, y = -2;
    	Point2 p1 = new Point2(x, y);
    	assertFalse(p1.equals(null));
    }
    
    @Test
    public void equalPointsReturnSameHashCode() {
    	int x = -532, y = 644;
    	Point2 p1 = new Point2(x, y);
    	Point2 p2 = new Point2(x, y);
    	assertTrue(p1.hashCode() == p2.hashCode());
    }
    
    @Test
    public void nonequalPointsReturnDifferentHashCodes() {
    	int x = -532, y = 644;
    	Point2 p1 = new Point2(x, y);
    	Point2 p2 = new Point2(x, y - 6);
    	assertTrue(p1.hashCode() != p2.hashCode());
    }
}
