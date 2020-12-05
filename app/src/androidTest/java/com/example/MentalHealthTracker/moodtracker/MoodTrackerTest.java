package com.example.MentalHealthTracker.moodtracker;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.MentalHealthTracker.mood_tracker.Mood;
import com.example.MentalHealthTracker.mood_tracker.MoodDBhandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

// Tests Mood Tracker database handler functions
@RunWith(AndroidJUnit4.class)
public class MoodTrackerTest {
    private MoodDBhandler db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = new MoodDBhandler(context, null);
    }

    @After
    public void closeDb() {
        db.closeConnection();
    }

    @Test
    public void writeUserAndReadInList() {
        String allEntries;
        int testID = 18022020;
        String testDate = "18022020";
        Mood mood = new Mood(testID, testDate, "Happy", "High",
                "Party!", "Productive" );

        // wipe database clean at start of test
        db.deleteAllHandler();

        // check Load Handler returns nothing
        allEntries = db.loadHandler();
        assert(allEntries.equals(""));

        // verify that entry does not exist
        Mood testNull = db.findHandler(testDate);
        assert(testNull == null);

        // test adding and finding a mood
        db.addHandler(mood);
        Mood testMood = db.findHandler(testDate);
        assert(testMood != null);
        assert(testMood.getID() == mood.getID());
        assert(testMood.getDate().equals(mood.getDate()));
        assert(testMood.getMood().equals(mood.getMood()));
        assert(testMood.getEnergy().equals(mood.getEnergy()));
        assert(testMood.getSocial_meter().equals(mood.getSocial_meter()));
        assert(testMood.getProductivity().equals(mood.getProductivity()));

        // test updating a mood
        Mood updateMood = new Mood(testID, testDate, "Sad", "No energy",
                "Not social", "Unproductive");
        boolean updateResult = db.updateHandler(updateMood);
        assert(updateResult);

        Mood testMood2 = db.findHandler(testDate);
        assert(testMood2 != null);
        assert(testMood2.getID() == updateMood.getID());
        assert(testMood2.getDate().equals(updateMood.getDate()));
        assert(testMood2.getMood().equals(updateMood.getMood()));
        assert(testMood2.getEnergy().equals(updateMood.getEnergy()));
        assert(testMood2.getSocial_meter().equals(updateMood.getSocial_meter()));
        assert(testMood2.getProductivity().equals(updateMood.getProductivity()));

        // test deleting a mood
        db.deleteHandler(testDate);
        Mood testMood3 = db.findHandler(testDate);
        assert(testMood3 == null);

        // test adding multiple entries, then loading all
        Mood addMood1 = new Mood(12, "15", "Down", "Sleepy",
                "Me Day", "not today");
        Mood addMood2 = new Mood(22, "33", "Angry", "High",
                "Ew People", "Decent");
        Mood addMood3 = new Mood(44, "66", "Stressed", "Mediocre",
                "Staying In", "Undecided");
        db.addHandler(addMood1);
        db.addHandler(addMood2);
        db.addHandler(addMood3);

        allEntries = db.loadHandler();

        assert(allEntries.contains("12"));
        assert(allEntries.contains("15"));
        assert(allEntries.contains("Down"));
        assert(allEntries.contains("Sleepy"));
        assert(allEntries.contains("Me Day"));
        assert(allEntries.contains("not today"));

        assert(allEntries.contains("22"));
        assert(allEntries.contains("33"));
        assert(allEntries.contains("Angry"));
        assert(allEntries.contains("High"));
        assert(allEntries.contains("Ew People"));
        assert(allEntries.contains("Decent"));

        assert(allEntries.contains("44"));
        assert(allEntries.contains("66"));
        assert(allEntries.contains("Stressed"));
        assert(allEntries.contains("Mediocre"));
        assert(allEntries.contains("Staying In"));
        assert(allEntries.contains("Undecided"));

        // Finally, delete all and verify database empty
        db.deleteAllHandler();
        allEntries = db.loadHandler();
        assert(allEntries.equals(""));
    }
}
