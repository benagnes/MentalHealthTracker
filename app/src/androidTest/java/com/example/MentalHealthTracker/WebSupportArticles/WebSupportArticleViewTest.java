package com.example.MentalHealthTracker.WebSupportArticles;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.MentalHealthTracker.R;
import com.example.MentalHealthTracker.helpers.RecyclerViewItemCountAssertion;
import com.example.MentalHealthTracker.mindfulness.Mindfulness;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class WebSupportArticleViewTest {
    private static final int ARTICLE_COUNT = 2;
    static Intent intent;

    static {
        intent = new Intent(ApplicationProvider.getApplicationContext(), WebSupportArticleView.class);
        intent.putExtra(WebSupportArticleView.CATEGORY_REF, "0");
    }

    @Rule
    public ActivityScenarioRule<WebSupportArticleView> mindfulnessScenario
            = new ActivityScenarioRule<>(intent);

    @Test
    public void data_articles_load() throws InterruptedException {
        Thread.sleep(1000);
        onView(withId(R.id.webSupportArticles)).check(
                new RecyclerViewItemCountAssertion(ARTICLE_COUNT)
        );
    }

    @Test
    public void search_by_title() throws InterruptedException {
        Thread.sleep(1000);

        final String articleTitle = "Sample Article 0";
        final String articleDescription = "Some sample article content";
        final String articleTitleSearchTerm = "     Article 0"; // Note the spaces before

        // Click the search button
        onView(withId(R.id.articleSearch)).perform(click());
        // Start typing
        onView(withId(R.id.search_src_text)).perform(typeText(articleTitleSearchTerm));
        // Close the keyboard
        Espresso.closeSoftKeyboard();

        // Check to make sure only 1 article is now displayed
        onView(withId(R.id.webSupportArticles)).check(
                new RecyclerViewItemCountAssertion(1)
        );
        // Check to make sure the article is displayed
        onView(withId(R.id.webSupportArticleTitle)).check(matches(withText(articleTitle)));
        // Check to make sure the description is displayed
        onView(withId(R.id.webSupportArticleContent)).check(matches(withText(articleDescription)));
    }

    @Test
    public void search_by_description() throws InterruptedException {
        Thread.sleep(1000);

        final String articleTitle = "Sample Article 1";
        final String articleDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum quis risus at nisi varius finib ...";
        final String articleDescriptionSearchTerm = "  amet, consectetur "; // Note the spaces before and after

        // Click the search button
        onView(withId(R.id.articleSearch)).perform(click());
        // Start typing
        onView(withId(R.id.search_src_text)).perform(typeText(articleDescriptionSearchTerm));
        // Close the keyboard
        Espresso.closeSoftKeyboard();

        // Check to make sure only 1 article is now displayed
        onView(withId(R.id.webSupportArticles)).check(
                new RecyclerViewItemCountAssertion(1)
        );
        // Check to make sure the article is displayed
        onView(withId(R.id.webSupportArticleTitle)).check(matches(withText(articleTitle)));
        // Check to make sure the description is displayed
        onView(withId(R.id.webSupportArticleContent)).check(matches(withText(articleDescription)));
    }

    @Test
    public void cancel_search() throws InterruptedException {
        Thread.sleep(1000);
        // Click the search button
        onView(withId(R.id.articleSearch)).perform(click());
        // Start typing
        onView(withId(R.id.search_src_text)).perform(typeText("something"));

        // Close the keyboard
        Espresso.closeSoftKeyboard();
        // Back button to cancel search
        Espresso.pressBack();
        Espresso.pressBack();

        // Make sure both articles are displayed
        onView(withId(R.id.webSupportArticles)).check(
                new RecyclerViewItemCountAssertion(ARTICLE_COUNT)
        );
    }

    @Test
    public void search_multiple_match() throws InterruptedException {
        Thread.sleep(1000);

        // Click the search button
        onView(withId(R.id.articleSearch)).perform(click());
        // Start typing
        onView(withId(R.id.search_src_text)).perform(typeText("Article"));

        // Make sure both articles are displayed
        onView(withId(R.id.webSupportArticles)).check(
                new RecyclerViewItemCountAssertion(ARTICLE_COUNT)
        );
    }
}