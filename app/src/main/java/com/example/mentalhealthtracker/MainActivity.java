package com.example.mentalhealthtracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.mentalhealthtracker.favourites.EditFavourites;
import com.example.mentalhealthtracker.music.PlayMusic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {
    // attributes
    private TextView welcomeMsg;

    // methods
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set welcome message based on time of day
        welcomeMsg = (TextView) findViewById(R.id.welcomeMsg);
        setWelcomeMsg();

        // Button that links to 'Do Yoga' component
        Button doYogaButton = (Button) findViewById(R.id.yogaButton);
        doYogaButton.setOnClickListener(v -> openYogaActivity());

        // Button that links to 'Play Music' component
        Button playMusicButton = (Button) findViewById(R.id.playMusicButton);
        playMusicButton.setOnClickListener(v -> openPlayMusicActivity());

        // Button that links to 'Mood Tracker' component
        Button moodTrackerButton = (Button) findViewById(R.id.moodTrackerButton);
        moodTrackerButton.setOnClickListener(v -> openMoodTracker());

        // Button that links to 'Breathing Exercises' component
        Button breathingExButton = (Button) findViewById(R.id.breathingExButton);
        breathingExButton.setOnClickListener(v -> openBreathingExercises());

        // Button that links to 'Mindfulness' component
        Button mindfulnessButton = (Button) findViewById(R.id.mindfulnessButton);
        mindfulnessButton.setOnClickListener(v -> openMindfulness());

        // Button that links to 'Meditation' component
        Button meditationButton = (Button) findViewById(R.id.meditationButton);
        meditationButton.setOnClickListener(v -> openMeditation());

        // Button that links to 'More Resources' component
        Button moreResourcesButton = (Button) findViewById(R.id.moreResourcesButton);
        moreResourcesButton.setOnClickListener(v -> openMoreResources());
        
        // Button that links to 'Edit Favourites' component
        Button editFavouritesButton = (Button) findViewById(R.id.editFavouritesButton);
        editFavouritesButton.setOnClickListener(v -> openEditFavourites());

        // Defaults for favourite resources
        Button favourites1Button = (Button) findViewById(R.id.favourite1Button);
        favourites1Button.setOnClickListener(v -> setDefaultFavourites(1));

        Button favourites2Button = (Button) findViewById(R.id.favourite2Button);
        favourites2Button.setOnClickListener(v -> setDefaultFavourites(2));

        Button favourites3Button = (Button) findViewById(R.id.favourite3Button);
        favourites3Button.setOnClickListener(v -> setDefaultFavourites(3));
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

    public void openMeditation() {
        Intent intent = new Intent(this, Meditation.class);
        startActivity(intent);
    }

    public void openMoreResources() {
        Intent intent = new Intent(this, MoreResources.class);
        startActivity(intent);
    }
    
    public void openEditFavourites() {
        Intent intent = new Intent(this, EditFavourites.class);
        startActivity(intent);
    }

    public void setDefaultFavourites(int favouriteNum) {
        switch(favouriteNum) {
            case 1:
                Intent intent = new Intent(this, PlayMusic.class);
                startActivity(intent);
                break;
            case 2:
                Intent intent2 = new Intent(this, Yoga.class);
                startActivity(intent2);
                break;
            case 3:
                Intent intent3 = new Intent(this, MoodTracker.class);
                startActivity(intent3);
                break;
            default:
        }
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