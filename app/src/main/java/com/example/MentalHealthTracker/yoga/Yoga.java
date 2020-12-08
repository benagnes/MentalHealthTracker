package com.example.MentalHealthTracker.yoga;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.MentalHealthTracker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class Yoga extends AppCompatActivity {
    // attributes
    private static final String appBarTitle = "Yoga";
    TextView yogaTextView;
    TextView poseNameTextView;
    TextView timerTextView;
    Boolean counterIsActive = false;
    Button goButton;
    CountDownTimer countDownTimer;
    ImageView image;
    TextView initialCountdownTimerTextView;
    CountDownTimer initialCountDownTimer;
    TextView getReadyTextView;
    FloatingActionButton mSkipNextButton;
    MediaPlayer mediaPlayer1;

    static int initialCounter = 3;

    static int counter = 0;
    int[] imageIds = {R.mipmap.bigtoepose, R.mipmap.intensesidestretch2, R.mipmap.eaglepose3, R.mipmap.tree2,
            R.mipmap.extendedtriangle2, R.mipmap.highlunge3, R.mipmap.warrior, R.mipmap.lordofdance3,
            R.mipmap.downwardsdog, R.mipmap.plank, R.mipmap.cobra, R.mipmap.reclininglegstretch,
            R.mipmap.childpose1, R.mipmap.childpose1};
    int numPoses = imageIds.length;
    String[] poseNames = {"Big Toe Pose", "Intense Side Stretch", "Eagle", "Tree",
            "Extended Triangle", "High Lunge", "Warrior II", "Lord of Dance", "Downwards Dog",
            "Plank", "Cobra", "Reclining Leg Stretch", "Child's Pose", "Child's Pose"};

    // methods
    public void resetTimer() {
        timerTextView.setText(R.string.yogaInitialTimer);
        goButton.setText(R.string.yogaBeginText);
        counterIsActive = false;

        counter++;

        if (counter < numPoses) {
            countDownTimer.start();
        }
        else {
            countDownTimer.cancel();
            poseNameTextView.setText(R.string.yogaCompletionMessage);
            image.setVisibility(View.INVISIBLE);
            timerTextView.setVisibility(View.INVISIBLE);
            mSkipNextButton.setVisibility(View.INVISIBLE);
            goButton.setVisibility(View.VISIBLE);

            // Reset the counter when we're at the end
            counter = 0;

            if (mediaPlayer1 != null) {
                mediaPlayer1.release();
            }
        }
    }

    public void startYoga() {
        image.setVisibility(View.VISIBLE);
        timerTextView.setVisibility(View.VISIBLE);
        poseNameTextView.setVisibility(View.VISIBLE);
        getReadyTextView.setVisibility(View.INVISIBLE);
        initialCountdownTimerTextView.setVisibility(View.INVISIBLE);
        mSkipNextButton.setVisibility(View.VISIBLE);
        poseNameTextView.setVisibility(View.VISIBLE);

        counterIsActive = true;
        goButton.setVisibility(View.INVISIBLE);

        image.setImageResource(imageIds[counter]);
        poseNameTextView.setText(poseNames[counter]);

        counter = 1;

        countDownTimer = new CountDownTimer(45000, 1000) {

            @Override
            public void onTick(long l) {
                updateTimer((int) l / 1000);
            }

            @Override
            public void onFinish() {
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.beep);
                mediaPlayer.start();
                image.setImageResource(imageIds[counter]);
                poseNameTextView.setText(poseNames[counter]);
                resetTimer();
            }
        }.start();

    }

    public void buttonClicked() {
        getReadyTextView.setText(R.string.yogaGetReadyText);
        getReadyTextView.setVisibility(View.VISIBLE);
        initialCountdownTimerTextView.setVisibility(View.VISIBLE);
        poseNameTextView.setVisibility(View.INVISIBLE);

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
                startYoga();
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

        timerTextView.setText(String.format(Locale.getDefault(), "%d:%s", minutes,
                secondString));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_yoga);

        yogaTextView = findViewById(R.id.yogaTextView);
        yogaTextView.setText(R.string.yogaPageTitle);
        poseNameTextView = findViewById(R.id.poseNameTextView);
        timerTextView = findViewById(R.id.countdownTextView);
        image = findViewById(R.id.yogaImageView);
        initialCountdownTimerTextView = findViewById(R.id.initialCountdownTextView);
        getReadyTextView = findViewById(R.id.getReadyTextView);

        goButton = findViewById(R.id.startButton);
        goButton.setOnClickListener(v -> buttonClicked());

        mSkipNextButton = findViewById(R.id.mStepSkipButton);
        mSkipNextButton.setVisibility(View.INVISIBLE);
        mSkipNextButton.setOnClickListener((l) -> {
            countDownTimer.cancel();
            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.beep);
            mediaPlayer.start();
            image.setImageResource(imageIds[counter]);
            poseNameTextView.setText(poseNames[counter]);
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
        if (countDownTimer != null)
            countDownTimer.cancel();
        counter = 0;

        if (mediaPlayer1 != null) {
            mediaPlayer1.release();
        }

        super.onStop();
    }
}

