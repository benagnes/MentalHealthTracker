package com.example.MentalHealthTracker.breathingexercises;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MentalHealthTracker.R;

public class BreathingExercises extends AppCompatActivity {
    // attributes
    public static final String ACTIVITY_REF = "BREATHING_EXERCISES_ACTIVITY";
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
    private String[] stepsIds;
    private String[] instructionIds;

    public void runBreathingRoutine() {
        breathingImages.setImageResource(imageIds[counter]);
        breathingStateTextView.setText(stepsIds[counter]);
        breathingInfoTextView.setText(instructionIds[counter]);
        if(counter == 0) {
            nextButton.setText(R.string.nextBreathing);
        }
        counter++;
        if (counter == numSteps) {
            nextButton.setText(R.string.startBreathing);
            counter = 0;
        }
    }

    // methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breathing_exercises);

        // Initialize step titles
        stepsIds = new String[10];
        stepsIds[0] = getResources().getString(R.string.breathfocus);
        stepsIds[1] = getResources().getString(R.string.breathin);
        stepsIds[2] = getResources().getString(R.string.breathout);
        stepsIds[3] = getResources().getString(R.string.breathin);
        stepsIds[4] = getResources().getString(R.string.breathout);
        stepsIds[5] = getResources().getString(R.string.breathin);
        stepsIds[6] = getResources().getString(R.string.breathout);
        stepsIds[7] = getResources().getString(R.string.breathin);
        stepsIds[8] = getResources().getString(R.string.breathout);
        stepsIds[9] = getResources().getString(R.string.breathingexercisesComplete);

        // Initialize step instruction
        instructionIds = new String[10];
        instructionIds[0] = getResources().getString(R.string.comfortableposition);
        instructionIds[1] = getResources().getString(R.string.imaginepeacecalm);
        instructionIds[2] = getResources().getString(R.string.leavestresstension);
        instructionIds[3] = getResources().getString(R.string.abdomenexpanding);
        instructionIds[4] = getResources().getString(R.string.awareofbreath);
        instructionIds[5] = getResources().getString(R.string.tightenmuscles);
        instructionIds[6] = getResources().getString(R.string.relaxmuscles);
        instructionIds[7] = getResources().getString(R.string.peacetranequility);
        instructionIds[8] = getResources().getString(R.string.letgoOfstressanxiety);
        instructionIds[9] = getResources().getString(R.string.feelrelaxedrejuvinated);

        breathingStateTextView = findViewById(R.id.breatheStateTextView);
        breathingImages = (ImageView) findViewById(R.id.breathingImageView);
        breathingInfoTextView = findViewById(R.id.breathingInstructionTextView);

        nextButton = findViewById(R.id.nextButton);
        nextButton.setText(R.string.startBreathing);
        nextButton.setOnClickListener(v -> runBreathingRoutine());

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