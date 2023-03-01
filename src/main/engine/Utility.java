package main.engine;

public class Utility {

    public static boolean floatEquals(float f1, float f2) {
        return Math.abs(f1 - f2) < 0.001f;
    }

}
