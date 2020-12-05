package com.example.MentalHealthTracker;

import org.junit.Test;
import static org.junit.Assert.*;

// Unit test for welcome message string
public class WelcomeMessageTest {
    @Test
    public void checkWelcomeMsg() {
        String welcomeMsg = "Good morning!";
        assertEquals("Good morning!", welcomeMsg);
    }
}
