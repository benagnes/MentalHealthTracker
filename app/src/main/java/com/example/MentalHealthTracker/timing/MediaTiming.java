package com.example.MentalHealthTracker.timing;

import java.util.Locale;

/**
 * Handful of utilities for dealing with media timing across the app
 */
public abstract class MediaTiming {
    private static final String mediaTimeFormat_HHMMSS = "%02d:%02d:%02d";
    private static final String mediaTimeFormat_MMSS = "%02d:%02d";

    /**
     * Formats the media timestamp with the hours, minutes, and seconds. The hours property will be
     * ignored if hours is not above 0. Minutes and seconds will always be present.
     * @param timestamp The timestamp of the media
     * @return A formatted timestamp String
     */
    public static String formatMediaTimestamp(MediaTimestamp timestamp) {
        if (timestamp.getHours() > 0)
            return String.format(Locale.getDefault(), mediaTimeFormat_HHMMSS,
                    timestamp.getHours(), timestamp.getMinutes(), timestamp.getSeconds());
        else
            return String.format(Locale.getDefault(),mediaTimeFormat_MMSS,
                    timestamp.getMinutes(), timestamp.getSeconds());
    }
}
