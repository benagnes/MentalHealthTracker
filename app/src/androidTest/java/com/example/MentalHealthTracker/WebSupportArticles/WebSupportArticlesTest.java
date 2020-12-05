package com.example.MentalHealthTracker.WebSupportArticles;

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
public class WebSupportArticlesTest {
    private static final int CATEGORIES_LENGTH = 5;

    @Rule
    public ActivityScenarioRule<WebSupportArticles> mindfulnessScenario
            = new ActivityScenarioRule<>(WebSupportArticles.class);

    @Test
    public void support_articles_categories_load() throws InterruptedException {
        Thread.sleep(1000);
        onView(withId(R.id.webSupportCategories)).check(
                new RecyclerViewItemCountAssertion(CATEGORIES_LENGTH)
        );
    }
}