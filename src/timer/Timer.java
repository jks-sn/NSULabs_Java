package timer;

import java.util.TimerTask;

public class Timer {
    private long seconds;
    private long minets;
    private static Thread timer;

    private static TimerTask task;
    private boolean isRunning;

    public Timer()
    {
        seconds = 0;
        minets = 0;
        isRunning = false;
    }
    public void startTimer()
    {
        isRunning = true;
        timer = new Thread(() -> {
            while(isRunning)
            {
                seconds++;
                if(seconds > 60) {
                    minets++;
                    seconds -= 60;
                }
                try{
                    Thread.sleep(1000);
                }
                catch(InterruptedException ignored){}
            }
        });

        timer.start();
    }
    public void stopTimer()
    {
        isRunning = false;

        try
        {
            if (timer!= null)
                timer.join();
        }
        catch (InterruptedException ignored)
        {

        }
    }
    public void resetTimer()
    {
        seconds = 0;
        minets = 0;

    }
    public long getTime()
    {
        return seconds+minets*60;
    }
}

