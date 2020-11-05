package com.example.mentalhealthtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Yoga extends AppCompatActivity {

    TextView yogaTextView;
    TextView poseNameTextView;
    TextView timerTextView;
    Boolean counterIsActive = false;
    Button goButton;
    CountDownTimer countDownTimer;
    ImageView image;

    static int counter = 0;
    int imageIds[] = {R.drawable.downwardsdog, R.drawable.plank, R.drawable.cobra, R.drawable.childpose1, R.drawable.childpose1};
    int numPoses = imageIds.length;
    String poseNames[] = {"Downwards Dog", "Plank", "Cobra", "Child's Pose", "Child's Pose"};

    public void resetTimer() {
        timerTextView.setText("0:10");
        goButton.setText("START");
        counterIsActive = false;

        counter++;

        if (counter < numPoses) {
            countDownTimer.start();
        }
        else {
            countDownTimer.cancel();
            poseNameTextView.setText("GREAT JOB!");
            image.setVisibility(View.INVISIBLE);
            timerTextView.setVisibility(View.INVISIBLE);

            // Reset the counter when we're at the end
            counter = 0;
        }
    }


    public void buttonClicked(View view) {

        counterIsActive = true;
        goButton.setVisibility(View.INVISIBLE);

        image.setImageResource(imageIds[counter]);
        poseNameTextView.setText(poseNames[counter]);

        counter = 1;

        countDownTimer = new CountDownTimer(10000, 1000) {

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


    public void updateTimer(int secondsLeft) {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);

        String secondString = Integer.toString(seconds);

        if (seconds <= 9) {
            secondString = "0" + secondString;
        }

        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_yoga);

        yogaTextView = findViewById(R.id.yogaTextView);
        yogaTextView.setText("YOGA");
        poseNameTextView = findViewById(R.id.poseNameTextView);
        timerTextView = findViewById(R.id.countdownTextView);
        goButton = findViewById(R.id.startButton);
        image = (ImageView) findViewById(R.id.yogaImageView);
    }


    @Override
    protected void onStop() {
        // When we stop this activity, cancel the timer, and reset the counter
        if (countDownTimer != null)
            countDownTimer.cancel();
        counter = 0;

        super.onStop();
    }

}

