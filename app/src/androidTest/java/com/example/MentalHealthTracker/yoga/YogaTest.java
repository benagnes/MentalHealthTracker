package com.example.MentalHealthTracker.yoga;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.MentalHealthTracker.R;
import com.example.MentalHealthTracker.Yoga;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class YogaTest {
    @Rule
    public ActivityScenarioRule<Yoga> yogaScenario =
            new ActivityScenarioRule<>(Yoga.class);

    @Test
    public void begin_start_button_visible() throws InterruptedException {
        Thread.sleep(2000);

        // Make sure the start button is visible
        onView(ViewMatchers.withId(R.id.startButton)).check(matches(isDisplayed()));

        // Make sure the following items are not displayed
        onView(withId(R.id.countdownTextView)).check(matches(withText("")));
        onView(withId(R.id.getReadyTextView)).check(matches(withText("")));
        onView(withId(R.id.mStepSkipButton)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void first_pose_appears() throws InterruptedException {
        // Press the start button
        onView(withId(R.id.startButton)).perform(click());

        // Wait until the first pose starts
        Thread.sleep(1000);

        // Make sure the get ready text id displayed
        onView(withId(R.id.getReadyTextView)).check(matches(withText("Get Ready!")));

        // Wait until first pose appears
        Thread.sleep(3000);

        // Make sure the first pose is correct
        onView(withId(R.id.poseNameTextView)).check(matches(withText("Big Toe Pose")));
        // Make sure the skip button is visible
        onView(withId(R.id.mStepSkipButton)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        // Make sure the start button is hidden
        onView(withId(R.id.startButton)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void skip_pose() throws InterruptedException {
        // Press the start button
        onView(withId(R.id.startButton)).perform(click());

        // Wait until first pose appears
        Thread.sleep(4000);

        for (int i = 0; i < 13; i++) {
            onView(withId(R.id.mStepSkipButton)).perform(click());
            Thread.sleep(500);
        }

        // Make sure the finishing text is available
        onView(withId(R.id.poseNameTextView)).check(matches(withText(R.string.yogaCompletionMessage)));
    }

    @Test
    public void skip_through_and_restart() throws InterruptedException {
        // Press the start button
        onView(withId(R.id.startButton)).perform(click());
        // Wait until first pose appears
        Thread.sleep(4000);

        for (int i = 0; i < 13; i++) {
            onView(withId(R.id.mStepSkipButton)).perform(click());
            Thread.sleep(500);
        }

        // Press the start button to start again
        onView(withId(R.id.startButton)).perform(click());
        // Wait until first pose appears
        Thread.sleep(4000);

        // Make sure the first pose is correct
        onView(withId(R.id.poseNameTextView)).check(matches(withText("Big Toe Pose")));
        // Make sure the skip button is visible
        onView(withId(R.id.mStepSkipButton)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        // Make sure the start button is hidden
        onView(withId(R.id.startButton)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }
}