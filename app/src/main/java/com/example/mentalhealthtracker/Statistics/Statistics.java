package com.example.mentalhealthtracker.Statistics;

// Object to track the usage of an activity
public class Statistics {
    // attributes
    private String Resource;
    private int numTimesUsed;

    //methods
    public Statistics() {}

    public Statistics(String Resource, int numTimesUsed) {
        this.Resource = Resource;
        this.numTimesUsed = numTimesUsed;
    }

    public String getResource() {
        return Resource;
    }

    public void setResource(String resource) {
        Resource = resource;
    }

    public int getNumTimesUsed() {
        return numTimesUsed;
    }

    public void setNumTimesUsed(int numTimesUsed) {
        this.numTimesUsed = numTimesUsed;
    }
}
