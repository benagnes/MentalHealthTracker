package com.example.MentalHealthTracker.MediaTests;

import com.example.MentalHealthTracker.timing.MediaTimestamp;
import com.example.MentalHealthTracker.timing.MediaTiming;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class MediaTimingTest {
    @Test
    public void correctly_formats_single_digit_timestamp_HHMMSS() {
        final int requiredHoursInMillis = (int) TimeUnit.HOURS.toMillis(5);
        final int requiredMinutesInMillis = (int) TimeUnit.MINUTES.toMillis(4);
        final int requiredSecondsInMillis = (int) TimeUnit.SECONDS.toMillis(3);
        final int total = requiredHoursInMillis + requiredMinutesInMillis + requiredSecondsInMillis;

        MediaTimestamp t = new MediaTimestamp(total);

        assertEquals("05:04:03", MediaTiming.formatMediaTimestamp(t));
    }

    @Test
    public void correctly_formats_multi_digit_timestamp_HHMMSS() {
        final int requiredHoursInMillis = (int) TimeUnit.HOURS.toMillis(145);
        final int requiredMinutesInMillis = (int) TimeUnit.MINUTES.toMillis(59);
        final int requiredSecondsInMillis = (int) TimeUnit.SECONDS.toMillis(59);
        final int total = requiredHoursInMillis + requiredMinutesInMillis + requiredSecondsInMillis;

        MediaTimestamp t = new MediaTimestamp(total);

        assertEquals("145:59:59", MediaTiming.formatMediaTimestamp(t));
    }

    @Test
    public void correctly_formats_sub_1_hour_timestamps_MMSS() {
        final int requiredHoursInMillis = (int) TimeUnit.HOURS.toMillis(0);
        final int requiredMinutesInMillis = (int) TimeUnit.MINUTES.toMillis(5);
        final int requiredSecondsInMillis = (int) TimeUnit.SECONDS.toMillis(0);
        final int total = requiredHoursInMillis + requiredMinutesInMillis + requiredSecondsInMillis;

        MediaTimestamp t = new MediaTimestamp(total);

        assertEquals("05:00", MediaTiming.formatMediaTimestamp(t));
    }
}
