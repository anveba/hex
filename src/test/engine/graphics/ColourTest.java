package test.engine.graphics;

import static main.engine.Utility.*;
import static org.junit.Assert.*;

import org.junit.*;

import main.engine.EngineException;
import main.engine.Point2;
import main.engine.graphics.Colour;
import main.engine.math.Vector2;

public class ColourTest {

    @Test
    public void emptyContructorSetsValuesToZeroAndAlphaToOne() {
        Colour c = new Colour();
        assertEquals(0.0f, c.r(), 0.001f);
        assertEquals(0.0f, c.g(), 0.001f);
        assertEquals(0.0f, c.b(), 0.001f);
        assertEquals(1.0f, c.a(), 0.001f);
    }
    
    @Test
    public void contructorSetsValuesToValuesGiven() {
    	float r = 0.153f;
    	float g = 0.674f;
    	float b = 0.120f;
    	float a = 0.832f;
        Colour c = new Colour(r, g, b, a);
        assertEquals(r, c.r(), 0.001f);
        assertEquals(g, c.g(), 0.001f);
        assertEquals(b, c.b(), 0.001f);
        assertEquals(a, c.a(), 0.001f);
    }
    
    @Test
    public void contructorWithOneArgumentSetsRGBValuesToValuesGivenAndAlphaToOne() {
    	float v = 0.256f;
        Colour c = new Colour(v);
        assertEquals(v, c.r(), 0.001f);
        assertEquals(v, c.g(), 0.001f);
        assertEquals(v, c.b(), 0.001f);
        assertEquals(1.0f, c.a(), 0.001f);
    }
    
    @Test
    public void addFunctionAddsComponentwise() {
    	float r1 = 0.153f;
    	float g1 = 0.674f;
    	float b1 = 0.120f;
    	float a1 = 0.832f;
        Colour c1 = new Colour(r1, g1, b1, a1);
        float r2 = 0.023f;
    	float g2 = 0.111f;
    	float b2 = 0.009f;
    	float a2 = 0.201f;
        Colour c2 = new Colour(r2, g2, b2, a2);
        Colour added = Colour.add(c1, c2);
        assertEquals(r1 + r2, added.r(), 0.001f);
        assertEquals(g1 + g2, added.g(), 0.001f);
        assertEquals(b1 + b2, added.b(), 0.001f);
        assertEquals(a1 + a2, added.a(), 0.001f);
    }
    
    @Test(expected = EngineException.class)
    public void addingNullAsFirstArgumentThrowsException() {
        Colour c = new Colour();
        Colour.add(null, c);
    }
    
    @Test(expected = EngineException.class)
    public void addingNullAsSecondArgumentThrowsException() {
        Colour c = new Colour();
        Colour.add(c, null);
    }
    
    @Test(expected = EngineException.class)
    public void addingNullAsBothArgumentsThrowsException() {
        Colour.add(null, null);
    }
    
    @Test
    public void multiplyFunctionMultipliesComponentwise() {
    	float r1 = 0.153f;
    	float g1 = 0.674f;
    	float b1 = 0.120f;
    	float a1 = 0.832f;
        Colour c1 = new Colour(r1, g1, b1, a1);
        float r2 = 0.023f;
    	float g2 = 0.111f;
    	float b2 = 0.009f;
    	float a2 = 0.201f;
        Colour c2 = new Colour(r2, g2, b2, a2);
        Colour mult = Colour.multiply(c1, c2);
        assertEquals(r1 * r2, mult.r(), 0.001f);
        assertEquals(g1 * g2, mult.g(), 0.001f);
        assertEquals(b1 * b2, mult.b(), 0.001f);
        assertEquals(a1 * a2, mult.a(), 0.001f);
    }
    
    @Test(expected = EngineException.class)
    public void multiplyingNullAsFirstArgumentThrowsException() {
        Colour c = new Colour();
        Colour.multiply(null, c);
    }
    
    @Test(expected = EngineException.class)
    public void multiplyingNullAsSecondArgumentThrowsException() {
        Colour c = new Colour();
        Colour.multiply(c, null);
    }
    
    @Test(expected = EngineException.class)
    public void multiplyingNullAsBothArgumentsThrowsException() {
        Colour.multiply(null, null);
    }
}