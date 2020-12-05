package com.example.mentalhealthtracker.meditation;

import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.mentalhealthtracker.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MeditationPlayerTest {
    // The activity intent
    static Intent intent;

    static {
        intent = new Intent(ApplicationProvider.getApplicationContext(), MeditationPlayer.class);
        intent.putExtra(MeditationPlayer.ROUTINE_REF, "0");
    }

    @Rule
    public ActivityScenarioRule<MeditationPlayer> meditationPlayerScenario =
            new ActivityScenarioRule<>(intent);

    @Test
    public void initial_step_loaded() throws InterruptedException {
        // Pause for activity to load
        Thread.sleep(1000);

        // Check to make sure the title description is correct
        onView(withId(R.id.meditationStepTitle)).check(matches(withText("Get Started")));
        // Check to make sure the description is correct
        onView(withId(R.id.meditationStepDescription)).check(matches(withText("Start by sitting down cross-legged, or in an upright chair position. Whichever is most comfortable to you.")));
    }

    @Test
    public void player_transition_automatically() throws InterruptedException {
        Thread.sleep(22 * 1000);

        // Check to make sure the title description is correct
        onView(withId(R.id.meditationStepTitle)).check(matches(withText("The Right Posture")));
        // Check to make sure the description is correct
        onView(withId(R.id.meditationStepDescription)).check(matches(withText("Make sure your back is straight, but keep it relaxed. Align your head and neck with your spine. Put your hands either on your legs (palms down) or on your lap.")));
    }

    @Test
    public void player_transition_skip() {
        // Skip all the steps to the end
        onView(withId(R.id.meditationSkipButton)).perform(click());
        onView(withId(R.id.meditationSkipButton)).perform(click());
        onView(withId(R.id.meditationSkipButton)).perform(click());

        // Check to make sure we're on the end screen
        onView(withId(R.id.meditationStepTitle)).check(matches(withText("Finished")));
        onView(withId(R.id.meditationStepDescription)).check(matches(withText("Your meditation exercise is finished, nice job!")));
    }
}