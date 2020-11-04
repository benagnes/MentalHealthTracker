package com.example.mentalhealthtracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.mentalhealthtracker.music.PlayMusic;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {
    private Button doYogaButton;
    private Button playMusicButton;
    private TextView welcomeMsg;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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

    }

    public void openYogaActivity() {
        Intent intent = new Intent(this, DoYoga.class);
        startActivity(intent);
    }

    public void openPlayMusicActivity() {
        Intent intent = new Intent(this, PlayMusic.class);
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