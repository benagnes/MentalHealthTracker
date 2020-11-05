package com.example.mentalhealthtracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.mentalhealthtracker.music.PlayMusic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {
    private TextView welcomeMsg;
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
        welcomeMsg = (TextView) findViewById(R.id.welcomeMsg);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
        LocalDateTime currentTime = LocalDateTime.now();
        String hourofDay = currentTime.format(formatter);
        int hour = Integer.parseInt(hourofDay);
        if (hour >= 4 && hour < 12 ) {
            welcomeMsg.setText("Good morning!");
        }
        else if (hour >= 12 && hour < 18) {
            welcomeMsg.setText("Good afternoon!");
        }
        else {
            welcomeMsg.setText("Good evening!");
        }

        // Button that links to 'Do Yoga' component
        doYogaButton = (Button) findViewById(R.id.yogaButton);
        doYogaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openYogaActivity();
            }
        });

        // Button that links to 'Play Music' component
        playMusicButton = (Button) findViewById(R.id.playMusicButton);
        playMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlayMusicActivity();
            }
        });

        // Button that links to 'Mood Tracker' component
        moodTrackerButton = (Button) findViewById(R.id.moodTrackerButton);
        moodTrackerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMoodTracker();
            }
        });

        // Button that links to 'Breathing Exercises' component
        breathingExButton = (Button) findViewById(R.id.breathingExButton);
        breathingExButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openBreathingExercises();}
        });

        // Button that links to 'Mindfulness' component
        mindfulnessButton = (Button) findViewById(R.id.mindfulnessButton);
        mindfulnessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openMindfulness();}
        });

        // Button that links to 'Web Support Articles' component
        webSupportArticlesButton = (Button) findViewById(R.id.webSupportArticlesButton);
        webSupportArticlesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openWebSupportArticles();}
        });

        // Button that links to 'More Resources' component
        moreResourcesButton = (Button) findViewById(R.id.moreResourcesButton);
        moreResourcesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openMoreResources();}
        });
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