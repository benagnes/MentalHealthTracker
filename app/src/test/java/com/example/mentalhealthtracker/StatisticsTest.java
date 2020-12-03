package com.example.mentalhealthtracker;

import com.example.mentalhealthtracker.Statistics.Statistics;

import org.junit.Test;
import static org.junit.Assert.*;

// Unit test for Statistics object
public class StatisticsTest {
    @Test
    public void checkStatisticsGetters() {
        Statistics statistics = new Statistics(1, 10);
        assertEquals(1, statistics.getID());
        assertEquals(10, statistics.getNumTimesUsed());
    }

    @Test
    public void checkStatisticSetters() {
        Statistics statistics = new Statistics();
        statistics.setID(2);
        statistics.setNumTimesUsed(5);
        assertEquals(2, statistics.getID());
        assertEquals(5, statistics.getNumTimesUsed());
    }
}
