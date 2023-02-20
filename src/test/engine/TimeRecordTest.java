package test.engine;

import static org.junit.jupiter.api.Assertions.*;
import static engine.Utility.*;

import org.junit.jupiter.api.Test;

import engine.TimeRecord;

class TimeRecordTest {

    @Test
    void contructorTest1() {
        float elapsed = 4.596f;
        float total = 52.56f;
        var r = new TimeRecord(elapsed, total);
        assertTrue(floatEquals(r.elapsedSeconds(), elapsed));
        assertTrue(floatEquals(r.totalSeconds(), total));
    }

}
