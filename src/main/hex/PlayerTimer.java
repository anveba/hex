package main.hex;

import main.engine.TimeRecord;
import main.engine.Utility;

/**
 * This class is the logic behind the chess-inspired timers for each of the two players
 * indicating how long they have left. When a player runs out of time the other player wins.
 *
 * @Author Oliver Siggaard
 */

public class PlayerTimer implements Updateable {
    private double remainingTime;
    private boolean isPaused;
    private Runnable callback;

    public PlayerTimer(double initialDuration) {
        this.remainingTime = initialDuration;
        this.isPaused = true;
    }

    public void setCallback(Runnable callback) {
        this.callback = callback;
    }

    public void startTimer() {
        if (isPaused)
            isPaused = false;
    }

    public void pauseTimer() {
        if (!isPaused)
            isPaused = true;
    }

    private void outOfTime() {
        if (!isPaused) {
            isPaused = true;
            if (callback != null)
                callback.run();
        }
    }

    public double getRemainingTime() {
        return remainingTime;
    }

    public void addTime(float addedSeconds) {
        remainingTime += addedSeconds;
    }
    public void setTime(int newTime) {
        remainingTime = newTime;
    }

    @Override
    public void update(TimeRecord elapsed) {
        if (!isPaused) {
            remainingTime -= elapsed.elapsedSeconds();
            if (remainingTime < 0) {
                remainingTime = 0;
                outOfTime();
            }
        }
    }

    public String getFormattedTime() {
        return Utility.getFormattedTime(getRemainingTime());
    }

    public boolean getIsPaused() {
        return isPaused;
    }
}
