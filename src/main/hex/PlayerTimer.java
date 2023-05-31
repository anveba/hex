package main.hex;

import java.util.*;

public class PlayerTimer {
    private Timer timer;
    private int remainingTime;
    boolean isPaused;

    public PlayerTimer(int initialDuration) {
        this.remainingTime = initialDuration;
        this.isPaused = true;
    }

    public void startTimer() {
        if (isPaused) {
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    remainingTime -= 1;
                    if (remainingTime <= 0) {
                        outOfTime();
                    }
                }
            };
            timer.scheduleAtFixedRate(timerTask, 0, 1000);
            isPaused = false;
        }
    }

    public void pauseTimer() {
        if (!isPaused) {
            timer.cancel();
            isPaused = true;
        }
    }

    public void outOfTime() {
        if (!isPaused) {
            timer.cancel();
            isPaused = true;
            System.out.println("OUT OF TIME");
        }
    }

    public int getRemainingTime() {
        return remainingTime;
    }
}
