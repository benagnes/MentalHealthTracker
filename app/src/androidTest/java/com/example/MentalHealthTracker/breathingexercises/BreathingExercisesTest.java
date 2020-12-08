package com.example.MentalHealthTracker.breathingexercises;

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

// Tests breathing exercises routine
@RunWith(AndroidJUnit4.class)
public class BreathingExercisesTest {
    static Intent intent;

    static {
        intent = new Intent(ApplicationProvider.getApplicationContext(), BreathingExercises.class);
        intent.putExtra(BreathingExercises.ACTIVITY_REF, "0");
    }

    @Rule
    public ActivityScenarioRule<BreathingExercises> interactiveScenario
            = new ActivityScenarioRule<>(intent);

    // Test that loading activity is at initial state
    @Test
    public void data_loads_for_first_step() throws InterruptedException {
        final String stepTitle = "Welcome to Breathing Exercises";
        final String startButton = "Start";
        Thread.sleep(3 * 1000);
        // Make sure the activity title matches
        onView(withId(R.id.breatheStateTextView)).check(matches(withText(stepTitle)));
        // Make sure start button text is correct
        onView(withId(R.id.nextButton)).check(matches(withText(startButton)));
    }

    // Test routine that each string is displayed as expected, and the 'next' button works correctly
    @Test
    public void breathing_exercise_transitions() throws InterruptedException {
        String[] stepsIds = {"Focus on your Breathing","Breathe in…", "Breathe out…",
                "Breathe in…", "Breathe out…", "Breathe in…",
                "Breathe out…", "Breathe in…", "Breathe out…", "Complete!" };

        // Initialize step instruction
        String[] instructionIds = {"Get into a comfortable position",
                "Imagine the air is filled with a sense of peace and calm",
                "Imagine the air leaves with your stress and tension",
                "Notice your abdomen expanding with deep inhalations",
                "Bring awareness to your breathing",
                "Tighten your muscles",
                "Relax your muscles, let go of all tension",
                "Imagine peace and tranquility",
                "Let go of stress and anxiety",
                "Hope you feel relaxed and rejuvenated" };

        // Next button text
        final String StartButton = "Start";
        final String NextButton = "Next";

        onView(withId(R.id.nextButton)).check(matches(withText(StartButton)));
        Thread.sleep(2 * 1000);
        for(int i=0;i<10; i++) {
            onView(withId(R.id.nextButton)).perform(click());
            Thread.sleep(2 * 1000);
            // Make sure next button reads 'next'
            if (i == 9) {
                onView(withId(R.id.nextButton)).check(matches(withText(StartButton)));
            }
            else {
                onView(withId(R.id.nextButton)).check(matches(withText(NextButton)));
            }
            // Make sure the step title matches
            onView(withId(R.id.breatheStateTextView)).check(matches(withText(stepsIds[i])));
            // Make sure the step description matches
            onView(withId(R.id.breathingInstructionTextView)).check(matches(withText(instructionIds[i])));
        }
    }
}
