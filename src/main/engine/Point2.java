package main.engine;

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
}
