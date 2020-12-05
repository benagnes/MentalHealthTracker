package com.example.mentalhealthtracker.mindfulness;

import com.example.mentalhealthtracker.mindfulness.db.MindfulnessActivity;
import com.example.mentalhealthtracker.mindfulness.db.MindfulnessStep;

import org.junit.Test;

import static org.junit.Assert.*;

public class MindfulnessTest {
    @Test
    public void mindfulness_activity_setters() {
        MindfulnessActivity activity = new MindfulnessActivity(0, "0", "0");

        activity.setActivityId(1);
        activity.setTitle("1t");
        activity.setDescription("1d");

        assertEquals(1, activity.getActivityId());
        assertEquals("1t", activity.getTitle());
        assertEquals("1d", activity.getDescription());
    }

    @Test
    public void mindfulness_activity_getters() {
        MindfulnessActivity activity = new MindfulnessActivity(0, "0t", "0d");

        assertEquals(0, activity.getActivityId());
        assertEquals("0t", activity.getTitle());
        assertEquals("0d", activity.getDescription());
    }

    @Test
    public void mindfulness_step_setters() {
        MindfulnessStep step = new MindfulnessStep(0, 0 , 500, "0t", "0d");

        step.setActivity(1);
        step.setNumber(2);
        step.setTime(0);
        step.setTitle("1t");
        step.setDescription("1d");

        assertEquals(1, step.getActivity());
        assertEquals(2, step.getNumber());
        assertEquals(0, step.getTime());
        assertEquals("1t", step.getTitle());
        assertEquals("1d", step.getDescription());
    }

    @Test
    public void mindfulness_step_getters() {
        MindfulnessStep step = new MindfulnessStep(0, 0 , 500, "0t", "0d");

        assertEquals(0, step.getActivity());
        assertEquals(0, step.getNumber());
        assertEquals(500, step.getTime());
        assertEquals("0t", step.getTitle());
        assertEquals("0d", step.getDescription());
    }
}