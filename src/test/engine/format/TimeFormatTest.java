package test.engine.format;

import main.engine.Utility;
import main.engine.format.TimeFormat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TimeFormatTest {

    TimeFormat timeFormat;

    @Before
    public void setup() {
        timeFormat = new TimeFormat();
    }

    @Test
    public void testFormatInt() {
        int seconds = 100;
        Assert.assertEquals(Utility.getFormattedTime(seconds), timeFormat.formatInt(seconds));
    }


}
