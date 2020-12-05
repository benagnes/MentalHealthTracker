
package com.example.MentalHealthTracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class BreathingExercises extends AppCompatActivity {
    // attributes
    private static final String appBarTitle = "Breathing Exercises";

    TextView breathingStateTextView;
    ImageView breathingImages;
    TextView breathingInfoTextView;
    Button nextButton;

    static int counter = 0;
    int[] imageIds = {R.mipmap.breathingpose2, R.mipmap.sunset, R.mipmap.storm3, R.mipmap.sunset,
            R.mipmap.storm3, R.mipmap.tensemuscle3, R.mipmap.musclerelax3, R.mipmap.sunset,
            R.mipmap.storm3, R.mipmap.peace3};
    int numSteps = imageIds.length;
    String[] stepsIds = {"Breath Focus", "Breathe in...", "Breathe out...", "Breathe in...", "Breathe out...",
            "Breathe in...", "Breathe out...", "Breathe in...", "Breathe out...", "Relaxed?"};
    String[] instructionIds = {"Get into a comfortable position", "Imagine the air is filled with a sense of peace and calm",
            "Imagine the air leaves with your stress and tension", "Notice your abdomen expanding with deep inhalations",
            "Bring awareness to your breath", "Tense your muscles", "Relax your muscles, letting go of all tension",
            "Imagine peace and tranquility", "Let go of stress and anxiety",
            "Hope you feel relaxed and rejuvenated"};

    // methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breathing_exercises);

        breathingStateTextView = findViewById(R.id.breatheStateTextView);
        breathingImages = (ImageView) findViewById(R.id.breathingImageView);
        breathingInfoTextView = findViewById(R.id.breathingInstructionTextView);

        nextButton = findViewById(R.id.nextButton);
        nextButton.setText(R.string.next);
        nextButton.setOnClickListener(v -> {
            breathingImages.setImageResource(imageIds[counter]);
            breathingStateTextView.setText(stepsIds[counter]);
            breathingInfoTextView.setText(instructionIds[counter]);
            counter++;
            if (counter == numSteps) {
                nextButton.setVisibility(View.INVISIBLE);
                counter = 0;
            }
        });

        ActionBar mainActionBar = getSupportActionBar();
        if (mainActionBar != null) {
            mainActionBar.setTitle(appBarTitle);
            mainActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onStop() {
        // When we stop this activity, reset the counter
        counter = 0;
        super.onStop();
    }
}