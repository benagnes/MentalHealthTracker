package com.example.MentalHealthTracker.WebSupportArticles;

import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.MentalHealthTracker.R;
import com.example.MentalHealthTracker.meditation.MeditationPlayer;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class WebSupportArticleDisplayTest {
    // The activity intent
    static Intent intent;

    private static final String TEST_ARTICLE_TITLE = "Test title";
    private static final String TEST_ARTICLE_DESCRIPTION = "Test description";

    static {
        intent = new Intent(ApplicationProvider.getApplicationContext(), WebSupportArticleDisplay.class);
        intent.putExtra(WebSupportArticleDisplay.ARTICLE_CONTENT_REF, "0");
        intent.putExtra(WebSupportArticleDisplay.ARTICLE_TITLE_REF, TEST_ARTICLE_TITLE);
        intent.putExtra(WebSupportArticleDisplay.ARTICLE_CONTENT_REF, TEST_ARTICLE_DESCRIPTION);
    }

    @Rule
    public ActivityScenarioRule<WebSupportArticleDisplay> articleDisplay =
            new ActivityScenarioRule<>(intent);

    @Test
    public void title_displayed() {
        onView(withId(R.id.articleFullTitle)).check(matches(withText(TEST_ARTICLE_TITLE)));
    }

    @Test
    public void content_displayed() {
        onView(withId(R.id.articleFullContent)).check(matches(withText(TEST_ARTICLE_DESCRIPTION)));
    }
}