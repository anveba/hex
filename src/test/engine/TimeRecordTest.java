package test.engine;

import static main.engine.Utility.*;
import static org.junit.Assert.*;

import org.junit.Test;

import main.engine.TimeRecord;

public class TimeRecordTest {

    @Test
    public void contructor_validValues_valuesSet() {
        float elapsed = 4.596f;
        float total = 52.56f;
        var r = new TimeRecord(elapsed, total);
        assertTrue(floatEquals(r.elapsedSeconds(), elapsed));
        assertTrue(floatEquals(r.totalSeconds(), total));
    }

}
