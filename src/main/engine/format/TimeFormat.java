package main.engine.format;

import main.engine.Utility;

/**
 *
 * The format interface is used to format Strings, into a time format.
 * currently accepted by the slider, to format the text displayed on the slider from integers (in seconds)
 * to time (eg. 60 seconds -> 1:00)
 *
 * @Author Oliver Grønborg Christensen - s204479
 */
public class TimeFormat implements Format {

    @Override
    public String formatInt(int time) {
        return Utility.getFormattedTime(time);
    }
}
