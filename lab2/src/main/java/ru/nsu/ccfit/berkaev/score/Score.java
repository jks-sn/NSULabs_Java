package ru.nsu.ccfit.berkaev.score;

import ru.nsu.ccfit.berkaev.logic.Game;

import static java.lang.Math.ceil;
import static ru.nsu.ccfit.berkaev.constants.Constants.PROPERTIES_PATH;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Properties;


public class Score
{
    Properties properties;
    ArrayList<Time> bestTimes;

    int gamesPlayed;
    int gamesWon;

    int longestWinningStreak;
    int longestLosingStreak;

    int currentStreak;

    int currentWinningStreak;
    int currentLosingStreak;

    public Score() {
        gamesPlayed = gamesWon = currentStreak = longestLosingStreak = longestWinningStreak = currentWinningStreak = currentLosingStreak = 0;
        bestTimes = new ArrayList<>();
        properties = new Properties();
        InputStream stream = Score.class.getResourceAsStream(PROPERTIES_PATH);
        try {
            properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public int getGamesPlayed()
    {
        return gamesPlayed;
    }

    public int getGamesWon()
    {
        return gamesWon;
    }

    public int getWinPercentage()
    {
        double gP = gamesPlayed;
        double gW = gamesWon;

        double percentage = ceil((gW/gP) * 100);

        return (int)percentage;
    }

    public int getLongestWinningStreak()
    {
        return longestWinningStreak;
    }

    public int getLongestLosingStreak()
    {
        return longestLosingStreak;
    }

    public int getCurrentStreak()
    {
        return currentStreak;
    }

    public int getCurrentLosingStreak()
    {
        return currentLosingStreak;
    }

    public int getCurrentWinningStreak(){
        return currentWinningStreak;
    }

    public void incGamesWon()
    {
        gamesWon++;
    }

    public void incGamesPlayed()
    {
        gamesPlayed++;
    }

    public void incCurrentStreak()
    {
        currentStreak++;
    }

    public void incCurrentLosingStreak()
    {
        currentLosingStreak++;

        if (longestLosingStreak < currentLosingStreak)
        {
            longestLosingStreak = currentLosingStreak;
        }
    }

    public void incCurrentWinningStreak()
    {
        currentWinningStreak++;

        if (longestWinningStreak < currentWinningStreak)
        {
            longestWinningStreak = currentWinningStreak;
        }
    }


    public void decCurrentStreak()
    {
        currentStreak--;
    }


    public void resetScore()
    {
        gamesPlayed = gamesWon = currentStreak = longestLosingStreak = longestWinningStreak = currentWinningStreak = currentLosingStreak = 0;
    }



    public ArrayList<Time> getBestTimes()
    {
        return bestTimes;
    }


    public void addTime(int time)
    {
        bestTimes.add(new Time(time));
        bestTimes.sort(new TimeComparator());

        if(bestTimes.size() > 5)
            bestTimes.remove(bestTimes.size()-1);
    }

    public boolean populate()
    {
        try {
                gamesPlayed = Integer.parseInt(this.properties.getProperty("GAMES_PLAYED"));
                gamesWon = Integer.parseInt(this.properties.getProperty("GAMES_WON"));

                longestWinningStreak = Integer.parseInt(this.properties.getProperty("LWSTREAK"));
                longestLosingStreak = Integer.parseInt(this.properties.getProperty("LLSTREAK"));

                currentStreak = Integer.parseInt(this.properties.getProperty("CSTREAK"));

                currentWinningStreak = Integer.parseInt(this.properties.getProperty("CWSTREAK"));
                currentLosingStreak = Integer.parseInt(this.properties.getProperty("CLSTREAK"));


            for(int i = 0; i < gamesPlayed;++i)
            {
                int time = Integer.parseInt(this.properties.getProperty("TIME"+ i));
                bestTimes.add(new Time(time));
            }

            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    public void save(int time)
    {
        addTime(time);
        properties.setProperty(("Time"+gamesPlayed),Integer.toString(time));
    }

    public class TimeComparator implements Comparator<Time>
    {
        @Override
        public int compare(Time a, Time b) {
            return Integer.compare(a.getTimeValue(), b.getTimeValue());
        }
    }

    //----------------------------------------------------------//
    public static class Time{
        int time;
        public Time(int t)
        {
            time = t;
        }

        public int getTimeValue()
        {
            return time;
        }
    }
}