package com.example.mentalhealthtracker.mindfulness;

import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.mentalhealthtracker.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MindfulnessInteractiveTest {
    static Intent intent;

    static {
        intent = new Intent(ApplicationProvider.getApplicationContext(), MindfulnessInteractive.class);
        intent.putExtra(MindfulnessInteractive.ACTIVITY_REF, "0");
    }

    @Rule
    public ActivityScenarioRule<MindfulnessInteractive> interactiveScenario
            = new ActivityScenarioRule<>(intent);

    @Test
    public void data_loads_for_first_step() {
        final String stepTitle = "Getting Started";
        final String stepDescription = "Find an open,quiet space in which you can move around comfortably without distractions (inside or outside)";
        final String stepNumber = "1";

        // Make sure the step title matches
        onView(withId(R.id.mStepTitle)).check(matches(withText(stepTitle)));
        // Make sure the step description matches
        onView(withId(R.id.mStepDescription)).check(matches(withText(stepDescription)));
        // Make sure the step matches
        onView(withId(R.id.mStepNumber)).check(matches(withText(stepNumber)));
    }

    @Test
    public void automatic_step_transition() throws InterruptedException {
        Thread.sleep(32 * 1000);

        final String stepTitle = "Find A Path";
        final String stepDescription = "Choose to follow a particular path around your environment";
        final String stepNumber = "2";

        // Make sure the step title matches
        onView(withId(R.id.mStepTitle)).check(matches(withText(stepTitle)));
        // Make sure the step description matches
        onView(withId(R.id.mStepDescription)).check(matches(withText(stepDescription)));
        // Make sure the step matches
        onView(withId(R.id.mStepNumber)).check(matches(withText(stepNumber)));
    }

    @Test
    public void skip_step_transition() {
        // Click to the last step
        onView(withId(R.id.mStepSkipButton)).perform(click());
        onView(withId(R.id.mStepSkipButton)).perform(click());
        onView(withId(R.id.mStepSkipButton)).perform(click());
        onView(withId(R.id.mStepSkipButton)).perform(click());
        onView(withId(R.id.mStepSkipButton)).perform(click());

        final String stepTitle = "Completed!";
        final String stepDescription = "Good job on completing this, hopefully you feel relaxed!";
        final String stepNumber = "6";

        // Make sure the step title matches
        onView(withId(R.id.mStepTitle)).check(matches(withText(stepTitle)));
        // Make sure the step description matches
        onView(withId(R.id.mStepDescription)).check(matches(withText(stepDescription)));
        // Make sure the step matches
        onView(withId(R.id.mStepNumber)).check(matches(withText(stepNumber)));
    }
}