package main.engine.math;

import java.util.Objects;
import static main.engine.Utility.floatEquals;

public class Vector2 {
    private float x, y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2() {
        this(0.0f, 0.0f);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
    
    @Override
    public String toString() {
    	return "(" + getX() + ", " + getY() + ")";
    }
    
    public boolean approx(Vector2 other) {
    	return floatEquals(getX(), other.getX()) && floatEquals(getY(), other.getY());
    }
    
    @Override
    public boolean equals(Object other) {
    	if (other == null || other.getClass() != getClass())
    		return false;
    	Vector2 v = (Vector2)other;
    	return getX() == v.getX() && getY() == v.getY();
    }
    
    @Override
    public int hashCode() {
    	return Objects.hash(getX(), getY());
    }
}
