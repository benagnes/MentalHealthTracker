package com.example.MentalHealthTracker.MediaTests;

import com.example.MentalHealthTracker.timing.MediaTimestamp;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class MediaTimestampTest {
    @Test
    public void accurately_converts_basic_timestamp() {
        final int requiredHoursInMillis = (int) TimeUnit.HOURS.toMillis(120);
        final int requiredMinutesInMillis = (int) TimeUnit.MINUTES.toMillis(59);
        final int requiredSecondsInMillis = (int) TimeUnit.SECONDS.toMillis(58);
        final int total = requiredHoursInMillis + requiredMinutesInMillis + requiredSecondsInMillis;

        MediaTimestamp t = new MediaTimestamp(total);

        assertEquals(120, t.getHours());
        assertEquals(59, t.getMinutes());
        assertEquals(58, t.getSeconds());
    }

    @Test
    public void handles_converting_invalid_timestamp() {
        final int requiredHoursInMillis = (int) TimeUnit.HOURS.toMillis(-5);
        final int requiredMinutesInMillis = (int) TimeUnit.MINUTES.toMillis(-2);
        final int requiredSecondsInMillis = (int) TimeUnit.SECONDS.toMillis(-9);
        final int total = requiredHoursInMillis + requiredMinutesInMillis + requiredSecondsInMillis;

        MediaTimestamp t = new MediaTimestamp(total);

        assertEquals(0, t.getHours());
        assertEquals(0, t.getMinutes());
        assertEquals(0, t.getSeconds());
    }

    @Test
    public void correctly_subtracts_timestamps() {
        final int requiredHoursInMillis = (int) TimeUnit.HOURS.toMillis(5);
        final int requiredMinutesInMillis = (int) TimeUnit.MINUTES.toMillis(10);
        final int requiredSecondsInMillis = (int) TimeUnit.SECONDS.toMillis(5);
        final int total = requiredHoursInMillis + requiredMinutesInMillis + requiredSecondsInMillis;

        final int subtractingHoursInMillis = (int) TimeUnit.HOURS.toMillis(4);
        final int subtractingMinutesInMillis = (int) TimeUnit.MINUTES.toMillis(15);
        final int subtractingSecondsInMillis = (int) TimeUnit.SECONDS.toMillis(25);
        final int subtractingTotal = subtractingHoursInMillis + subtractingMinutesInMillis
                + subtractingSecondsInMillis;

        MediaTimestamp t = new MediaTimestamp(total);
        MediaTimestamp result = t.subtract(new MediaTimestamp(subtractingTotal));

        assertEquals(0, result.getHours());
        assertEquals(54, result.getMinutes());
        assertEquals(40, result.getSeconds());
    }
}
