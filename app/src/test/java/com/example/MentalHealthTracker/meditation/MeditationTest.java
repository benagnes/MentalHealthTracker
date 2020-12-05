package com.example.mentalhealthtracker.meditation;

import com.example.mentalhealthtracker.meditation.db.MeditationRoutine;
import com.example.mentalhealthtracker.meditation.db.MeditationStep;

import org.junit.Test;

import static org.junit.Assert.*;

public class MeditationTest {
    @Test
    public void meditation_routine_setters() {
        MeditationRoutine mr = new MeditationRoutine(0, "0",
                "0");

        mr.setRoutineId(1);
        mr.setRoutineName("1");
        mr.setRoutineDescription("1");

        assertEquals(1, mr.getRoutineId());
        assertEquals("1", mr.getRoutineName());
        assertEquals("1", mr.getRoutineDescription());
    }

    @Test
    public void meditation_routine_getters() {
        MeditationRoutine mr = new MeditationRoutine(0, "0",
                "0");

        assertEquals(0, mr.getRoutineId());
        assertEquals("0", mr.getRoutineName());
        assertEquals("0", mr.getRoutineDescription());
    }

    @Test
    public void meditation_step_setters() {
        MeditationStep step = new MeditationStep(0, 0, 500, "0",
                "0", 0);

        step.setRoutine(1);
        step.setOrder(1);
        step.setTime(1);
        step.setTitle("1");
        step.setDescription("1");
        step.setImage(1);

        assertEquals(1, step.getRoutine());
        assertEquals(1, step.getOrder());
        assertEquals(1, step.getTime());
        assertEquals(1, step.getImage());
        assertEquals("1", step.getTitle());
        assertEquals("1", step.getDescription());
    }

    @Test
    public void meditation_step_getters() {
        MeditationStep step = new MeditationStep(0, 0, 500, "0",
                "0", 0);

        assertEquals(0, step.getRoutine());
        assertEquals(0, step.getOrder());
        assertEquals(500, step.getTime());
        assertEquals(0, step.getImage());
        assertEquals("0", step.getTitle());
        assertEquals("0", step.getDescription());
    }
}