package main.engine.math;

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
    
    public float lengthSquared() {
    	return x * x + y * y + z * z;
    }
    
    public float length() {
    	return (float)Math.sqrt(lengthSquared());
    }
}
