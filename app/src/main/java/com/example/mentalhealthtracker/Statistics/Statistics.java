package com.example.mentalhealthtracker.Statistics;

// Object to track the usage of an activity
public class Statistics {
    // attributes
    private int ID;
    private int numTimesUsed;

    //methods
    public Statistics() {}

    public Statistics(int ID, int numTimesUsed) {
        this.ID = ID;
        this.numTimesUsed = numTimesUsed;
    }

    public int getID() { return ID; }

    public void setID(int ID) { this.ID = ID; }

    public int getNumTimesUsed() {
        return numTimesUsed;
    }

    public void setNumTimesUsed(int numTimesUsed) {
        this.numTimesUsed = numTimesUsed;
    }
}
