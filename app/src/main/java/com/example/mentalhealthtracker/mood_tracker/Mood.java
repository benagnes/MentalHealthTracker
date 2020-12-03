package com.example.mentalhealthtracker.mood_tracker;

// Mood object to store user moods in database
public class Mood {
    // attributes
    private int ID;
    private String date;
    private String mood;
    private String energy;
    private String social_meter;
    private String productivity;

    // methods
    public Mood() { }

    public Mood(int ID, String date, String mood, String energy, String social_meter, String productivity){
        this.ID = ID;
        this.date = date;
        this.mood = mood;
        this.energy = energy;
        this.social_meter = social_meter;
        this.productivity = productivity;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getEnergy() {
        return energy;
    }

    public void setEnergy(String energy) {
        this.energy = energy;
    }

    public String getSocial_meter() {
        return social_meter;
    }

    public void setSocial_meter(String social_meter) {
        this.social_meter = social_meter;
    }

    public String getProductivity() {
        return productivity;
    }

    public void setProductivity(String productivity) {
        this.productivity = productivity;
    }
}
