package main.engine;

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
