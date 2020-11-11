package com.example.mentalhealthtracker;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Mindfulness extends AppCompatActivity {
    // attributes
    private static final String appBarTitle = "Mindfulness";

    // methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_mindfulness);

        ActionBar mainActionBar = getSupportActionBar();

        if (mainActionBar != null) {
            mainActionBar.setTitle(appBarTitle);
            mainActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
