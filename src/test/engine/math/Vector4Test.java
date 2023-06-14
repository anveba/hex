package test.engine.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.engine.math.Vector3;
import main.engine.math.Vector4;

public class Vector4Test {
	@Test
    public void emptyContructorSetsCoordsToZero() {
        Vector4 v = new Vector4();
        final float delta = 0.001f;
        assertEquals(v.x, 0.0f, delta);
        assertEquals(v.y, 0.0f, delta);
        assertEquals(v.z, 0.0f, delta);
        assertEquals(v.w, 0.0f, delta);
    }

    @Test
    public void contructorSetsCorrespondingCoords() {
        final float x = 55.0f, y = 6842.12f, z = 0.12f, w = 8.02f;
        Vector4 v = new Vector4(x, y, z, w);
        final float delta = 0.001f;
        assertEquals(v.x, x, delta);
        assertEquals(v.y, y, delta);
        assertEquals(v.z, z, delta);
        assertEquals(v.w, w, delta);
    }
    
    @Test
    public void addAddsComponentWise() {
    	final float x = -532.32f, y = 644.86f, z = -2.0f, w = 8.02f;
    	Vector4 v1 = new Vector4(x, y, z, w);
    	Vector4 v2 = new Vector4(x, y, z, w);
    	Vector4 v3 = Vector4.add(v1, v2);
    	final float delta = 0.001f;
    	assertEquals(v3.x, 2 * x, delta);
        assertEquals(v3.y, 2 * y, delta);
        assertEquals(v3.z, 2 * z, delta);
        assertEquals(v3.w, 2 * w, delta);
    }
    
    @Test
    public void multiplyMultipliesComponentWise() {
    	final float x = -532.32f, y = 644.86f, z = -2.0f, w = 8.02f;
    	Vector4 v1 = new Vector4(x, y, z, w);
    	final float c = 63.2f;
    	Vector4 v2 = Vector4.multiply(c, v1);
    	final float delta = 0.001f;
    	assertEquals(v2.x, c * x, delta);
        assertEquals(v2.y, c * y, delta);
        assertEquals(v2.z, c * z, delta);
        assertEquals(v2.w, c * w, delta);
    }
    
    @Test
    public void lengthYieldsEuclideanLength() {
    	final float x = -532.32f, y = 644.86f, z = -2.0f, w = 8.02f;
    	Vector4 v = new Vector4(x, y, z, w);
    	final float delta = 0.001f;
    	assertEquals(Math.sqrt(x * x + y * y + z * z + w * w), v.length(), delta);
    }

}
