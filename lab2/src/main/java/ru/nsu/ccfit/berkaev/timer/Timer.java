package ru.nsu.ccfit.berkaev.timer;

import ru.nsu.ccfit.berkaev.ui.UI;

public class Timer {
    private int timePassed;
    private boolean stopTimer;

    public Timer() {
        this.timePassed = 0;
        this.stopTimer = true;
    }

    public void setTimePassed(int time) {
        timePassed = time;
    }

    public void resetTimer(UI ui) {
        timePassed = 0;
        ui.setTime(timePassed);
    }

    public void setStopTimer(boolean stopTimer) {
        this.stopTimer = stopTimer;
    }

    public boolean getStopTimer() {
        return (this.stopTimer);
    }

    public void plusSecond() {
        timePassed++;
    }

    public int getTimePassed() {
        return timePassed;
    }

    public void startTimer(UI ui) {
        stopTimer = false;
        Thread timer = new Thread(() -> {
            while (!stopTimer) {
                timePassed++;
                ui.setTime(timePassed);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
            }
        });
        timer.start();
    }
}
