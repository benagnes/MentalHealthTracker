package com.example.mentalhealthtracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mentalhealthtracker.music.PlayMusic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {
    private EditText welcomeMsg;
    private Button doYogaButton;
    private Button playMusicButton;
    private Button moodTrackerButton;
    private Button breathingExButton;
    private Button mindfulnessButton;
    private Button webSupportArticlesButton;
    private Button moreResourcesButton;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set welcome message based on time of day
        welcomeMsg = (EditText) findViewById(R.id.welcomeMsg);
        setWelcomeMsg();

        // Button that links to 'Do Yoga' component
        doYogaButton = (Button) findViewById(R.id.yogaButton);
        doYogaButton.setOnClickListener(v -> openYogaActivity());

        // Button that links to 'Play Music' component
        playMusicButton = (Button) findViewById(R.id.playMusicButton);
        playMusicButton.setOnClickListener(v -> openPlayMusicActivity());

        // Button that links to 'Mood Tracker' component
        moodTrackerButton = (Button) findViewById(R.id.moodTrackerButton);
        moodTrackerButton.setOnClickListener(v -> openMoodTracker());

        // Button that links to 'Breathing Exercises' component
        breathingExButton = (Button) findViewById(R.id.breathingExButton);
        breathingExButton.setOnClickListener(v -> openBreathingExercises());

        // Button that links to 'Mindfulness' component
        mindfulnessButton = (Button) findViewById(R.id.mindfulnessButton);
        mindfulnessButton.setOnClickListener(v -> openMindfulness());

        // Button that links to 'Web Support Articles' component
        webSupportArticlesButton = (Button) findViewById(R.id.webSupportArticlesButton);
        webSupportArticlesButton.setOnClickListener(v -> openWebSupportArticles());

        // Button that links to 'More Resources' component
        moreResourcesButton = (Button) findViewById(R.id.moreResourcesButton);
        moreResourcesButton.setOnClickListener(v -> openMoreResources());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setWelcomeMsg() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
        LocalDateTime currentTime = LocalDateTime.now();
        String hourofDay = currentTime.format(formatter);
        int hour = Integer.parseInt(hourofDay);
        if (hour >= 4 && hour < 12 ) {
            welcomeMsg.setText(R.string.welcomeMessage_1);
        }
        else if (hour >= 12 && hour < 18) {
            welcomeMsg.setText(R.string.welcomeMessage_2);
        }
        else {
            welcomeMsg.setText(R.string.welcomeMessage_3);
        }
    }

    public void openYogaActivity() {
        Intent intent = new Intent(this, Yoga.class);
        startActivity(intent);
    }

    public void openPlayMusicActivity() {
        Intent intent = new Intent(this, PlayMusic.class);
        startActivity(intent);
    }

    public void openMoodTracker() {
        Intent intent = new Intent(this, MoodTracker.class);
        startActivity(intent);
    }

    public void openBreathingExercises() {
        Intent intent = new Intent(this, BreathingExercises.class);
        startActivity(intent);
    }

    public void openMindfulness() {
        Intent intent = new Intent(this, Mindfulness.class);
        startActivity(intent);
    }

    public void openWebSupportArticles() {
        Intent intent = new Intent(this, WebSupportArticles.class);
        startActivity(intent);
    }

    public void openMoreResources() {
        Intent intent = new Intent(this, MoreResources.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}