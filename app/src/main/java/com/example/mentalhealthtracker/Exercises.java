package com.example.mentalhealthtracker;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class Exercises extends AppCompatActivity {
    // attributes
    private static final String appBarTitle = "Exercises";

    TextView exerciseTextView;
    TextView exerciseNameTextView;
    TextView countdownExerciseTimerTextView4;
    Boolean exerciseCounterIsActive = false;
    Button exerciseGoButton;
    CountDownTimer countDownExerciseTimer;
    ImageView exerciseImageView;

    TextView initialCountdownTimerTextView;
    CountDownTimer initialCountDownTimer;
    TextView getReadyTextView;
    FloatingActionButton mSkipNextButton;

    static int initialCounter = 3;

    static int exerciseCounter = 0;
    int[] exerciseImageIds = {R.mipmap.jumpingjacks2, R.mipmap.lunge2, R.mipmap.squat3, R.mipmap.pushups3,
            R.mipmap.buttkicks3, R.mipmap.highknees3, R.mipmap.situps, R.mipmap.plank,
            R.mipmap.burpee, R.mipmap.sidelunge3, R.mipmap.round21, R.mipmap.jumpingjacks2, R.mipmap.lunge2,
            R.mipmap.squat3, R.mipmap.pushups3, R.mipmap.buttkicks3, R.mipmap.highknees3,
            R.mipmap.situps, R.mipmap.plank, R.mipmap.burpee, R.mipmap.sidelunge3, R.mipmap.sidelunge3};

    int numExercisePoses = exerciseImageIds.length;
    String[] exerciseNames = {"Jumping Jacks", "Lunges", "Squats", "Push Ups",
            "Butt Kickers", "High Knee Running", "Sit Ups", "Plank", "Burpees",
            "Side Lunges", " ", "Jumping Jacks", "Lunges", "Squats", "Push Ups",
            "Butt Kickers", "High Knee Running", "Sit Ups", "Plank", "Burpees",
            "Side Lunges", "Side Lunges"};

    // methods
    public void resetTimer() {
        countdownExerciseTimerTextView4.setText(R.string.exerciseInitialTimer);
        exerciseGoButton.setText(R.string.exerciseBeginText);
        exerciseCounterIsActive = false;

        exerciseCounter++;

        if (exerciseCounter < numExercisePoses) {
            countDownExerciseTimer.start();
        }
        else {
            countDownExerciseTimer.cancel();
            mSkipNextButton.setVisibility(View.INVISIBLE);
            exerciseNameTextView.setText(R.string.exerciseCompletionMessage);
            exerciseImageView.setVisibility(View.INVISIBLE);
            countdownExerciseTimerTextView4.setVisibility(View.INVISIBLE);

            // Reset the counter when we're at the end
            exerciseCounter = 0;
        }
    }

    public void startExercise(View view) {

        mSkipNextButton.setVisibility(View.VISIBLE);

        exerciseCounterIsActive = true;
        exerciseGoButton.setVisibility(View.INVISIBLE);

        exerciseImageView.setImageResource(exerciseImageIds[exerciseCounter]);
        exerciseNameTextView.setText(exerciseNames[exerciseCounter]);

        exerciseCounter = 1;

        countDownExerciseTimer = new CountDownTimer(30000, 1000) {

            @Override
            public void onTick(long l) {
                updateTimer((int) l / 1000);
            }

            @Override
            public void onFinish() {
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.beep);
                mediaPlayer.start();
                exerciseImageView.setImageResource(exerciseImageIds[exerciseCounter]);
                exerciseNameTextView.setText(exerciseNames[exerciseCounter]);
                resetTimer();
            }
        }.start();

    }


    public void exerciseButtonClicked(View view) {

        mSkipNextButton.setVisibility(View.INVISIBLE);

        getReadyTextView.setText(R.string.getReady);

        initialCountDownTimer = new CountDownTimer(4000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                initialCountdownTimerTextView.setText(String.valueOf(initialCounter));
                initialCounter--;
            }
            @Override
            public void onFinish() {
                //initialCountdownTimerTextView.setText("Finished");
                MediaPlayer mediaPlayer1 = MediaPlayer.create(getApplicationContext(), R.raw.beep);
                mediaPlayer1.start();
                getReadyTextView.setVisibility(View.INVISIBLE);
                initialCountdownTimerTextView.setVisibility(View.INVISIBLE);
                initialCounter = 3;
                startExercise(view);
            }
        }.start();

    }


    public void updateTimer(int secondsLeft) {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);

        String secondString = Integer.toString(seconds);

        if (seconds <= 9) {
            secondString = "0" + secondString;
        }

        countdownExerciseTimerTextView4.setText(String.format(Locale.getDefault(), "%d:%s", minutes,
                secondString));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        exerciseTextView = findViewById(R.id.exerciseTextView);
        exerciseTextView.setText(R.string.exercisePageTitle);
        exerciseNameTextView = findViewById(R.id.exerciseNameTextView);
        countdownExerciseTimerTextView4 = findViewById(R.id.countdownExerciseTextView4);
        exerciseGoButton = findViewById(R.id.exerciseStartButton);
        exerciseImageView = findViewById(R.id.exerciseImageView);
        initialCountdownTimerTextView = findViewById(R.id.initialCountdownTextView);
        getReadyTextView = findViewById(R.id.getReadyTextView);

        mSkipNextButton = findViewById(R.id.mStepSkipButton);

        mSkipNextButton.setVisibility(View.INVISIBLE);

        mSkipNextButton.setOnClickListener((l) -> {
            countDownExerciseTimer.cancel();
            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.beep);
            mediaPlayer.start();
            exerciseImageView.setImageResource(exerciseImageIds[exerciseCounter]);
            exerciseNameTextView.setText(exerciseNames[exerciseCounter]);
            resetTimer();
        });

        ActionBar mainActionBar = getSupportActionBar();

        if (mainActionBar != null) {
            mainActionBar.setTitle(appBarTitle);
            mainActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onStop() {
        // When we stop this activity, cancel the timer, and reset the counter
        if (countDownExerciseTimer != null)
            countDownExerciseTimer.cancel();
        exerciseCounter = 0;

        super.onStop();
    }
}