package com.example.MentalHealthTracker.statistics;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.MentalHealthTracker.Statistics.Statistics;
import com.example.MentalHealthTracker.Statistics.StatisticsDBHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

// Tests Statistics database handler functions
@RunWith(AndroidJUnit4.class)
public class StatisticsDatabaseTest {
    private StatisticsDBHandler db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = new StatisticsDBHandler(context, null);
    }

    @After
    public void closeDb() {
        db.closeConnection();
    }

    @Test
    public void TestStatisticsDatabase() {
        String allEntries;
        int resourceID = 3;
        int numTimesUsed = 55;

        // wipe database clean at start of test
        db.deleteAllHandler();

        // check Load Handler returns empty string
        allEntries = db.loadHandler();
        assert(allEntries.equals(""));

        // verify that entry does not exist
        Statistics testNull = db.findHandler(resourceID);
        assert(testNull == null);

        // test adding and finding a Statistic
        Statistics stat1 = new Statistics(resourceID, numTimesUsed);
        db.addHandler(stat1);
        Statistics testStat1 = db.findHandler(resourceID);
        assert(testStat1 != null);
        assert(testStat1.getID() == stat1.getID());
        assert(testStat1.getNumTimesUsed() == stat1.getNumTimesUsed());

        // test updating a Statistic -> use GetNumTimesUsed and Find handlers
        db.updateHandler(resourceID);
        int getNumTimesUsed = db.getTimesUsedHandler(resourceID);
        assert(getNumTimesUsed == (numTimesUsed+1));

        Statistics testStat2 = db.findHandler(resourceID);
        assert(testStat2 != null);
        assert(testStat2.getID() == stat1.getID());
        assert(testStat2.getNumTimesUsed() == (stat1.getNumTimesUsed()+1));

        // test deleting a statistic
        db.deleteHandler(resourceID);
        Statistics testStat3 = db.findHandler(numTimesUsed);
        assert(testStat3 == null);

        // test adding multiple entries, then loading all
        Statistics addStatistic1 = new Statistics(1, 500);
        Statistics addStatistic2 = new Statistics(2, 7000);
        Statistics addStatistic3 = new Statistics(3, 45);
        db.addHandler(addStatistic1);
        db.addHandler(addStatistic2);
        db.addHandler(addStatistic3);

        allEntries = db.loadHandler();

        assert(allEntries.contains("1"));
        assert(allEntries.contains("500"));

        assert(allEntries.contains("2"));
        assert(allEntries.contains("7000"));

        assert(allEntries.contains("3"));
        assert(allEntries.contains("45"));

        // Finally, delete all and verify database empty
        db.deleteAllHandler();
        allEntries = db.loadHandler();
        assert(allEntries.equals(""));
    }
}
