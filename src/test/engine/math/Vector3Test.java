package test.engine.math;

import static main.engine.Utility.floatEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import main.engine.math.Vector2;
import main.engine.math.Vector3;

public class Vector3Test {
    @Test
    public void emptyContructorSetsCoordsToZero() {
        Vector3 v = new Vector3();
        final float delta = 0.001f;
        assertEquals(v.x, 0.0f, delta);
        assertEquals(v.y, 0.0f, delta);
        assertEquals(v.z, 0.0f, delta);
    }

    @Test
    public void contructorSetsCorrespondingCoords() {
        final float x = 55.0f, y = 6842.12f, z = 0.12f;
        Vector3 v = new Vector3(x, y, z);
        final float delta = 0.001f;
        assertEquals(v.x, x, delta);
        assertEquals(v.y, y, delta);
        assertEquals(v.z, z, delta);
    }
    
    @Test
    public void addAddsComponentWise() {
    	final float x = -532.32f, y = 644.86f, z = -2.0f;
    	Vector3 v1 = new Vector3(x, y, z);
    	Vector3 v2 = new Vector3(x, y, z);
    	Vector3 v3 = Vector3.add(v1, v2);
    	final float delta = 0.001f;
    	assertEquals(v3.x, 2 * x, delta);
        assertEquals(v3.y, 2 * y, delta);
        assertEquals(v3.z, 2 * z, delta);
    }
    
    @Test
    public void multiplyMultipliesComponentWise() {
    	final float x = -532.32f, y = 644.86f, z = -2.0f;
    	Vector3 v1 = new Vector3(x, y, z);
    	final float c = 63.2f;
    	Vector3 v2 = Vector3.multiply(c, v1);
    	final float delta = 0.001f;
    	assertEquals(v2.x, c * x, delta);
        assertEquals(v2.y, c * y, delta);
        assertEquals(v2.z, c * z, delta);
    }
    
    @Test
    public void lengthYieldsEuclideanLength() {
    	final float x = -532.32f, y = 644.86f, z = -2.0f;
    	Vector3 v = new Vector3(x, y, z);
    	final float delta = 0.001f;
    	assertEquals(Math.sqrt(x * x + y * y + z * z), v.length(), delta);
    }
    
    @Test
    public void crossProductYieldsPerpendicularVector() {
    	Vector3 v1 = new Vector3(1.0f, 0.0f, 0.0f);
    	Vector3 v2 = new Vector3(0.0f, 1.0f, 0.0f);
    	Vector3 v3 = Vector3.cross(v1, v2);
    	final float delta = 0.001f;
    	assertEquals(v3.x, 0.0f, delta);
        assertEquals(v3.y, 0.0f, delta);
        assertEquals(v3.z, 1.0f, delta);
    }
}
