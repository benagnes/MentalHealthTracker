package com.example.MentalHealthTracker.exercises;

import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.MentalHealthTracker.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

// Tests Exercises Routine -> without skipping exercises and with skipping
@RunWith(AndroidJUnit4.class)
public class ExercisesTest {
    static Intent intent;

    static {
        intent = new Intent(ApplicationProvider.getApplicationContext(), Exercises.class);
        intent.putExtra(Exercises.ACTIVITY_REF, "0");
    }

    @Rule
    public ActivityScenarioRule<Exercises> interactiveScenario
            = new ActivityScenarioRule<>(intent);

    // Test initial state is correct when loading activity
    @Test
    public void data_loads_for_first_step() {
        final String startButton = "Start";
        // Make sure start button text is correct
        onView(withId(R.id.exerciseStartButton)).check(matches(withText(startButton)));
    }

    // Tests that 'Get Ready!' and initial countdown occurs when START button clicked
    @Test
    public void getReadyCountdown() throws InterruptedException {
        final String getReady = "Get Ready!";
        Thread.sleep(500);
        onView(withId(R.id.exerciseStartButton)).perform(click());
        Thread.sleep(100);

        // check Get Ready message
        onView(withId(R.id.getReadyTextView)).check(matches(withText(getReady)));

        // test initial countdown
        onView(withId(R.id.initialCountdownTextView)).check(matches(withText("3")));
        Thread.sleep(1000);
        onView(withId(R.id.initialCountdownTextView)).check(matches(withText("2")));
        Thread.sleep(1000);
        onView(withId(R.id.initialCountdownTextView)).check(matches(withText("1")));
        Thread.sleep(1000);
        onView(withId(R.id.initialCountdownTextView)).check(matches(withText("0")));
        Thread.sleep(1000);
    }

    // Go through exercise routine without pressing skip button
    @Test
    public void exercise_transitions_withoutSkip() throws InterruptedException {
        String[] exerciseNames = {"Jumping Jacks", "Lunges", "Squats", "Push Ups",
                "Butt Kickers", "High Knees", "Sit Ups", "Plank", "Burpees",
                "Side Lunges", "", "Jumping Jacks", "Lunges", "Squats", "Push Ups",
                "Butt Kickers", "High Knees", "Sit Ups", "Plank", "Burpees",
                "Side Lunges"};
        final String completionMessage = "Great Work!";

        onView(withId(R.id.exerciseStartButton)).perform(click());
        Thread.sleep(5 * 1000);
        for (String exerciseName : exerciseNames) {
            // Make sure the exercise title matches
            onView(withId(R.id.exerciseNameTextView)).check(matches(withText(exerciseName)));
            Thread.sleep(30 * 1000);
        }
        // test completion message
        onView(withId(R.id.exerciseNameTextView)).check(matches(withText(completionMessage)));
    }

    // Go through exercise routine with pressing skip button for each exercise
    @Test
    public void exercise_transitions_withSkip() throws InterruptedException {
        String[] exerciseNames = {"Jumping Jacks", "Lunges", "Squats", "Push Ups",
            "Butt Kickers", "High Knees", "Sit Ups", "Plank", "Burpees",
            "Side Lunges", "", "Jumping Jacks", "Lunges", "Squats", "Push Ups",
            "Butt Kickers", "High Knees", "Sit Ups", "Plank", "Burpees",
            "Side Lunges"};
        final String completionMessage = "Great Work!";

        onView(withId(R.id.exerciseStartButton)).perform(click());
        Thread.sleep(5 * 1000);
        for (String exerciseName : exerciseNames) {
            // Make sure the exercise title matches
            onView(withId(R.id.exerciseNameTextView)).check(matches(withText(exerciseName)));
            System.out.println(exerciseName);
            // press skip button to skip to next exercise
            onView(withId(R.id.mStepSkipButton)).perform(click());
            Thread.sleep(1000);
        }
        // Test completion message
        onView(withId(R.id.exerciseNameTextView)).check(matches(withText(completionMessage)));
    }
}
