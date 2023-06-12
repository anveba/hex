package main.engine.format;

import main.engine.Utility;

public class TimeFormat implements Format {

    @Override
    public String formatInt(int time) {
        return Utility.getFormattedTime(time);
    }
}
