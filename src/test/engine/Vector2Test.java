package test.engine;

import static org.junit.jupiter.api.Assertions.*;
import static engine.Utility.*;

import org.junit.jupiter.api.Test;

import engine.Vector2;

class Vector2Test {

    @Test
    void contructorTest1() {
        Vector2 v = new Vector2();
        assertTrue(floatEquals(v.getX(), 0.0f));
        assertTrue(floatEquals(v.getY(), 0.0f));
    }

    @Test
    void contructorTest2() {
        float x = 55.0f, y = 6842.12f;
        Vector2 v = new Vector2(x, y);
        assertTrue(floatEquals(v.getX(), x));
        assertTrue(floatEquals(v.getY(), y));
    }

    @Test
    void setterTest1() {
        Vector2 v = new Vector2();
        float x = 782.34f, y = 32.87f;
        v.setX(x);
        v.setY(y);
        assertTrue(floatEquals(v.getX(), x));
        assertTrue(floatEquals(v.getY(), y));
    }
}
