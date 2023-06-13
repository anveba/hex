package main.engine.format;

/**
 *
 * The format interface is used to format the Strings.
 * Currently accepted by the slider, to format the text displayed on the slider (eg. time, percentage etc.)
 *
 * @Author Oliver Grønborg Christensen - s204479
 */
public interface Format {
    public String formatInt(int time);
}
