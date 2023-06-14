package main.engine.math;

/**
 * Represents a 3D vector.
 * @author Andreas - s214971
 *
 */
public class Vector3 {
	public float x, y, z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3() {
        this(0.0f, 0.0f, 0.0f);
    }
    
    public static Vector3 add(Vector3 v1, Vector3 v2) {
    	return new Vector3(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
    }
    
    public static Vector3 multiply(float c, Vector3 v) {
    	return new Vector3(c * v.x, c * v.y, c * v.z);
    }
    
    public float lengthSquared() {
    	return x * x + y * y + z * z;
    }
    
    public float length() {
    	return (float)Math.sqrt(lengthSquared());
    }

	public static Vector3 cross(Vector3 v1, Vector3 v2) {
		return new Vector3(
				v1.y * v2.z - v2.y * v1.z,
				-(v1.x * v2.z - v2.x * v1.z),
				v1.x * v2.y - v2.x * v1.y
				);
	}
}
