package com.example.mentalhealthtracker;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class BreathingExercises extends AppCompatActivity {
    // attributes
    private static final String appBarTitle = "Breathing Exercises";

    // methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breathing_exercises);

        ActionBar mainActionBar = getSupportActionBar();

        if (mainActionBar != null) {
            mainActionBar.setTitle(appBarTitle);
            mainActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}