package test.engine;

import static org.junit.jupiter.api.Assertions.*;
import static engine.Utility.*;

import org.junit.jupiter.api.Test;

import engine.TimeRecord;

class TimeRecordTest {

    @Test
    void contructorTest1() {
        float seconds = 4.596f;
        assert (floatEquals(new TimeRecord(seconds).seconds(), seconds));
    }

}
