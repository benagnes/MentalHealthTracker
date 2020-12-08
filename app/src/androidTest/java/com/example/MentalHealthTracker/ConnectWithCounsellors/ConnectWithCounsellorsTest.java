package com.example.MentalHealthTracker.ConnectWithCounsellors;

import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.MentalHealthTracker.connectwithcounsellors.ConnectWithCounsellors;
import com.example.MentalHealthTracker.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

// Unit Tests for 'Connect With Counsellors' Activity
@RunWith(AndroidJUnit4.class)
public class ConnectWithCounsellorsTest {
    static Intent intent;

    static {
        intent = new Intent(ApplicationProvider.getApplicationContext(), ConnectWithCounsellors.class);
        intent.putExtra(ConnectWithCounsellors.ACTIVITY_REF, "0");
    }

    @Rule
    public ActivityScenarioRule<ConnectWithCounsellors> interactiveScenario
            = new ActivityScenarioRule<>(intent);

    // Test initial state is correct when loading activity
    @Test
    public void test_DataLoadsonStart() {
        final String connectCounsellorsInfoText = "Information on and hotlines for Counsellors and Mental Health support";
        final String cmha = "Canadian Mental Health Association";
        final String here2talk = "HERE2TALK";
        final String crisisLine = "Crisis Line and Chat";
        final String youthspace = "YOUTHSPACE";

        // Make sure welcome / information message matches
        onView(withId(R.id.connectCounsellorsInfoTextView)).check(matches(withText(connectCounsellorsInfoText)));

        // Make sure button texts are correct
        onView(withId(R.id.cmhaButton)).check(matches(withText(cmha)));

        onView(withId(R.id.here2talkButton)).check(matches(withText(here2talk)));

        onView(withId(R.id.crisisLineButton)).check(matches(withText(crisisLine)));

        onView(withId(R.id.youthspaceButton)).check(matches(withText(youthspace)));
    }

    // Test Canadian mental health assoc. button works, text is loaded correctly, and back button works
    @Test
    public void test_CanadianMentalHealthAssociation() {
        final String connectCounsellorsInfoText = "Information on and hotlines for Counsellors and Mental Health support";
        final String cmhaResourceInfo = "Visit www.cmha.bc.ca or call 1-800-555-8222 " +
                "for information and community resources on mental health or any mental illness. " +
                "A free program called Bounce Back helps adults experiencing mild to moderate " +
                "depression, stress, or anxiety, using self-help materials and coaching";
        onView(withId(R.id.cmhaButton)).perform(click());

        onView(withId(R.id.resourceInfoTextView)).check(matches(withText(cmhaResourceInfo)));

        onView(withId(R.id.backButton)).perform(click());

        // Make sure welcome / information message matches
        onView(withId(R.id.connectCounsellorsInfoTextView)).check(matches(withText(connectCounsellorsInfoText)));
    }

    // Test HERE2TALK button works, text is loaded correctly, and back button works
    @Test
    public void test_HERE2TALK() {
        final String connectCounsellorsInfoText = "Information on and hotlines for Counsellors and Mental Health support";
        final String here2talkResourceInfo = "Here2Talk connects students with mental health support. " +
                "All students currently registered in a B.C. post-secondary institution have access to free, " +
                "confidential counselling and community referral services, conveniently available 24/7 " +
                "via app, phone and web. Call Toll Free 1.877.857.3397 or Direct 604.642.5212 " +
                "The Here2Talk app is available for Apple and Android devices";
        onView(withId(R.id.here2talkButton)).perform(click());

        onView(withId(R.id.resourceInfoTextView)).check(matches(withText(here2talkResourceInfo)));

        onView(withId(R.id.backButton)).perform(click());

        // Make sure welcome / information message matches
        onView(withId(R.id.connectCounsellorsInfoTextView)).check(matches(withText(connectCounsellorsInfoText)));
    }

    // Test Crisis Line and Chat button works, text is loaded correctly, and back button works
    @Test
    public void test_CrisisLineandChat() {
        final String connectCounsellorsInfoText = "Information on and hotlines for Counsellors and Mental Health support";
        final String crisisLineResourceInfo = "BCs Distress Phone Services 1-800-784-2433 provide " +
                "confidential, non-judgmental, free emotional support, 24 hours a day, 7 days a week, " +
                "in over 140 languages. The Crisis Centre offers one-on-one online chat noon-1am daily " +
                "and 24/7 access to resources through the Youth in BC program";
        onView(withId(R.id.crisisLineButton)).perform(click());

        onView(withId(R.id.resourceInfoTextView)).check(matches(withText(crisisLineResourceInfo)));

        onView(withId(R.id.backButton)).perform(click());

        // Make sure welcome / information message matches
        onView(withId(R.id.connectCounsellorsInfoTextView)).check(matches(withText(connectCounsellorsInfoText)));
    }

    // Test Youthspace button works, text is loaded correctly, and back button works
    @Test
    public void test_YOUTHSPACE() {
        final String connectCounsellorsInfoText = "Information on and hotlines for Counsellors and Mental Health support";
        final String youthspaceResourceInfo = "Youthspace.ca provides emotional support and crisis " +
                "intervention service for young people under 30. Youthspace.ca is made up of a community " +
                "of volunteers who are here to support you - whatever you are going through. " +
                "Chat online and text (778-783-0177) are available from 6:00pm to midnight every day";
        onView(withId(R.id.youthspaceButton)).perform(click());

        onView(withId(R.id.resourceInfoTextView)).check(matches(withText(youthspaceResourceInfo)));

        onView(withId(R.id.backButton)).perform(click());

        // Make sure welcome / information message matches
        onView(withId(R.id.connectCounsellorsInfoTextView)).check(matches(withText(connectCounsellorsInfoText)));
    }
}
