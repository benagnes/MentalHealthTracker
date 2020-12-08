package com.example.MentalHealthTracker.MoodTracker;

import com.example.MentalHealthTracker.mood_tracker.Mood;

import org.junit.Test;
import static org.junit.Assert.*;

// Unit tests for 'Mood' object -> com.example.mentalhealthtracker.mood_tracker.db.mood
public class MoodObjectTest {
    @Test
    public void checkMoodGetters() {
        Mood mood = new Mood(1, "Nov-23-2020", "Happy",
                "Sleepy", "Lets Party!", "Productive");
        assertEquals(1, mood.getID());
        assertEquals("Nov-23-2020", mood.getDate());
        assertEquals("Sleepy", mood.getEnergy());
        assertEquals("Lets Party!", mood.getSocial_meter());
        assertEquals("Productive", mood.getProductivity());
    }

    @Test
    public void checkMoodSetters() {
        Mood mood = new Mood();
        mood.setID(1);
        mood.setDate("Dec-5-2021");
        mood.setEnergy("Low");
        mood.setSocial_meter("Not feeling it");
        mood.setProductivity("Unproductive");

        assertEquals(1, mood.getID());
        assertEquals("Dec-5-2021", mood.getDate());
        assertEquals("Low", mood.getEnergy());
        assertEquals("Not feeling it", mood.getSocial_meter());
        assertEquals("Unproductive", mood.getProductivity());
    }
}
