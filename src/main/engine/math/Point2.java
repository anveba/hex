package main.engine.math;

import java.util.Objects;

/**
 * Represents an integer point in 2D space.
 * @author andreas
 *
 */
public class Point2 {

    private int x, y;

    public Point2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point2() {
        this(0, 0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    @Override
    public String toString() {
    	return "(" + getX() + ", " + getY() + ")";
    }
    
    @Override
    public boolean equals(Object other) {
    	if (other == null || other.getClass() != getClass())
    		return false;
    	Point2 p = (Point2)other;
    	return getX() == p.getX() && getY() == p.getY();
    }
    
    @Override
    public int hashCode() {
    	return Objects.hash(getX(), getY());
    }
}
