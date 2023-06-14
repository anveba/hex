package main.engine.math;

/**
 * Represents a 4D vector.
 * @author Andreas - s214971
 *
 */
public class Vector4 {
	public float x, y, z, w;

    public Vector4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4() {
        this(0.0f, 0.0f, 0.0f, 0.0f);
    }
    
    public static Vector4 add(Vector4 v1, Vector4 v2) {
    	return new Vector4(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z, v1.w + v2.w);
    }
    
    public static Vector4 multiply(float c, Vector4 v) {
    	return new Vector4(c * v.x, c * v.y, c * v.z, c * v.w);
    }
    
    public float lengthSquared() {
    	return x * x + y * y + z * z + w * w;
    }
    
    public float length() {
    	return (float)Math.sqrt(lengthSquared());
    }
}
