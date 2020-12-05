package com.example.MentalHealthTracker.meditation;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.MentalHealthTracker.R;
import com.example.MentalHealthTracker.helpers.RecyclerViewItemCountAssertion;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MeditationTest {
    private static final int ROUTINE_LENGTH = 1;
    private static final long ROUTINE_LOAD_TIMEOUT = 500;

    @Rule
    public ActivityScenarioRule<Meditation> meditationScenario =
            new ActivityScenarioRule<>(Meditation.class);

    public void pauseDataLoad() {
        // Wait for the data to load
        try {
            Thread.sleep(ROUTINE_LOAD_TIMEOUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void routine_data_loaded() {
        pauseDataLoad();
        onView(withId(R.id.meditationRoutines)).check(
                new RecyclerViewItemCountAssertion(ROUTINE_LENGTH)
        );
    }
}