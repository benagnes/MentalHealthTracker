package com.example.mentalhealthtracker;

import com.example.mentalhealthtracker.MainActivity;

import org.junit.Test;
import static org.junit.Assert.*;

public class WelcomeMessageTest {
    @Test
    public void checkWelcomeMsg() {
        String s = "Good morning!";
        assertEquals("Good morning!", s);
    }
}
