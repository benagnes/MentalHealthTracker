
package com.example.mentalhealthtracker;

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

















//______________________________________________________________________________________
//package com.example.mentalhealthtracker;
//
//        import android.media.MediaPlayer;
//        import android.os.Bundle;
//        import android.os.CountDownTimer;
//        import android.view.View;
//        import android.widget.Button;
//        import android.widget.ImageView;
//        import android.widget.TextView;
//
//        import androidx.appcompat.app.ActionBar;
//        import androidx.appcompat.app.AppCompatActivity;
//
//        import java.util.Locale;
//
//public class BreathingExercises extends AppCompatActivity {
//    // attributes
//    private static final String appBarTitle = "Breathing Exercises";
//
////    TextView breathingTextView;
////    TextView breatheInOutTextView;
////    TextView timerTextView;
////    Boolean counterIsActive = false;
////    Button goButton;
////    CountDownTimer countDownTimer;
////    ImageView image;
////
////    static int counter = 0;
////    int[] imageIds = {R.mipmap.bigtoepose, R.mipmap.childpose1, R.mipmap.childpose1};
////    int numPoses = imageIds.length;
////    String[] poseNames = {"Big Toe Pose", "Child's Pose", "Child's Pose"};
////
////    // methods
////    public void resetTimer() {
////        timerTextView.setText(R.string.yogaInitialTimer);
////        goButton.setText(R.string.yogaBeginText);
////        counterIsActive = false;
////
////        counter++;
////
////        if (counter < numPoses) {
////            countDownTimer.start();
////        }
////        else {
////            countDownTimer.cancel();
////            breatheInOutTextView.setText(R.string.yogaCompletionMessage);
////            image.setVisibility(View.INVISIBLE);
////            timerTextView.setVisibility(View.INVISIBLE);
////
////            // Reset the counter when we're at the end
////            counter = 0;
////        }
////    }
////
////
////    public void buttonClicked(View view) {
////        counterIsActive = true;
////        goButton.setVisibility(View.INVISIBLE);
////
////        image.setImageResource(imageIds[counter]);
////        breatheInOutTextView.setText(poseNames[counter]);
////
////        counter = 1;
////
////        countDownTimer = new CountDownTimer(45000, 1000) {
////
////            @Override
////            public void onTick(long l) {
////                updateTimer((int) l / 1000);
////            }
////
////            @Override
////            public void onFinish() {
////                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.beep);
////                mediaPlayer.start();
////                image.setImageResource(imageIds[counter]);
////                breatheInOutTextView.setText(poseNames[counter]);
////                resetTimer();
////            }
////        }.start();
////    }
////
////
////    public void updateTimer(int secondsLeft) {
////        int minutes = secondsLeft / 60;
////        int seconds = secondsLeft - (minutes * 60);
////
////        String secondString = Integer.toString(seconds);
////
////        if (seconds <= 9) {
////            secondString = "0" + secondString;
////        }
////
////        timerTextView.setText(String.format(Locale.getDefault(), "%d:%s", minutes,
////                secondString));
////    }
////
//
//    // methods
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_breathing_exercises);
//
////        breathingTextView = findViewById(R.id.breathingTextView);
////        breathingTextView.setText(R.string.yogaPageTitle);
////        breatheInOutTextView = findViewById(R.id.breatheInOutTextView);
////        timerTextView = findViewById(R.id.countdownTextView);
////        goButton = findViewById(R.id.startButton);
////        image = (ImageView) findViewById(R.id.yogaImageView);
//
//        ActionBar mainActionBar = getSupportActionBar();
//
//        if (mainActionBar != null) {
//            mainActionBar.setTitle(appBarTitle);
//            mainActionBar.setDisplayHomeAsUpEnabled(true);
//        }
//    }
//
//
////    @Override
////    protected void onStop() {
////        // When we stop this activity, cancel the timer, and reset the counter
////        if (countDownTimer != null)
////            countDownTimer.cancel();
////        counter = 0;
////
////        super.onStop();
////    }
//
//}







