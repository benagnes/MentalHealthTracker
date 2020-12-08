package com.example.MentalHealthTracker.exercises;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MentalHealthTracker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class Exercises extends AppCompatActivity {
    // attributes
    private static final String appBarTitle = "Exercises";
    public static final String ACTIVITY_REF = "EXERCISES_ACTIVITY";

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
    MediaPlayer mediaPlayer1;

    static int initialCounter = 3;

    static int exerciseCounter = 0;
    int[] exerciseImageIds = {R.mipmap.jumpingjacks2, R.mipmap.lunge2, R.mipmap.squat3, R.mipmap.pushups3,
            R.mipmap.buttkicks3, R.mipmap.highknees3, R.mipmap.situps, R.mipmap.plank,
            R.mipmap.burpee, R.mipmap.sidelunge3, R.mipmap.round21, R.mipmap.jumpingjacks2, R.mipmap.lunge2,
            R.mipmap.squat3, R.mipmap.pushups3, R.mipmap.buttkicks3, R.mipmap.highknees3,
            R.mipmap.situps, R.mipmap.plank, R.mipmap.burpee, R.mipmap.sidelunge3};

    int numExercisePoses = exerciseImageIds.length;
    String[] exerciseNames;

    // methods
    public void resetTimer() {
        countdownExerciseTimerTextView4.setText(R.string.exerciseInitialTimer);
        exerciseCounterIsActive = false;
        exerciseCounter++;

        if (exerciseCounter <= numExercisePoses) {
            countDownExerciseTimer.start();
        }
        else {
            exercisesComplete();
        }
    }

    public void startExercise() {
        mSkipNextButton.setVisibility(View.VISIBLE);
        exerciseCounterIsActive = true;
        exerciseImageView.setVisibility(View.VISIBLE);
        exerciseNameTextView.setVisibility(View.VISIBLE);
        getReadyTextView.setVisibility(View.INVISIBLE);
        initialCountdownTimerTextView.setVisibility(View.INVISIBLE);
        countdownExerciseTimerTextView4.setVisibility(View.VISIBLE);

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
                if (exerciseCounter < numExercisePoses) {
                    exerciseImageView.setImageResource(exerciseImageIds[exerciseCounter]);
                    exerciseNameTextView.setText(exerciseNames[exerciseCounter]);
                    resetTimer();
                }
                else {
                    exercisesComplete();
                }
            }
        }.start();
    }

    public void exerciseButtonClicked() {
        mSkipNextButton.setVisibility(View.INVISIBLE);
        getReadyTextView.setText(R.string.getReady);
        exerciseGoButton.setVisibility(View.INVISIBLE);
        exerciseImageView.setVisibility(View.INVISIBLE);
        exerciseNameTextView.setVisibility(View.INVISIBLE);
        countdownExerciseTimerTextView4.setVisibility(View.INVISIBLE);
        getReadyTextView.setVisibility(View.VISIBLE);
        initialCountdownTimerTextView.setVisibility(View.VISIBLE);

        initialCountDownTimer = new CountDownTimer(4000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                initialCountdownTimerTextView.setText(String.valueOf(initialCounter));
                initialCounter--;
            }
            @Override
            public void onFinish() {
                mediaPlayer1 = MediaPlayer.create(getApplicationContext(), R.raw.beep);
                mediaPlayer1.start();
                getReadyTextView.setVisibility(View.INVISIBLE);
                initialCountdownTimerTextView.setVisibility(View.INVISIBLE);
                initialCounter = 3;
                startExercise();
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

    public void exercisesComplete() {
        countDownExerciseTimer.cancel();
        mSkipNextButton.setVisibility(View.INVISIBLE);
        exerciseNameTextView.setText(R.string.exerciseCompletionMessage);
        exerciseImageView.setVisibility(View.INVISIBLE);
        countdownExerciseTimerTextView4.setVisibility(View.INVISIBLE);
        exerciseGoButton.setVisibility(View.VISIBLE);

        // Reset the counter when we're at the end
        exerciseCounter = 0;

        if (mediaPlayer1 != null) {
            mediaPlayer1.reset();
            mediaPlayer1.release();
        }
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

        exerciseGoButton.setOnClickListener(v -> exerciseButtonClicked());

        exerciseNames = new String[numExercisePoses];
        exerciseNames[0] = getResources().getString(R.string.jumpingjacks);
        exerciseNames[1] = getResources().getString(R.string.lunges);
        exerciseNames[2] = getResources().getString(R.string.squats);
        exerciseNames[3] = getResources().getString(R.string.pushups);
        exerciseNames[4] = getResources().getString(R.string.buttkickers);
        exerciseNames[5] = getResources().getString(R.string.highknees);
        exerciseNames[6] = getResources().getString(R.string.situps);
        exerciseNames[7] = getResources().getString(R.string.plank);
        exerciseNames[8] = getResources().getString(R.string.burpee);
        exerciseNames[9] = getResources().getString(R.string.sidelunges);
        exerciseNames[10] = "";
        exerciseNames[11] = getResources().getString(R.string.jumpingjacks);
        exerciseNames[12] = getResources().getString(R.string.lunges);
        exerciseNames[13] = getResources().getString(R.string.squats);
        exerciseNames[14] = getResources().getString(R.string.pushups);
        exerciseNames[15] = getResources().getString(R.string.buttkickers);
        exerciseNames[16] = getResources().getString(R.string.highknees);
        exerciseNames[17] = getResources().getString(R.string.situps);
        exerciseNames[18] = getResources().getString(R.string.plank);
        exerciseNames[19] = getResources().getString(R.string.burpee);
        exerciseNames[20] = getResources().getString(R.string.sidelunges);

        mSkipNextButton.setVisibility(View.INVISIBLE);
        mSkipNextButton.setOnClickListener(v -> {
            countDownExerciseTimer.cancel();
            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.beep);
            mediaPlayer.start();
            if (exerciseCounter < numExercisePoses) {
                exerciseImageView.setImageResource(exerciseImageIds[exerciseCounter]);
                exerciseNameTextView.setText(exerciseNames[exerciseCounter]);
                resetTimer();
            }
            else {
                exercisesComplete();
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
        // When we stop this activity, cancel the timer, and reset the counter
        if (countDownExerciseTimer != null)
            countDownExerciseTimer.cancel();
        exerciseCounter = 0;

        if (mediaPlayer1 != null) {
            mediaPlayer1.release();
        }

        super.onStop();
    }
}