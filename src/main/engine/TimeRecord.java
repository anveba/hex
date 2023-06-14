package main.engine;

/**
 * Represents time; both elapsed and total time since some event, usually
 * since the last frame and since the program began.  
 * @author Andreas - s214971
 *
 */
public class TimeRecord {

    private float elapsedSeconds;
    private float totalSeconds;

    public TimeRecord(float elapsedSeconds, float totalSeconds) {
        this.elapsedSeconds = elapsedSeconds;
        this.totalSeconds = totalSeconds;
    }

    public float elapsedSeconds() {
        return elapsedSeconds;
    }
    
    public float totalSeconds() {
        return totalSeconds;
    }
}
