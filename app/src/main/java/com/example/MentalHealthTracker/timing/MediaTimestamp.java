package com.example.MentalHealthTracker.timing;

/**
 * MediaTimestamp is responsible for holding a single timestamp broken down into
 * the hours, minutes, and seconds components.
 *
 * It holds precision up to the second. The millisecond attribute is simply used as a reference
 * of the original value.
 */
public class MediaTimestamp {
    private int milliseconds;
    private int hours;
    private int minutes;
    private int seconds;

    public MediaTimestamp(int length) {
        setTimestamp(length);
    }

    /**
     * Sets the timestamp attributes based on the length
     * @param length The time in milliseconds (ms)
     */
    public void setTimestamp(int length) {
        if (length <= 0) {
            this.milliseconds = 0;
            this.hours = 0;
            this.minutes = 0;
            this.seconds = 0;
        } else {
            this.milliseconds = length;
            this.seconds = (length / 1000) % 60 ;
            this.minutes = ((length / (1000 * 60)) % 60);
            this.hours = (length / (1000 * 60 * 60));
        }
    }

    /**
     * Subtracts the timestamps with the rounded seconds value
     * @param timestamp The timestamp to subtract
     * @return A new timestamp
     */
    public MediaTimestamp subtract(MediaTimestamp timestamp) {
        if (timestamp == null)
            return this;

        return new MediaTimestamp(this.milliseconds - timestamp.getMilliseconds());
    }

    /**
     * Adds the timestamps with the rounded second value
     * @param timestamp The timestamp to add
     * @return A new timestamp
     */
    public MediaTimestamp add(MediaTimestamp timestamp) {
        if (timestamp == null)
            return this;

        return new MediaTimestamp((this.milliseconds + timestamp.getMilliseconds()));
    }

    public int getTotalAsHours() {
        return this.milliseconds / (1000 * 60 * 60);
    }

    public int getTotalAsMinutes() {
        return this.milliseconds / (1000 * 60);
    }

    public int getTotalAsSeconds() {
        return this.milliseconds / 1000;
    }

    public int getMilliseconds() {
        return this.milliseconds;
    }

    public int getHours() {
        return this.hours;
    }

    public int getMinutes() {
        return this.minutes;
    }

    public int getSeconds() {
        return this.seconds;
    }
}
