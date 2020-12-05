package com.example.MentalHealthTracker.mindfulness;

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
public class MindfulnessTest {
    private static final int MINDFULNESS_ACTIVITIES_LENGTH = 2;

    @Rule
    public ActivityScenarioRule<Mindfulness> mindfulnessScenario
            = new ActivityScenarioRule<>(Mindfulness.class);

    @Test
    public void mindfulness_routines_load() throws InterruptedException {
        Thread.sleep(1000);
        onView(withId(R.id.mindfulnessActivities)).check(
                new RecyclerViewItemCountAssertion(MINDFULNESS_ACTIVITIES_LENGTH)
        );
    }
}