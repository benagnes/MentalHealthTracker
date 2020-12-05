package com.example.MentalHealthTracker.music;

import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.MentalHealthTracker.R;
import com.example.MentalHealthTracker.helpers.EspressoTestMatchers;
import com.example.MentalHealthTracker.helpers.RecyclerViewItemCountAssertion;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class PlayMusicTest {
    private static final int SONG_LIBRARY_LENGTH = 3;
    private static final long SONG_LIBRARY_LOAD_TIMEOUT = 500;
    // The activity intent
    static Intent intent;

    static {
        intent = new Intent(ApplicationProvider.getApplicationContext(), PlayMusic.class);
    }

    @Rule
    public ActivityScenarioRule<PlayMusic> playMusicScenario = new ActivityScenarioRule<>(intent);

    public void pauseDataLoad() {
        // Wait for the data to load
        try {
            Thread.sleep(SONG_LIBRARY_LOAD_TIMEOUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        playMusicScenario.getScenario().close();
    }

    @Test
    public void song_data_loaded() {
        pauseDataLoad();
        // Check to make sure our 3 songs get loaded
        // Obviously this is a temporary test since the library is static in size
        onView(withId(R.id.songListRecyclerView)).check(
                new RecyclerViewItemCountAssertion(SONG_LIBRARY_LENGTH)
        );
    }

    @Test
    public void player_opened_mp() {
        pauseDataLoad();

        final String chosenSongTitle = "Serenity";
        final String chosenSongArtists = "Man from Mars";

        // Click on the song
        onView(ViewMatchers.withId(R.id.songListRecyclerView))
                .perform(RecyclerViewActions.actionOnItem(
                        allOf(
                                hasDescendant(withText(chosenSongTitle)),
                                hasDescendant(withText(chosenSongArtists))
                        ),
                        click()
                ));
        // Check for the Mini Player display status
        onView(withId(R.id.musicBar)).check(matches(isDisplayed()));

        // Make sure the title is correct
        onView(withId(R.id.miniHeader)).check(matches(withText(chosenSongTitle)));
        // Make sure the artists text is correct
        onView(withId(R.id.miniplayerSubHeader))
                .check(matches(withText(chosenSongArtists)));
        // Validate the pause button is currently paused
        onView(withId(R.id.miniPlayerControlButton))
                .check(matches(EspressoTestMatchers.withDrawable(
                        R.drawable.ic_baseline_play_arrow_24)));
    }

    @Test
    public void player_opened_fp() {
        pauseDataLoad();

        final String chosenSongTitle = "Serenity";
        final String chosenSongArtists = "Man from Mars";
        final String chosenSongStartTime = "00:00";
        final String chosenSongEndTime = "04:24";

        // Click on the song
        onView(ViewMatchers.withId(R.id.songListRecyclerView))
                .perform(RecyclerViewActions.actionOnItem(
                        allOf(
                                hasDescendant(withText(chosenSongTitle)),
                                hasDescendant(withText(chosenSongArtists))
                        ),
                        click()
                ));
        // Now that the Mini Player is opened, maximize the Full Player
        // by clicking on the Mini Player
        onView(withId(R.id.musicBar)).perform(click());
        // Check for the full player being displayed
        onView(withId(R.id.fullMusicPlayer)).check(matches(isDisplayed()));

        // Check the title matches
        onView(withId(R.id.fpSongTitle)).check(matches(withText(chosenSongTitle)));
        // Check the artists match
        onView(withId(R.id.fpSongArtists)).check(matches(withText(chosenSongArtists)));
        // Check the initial play time matches
        onView(withId(R.id.fpPlayTimeText)).check(matches(withText(chosenSongStartTime)));
        // Check the song length matches
        onView(withId(R.id.fpPlayTimeLeftText)).check(matches(withText(chosenSongEndTime)));
    }

    @Test
    public void player_action_pause_mp() {
        pauseDataLoad();

        // Click on the first item
        onView(withId(R.id.songListRecyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // Click the play button twice, once to play, once to pause
        onView(withId(R.id.miniPlayerControlButton)).perform(click());
        onView(withId(R.id.miniPlayerControlButton)).perform(click());
        // Make sure the button changes to a pause button
        onView(withId(R.id.miniPlayerControlButton))
                .check(matches(EspressoTestMatchers.withDrawable(
                        R.drawable.ic_baseline_play_arrow_24)));
    }

    @Test
    public void player_action_play_mp() {
        pauseDataLoad();

        // Click on the first item
        onView(withId(R.id.songListRecyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // Click the play button
        onView(withId(R.id.miniPlayerControlButton)).perform(click());
        // Make sure the button changes to a pause button
        onView(withId(R.id.miniPlayerControlButton))
                .check(matches(EspressoTestMatchers.withDrawable(
                        R.drawable.ic_baseline_pause_24)));
    }

    @Test
    public void player_action_skip_forward_mp() {
        pauseDataLoad();

        final String chosenSongTitle = "Serenity";
        final String chosenSongArtists = "Man from Mars";

        // Click on the song
        onView(ViewMatchers.withId(R.id.songListRecyclerView))
                .perform(RecyclerViewActions.actionOnItem(
                        allOf(
                                hasDescendant(withText(chosenSongTitle)),
                                hasDescendant(withText(chosenSongArtists))
                        ),
                        click()
                ));
        // Skip forward
        onView(withId(R.id.miniPlayerSkipForwardButton)).perform(click());
        // Make sure the song title changes
        onView(withId(R.id.miniHeader)).check(matches(not(withText(chosenSongTitle))));
        // Make sure the artists changes
        onView(withId(R.id.miniplayerSubHeader)).check(matches(not(withText(chosenSongArtists))));
    }

    @Test
    public void player_action_skip_forward_fp() {
        pauseDataLoad();

        final String chosenSongTitle = "Serenity";
        final String chosenSongArtists = "Man from Mars";

        // Click on the song
        onView(ViewMatchers.withId(R.id.songListRecyclerView))
                .perform(RecyclerViewActions.actionOnItem(
                        allOf(
                                hasDescendant(withText(chosenSongTitle)),
                                hasDescendant(withText(chosenSongArtists))
                        ),
                        click()
                ));
        // Open the full player
        onView(withId(R.id.musicBar)).perform(click());
        // Skip forward
        onView(withId(R.id.fpSkipForward)).perform(click());
        // Make sure the song title changes
        onView(withId(R.id.fpSongTitle)).check(matches(not(withText(chosenSongTitle))));
        // Make sure the artists changes
        onView(withId(R.id.fpSongArtists)).check(matches(not(withText(chosenSongArtists))));
    }

    @Test
    public void player_action_skip_backward_fp() {
        pauseDataLoad();

        final String chosenSongTitle = "Serenity";
        final String chosenSongArtists = "Man from Mars";

        // Click on the song
        onView(ViewMatchers.withId(R.id.songListRecyclerView))
                .perform(RecyclerViewActions.actionOnItem(
                        allOf(
                                hasDescendant(withText(chosenSongTitle)),
                                hasDescendant(withText(chosenSongArtists))
                        ),
                        click()
                ));
        // Open the full player
        onView(withId(R.id.musicBar)).perform(click());
        // Skip forward
        onView(withId(R.id.fpSkipBackward)).perform(click());
        // Make sure the song title changes
        onView(withId(R.id.fpSongTitle)).check(matches(not(withText(chosenSongTitle))));
        // Make sure the artists changes
        onView(withId(R.id.fpSongArtists)).check(matches(not(withText(chosenSongArtists))));
    }

    @Test
    public void player_transition_mp_fp_mp() {
        pauseDataLoad();

        // Click on the first item
        onView(withId(R.id.songListRecyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));
        // Open the full player
        onView(withId(R.id.musicBar)).perform(click());
        // Close the full player
        onView(withId(R.id.fpCloseButton)).perform(click());
        // Make sure full player was closed, and mini player is visible
        onView(withId(R.id.fullMusicPlayer)).check(doesNotExist());
        onView(withId(R.id.musicBar)).check(matches(isDisplayed()));
    }
}