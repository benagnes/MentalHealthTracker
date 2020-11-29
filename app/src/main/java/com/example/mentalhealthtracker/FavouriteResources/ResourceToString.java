package com.example.mentalhealthtracker.FavouriteResources;

import com.example.mentalhealthtracker.R;

public class ResourceToString {
    public static int getNameforFavourite(String resource) {
        if (resource.equals(Resources.Yoga.toString())) {
            return R.string.yoga;
        }
        else if (resource.equals(Resources.PlayMusic.toString())) {
            return R.string.playmusic;
        }
        else if (resource.equals(Resources.MoodTracker.toString())) {
            return R.string.moodtracker;
        }
        else if (resource.equals(Resources.BreathingExercises.toString())) {
            return R.string.breathingexercises;
        }
        else if (resource.equals(Resources.Mindfulness.toString())) {
            return R.string.mindfulness;
        }
        else if (resource.equals(Resources.Exercises.toString())) {
            return R.string.exercises;
        }
        else if (resource.equals(Resources.WebSupportArticles.toString())) {
            return R.string.websupportarticles;
        }
        else if (resource.equals(Resources.Meditation.toString())) {
            return R.string.meditation;
        }
        else if (resource.equals(Resources.ConnectCounsellors.toString())) {
            return R.string.counsellors;
        }
        else if (resource.equals(Resources.ViewStatistics.toString())) {
            return R.string.statistics;
        }
        else {
            return -1;
        }
    }
}
