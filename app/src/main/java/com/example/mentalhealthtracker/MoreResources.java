package com.example.mentalhealthtracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mentalhealthtracker.WebSupportArticles.WebSupportArticles;

public class MoreResources extends AppCompatActivity {
    // attributes
    private static final String appBarTitle = "More Resources";

    // Resource statistics database handler and support
    //private StatisticsDBHandler statisticsDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_resources);

        ActionBar mainActionBar = getSupportActionBar();
        if (mainActionBar != null) {
            mainActionBar.setTitle(appBarTitle);
            mainActionBar.setDisplayHomeAsUpEnabled(true);
        }

        //statisticsDBHandler = new StatisticsDBHandler(this, null);

        // Button that links to 'Web Support Articles' component
        Button webSupportButton = findViewById(R.id.webSupportButton);
        webSupportButton.setOnClickListener(v -> openWebSupport());

        // Button that links to 'Meditation' component
        Button meditationButton = findViewById(R.id.meditationButton);
        meditationButton.setOnClickListener(v -> openMeditation());

        // Button that links to 'Connect with Counsellors' component
        Button counsellorsButton = findViewById(R.id.counsellorsButton);
        counsellorsButton.setOnClickListener(v -> openConnectCounsellors());

        // Button that links to 'View Statistics' component
        Button statisticsButton = findViewById(R.id.statisticsButton);
        statisticsButton.setOnClickListener(v -> openStatistics());

    }

    public void openWebSupport() {
        //statisticsDBHandler.updateHandler(Resources.WebSupportArticles.toString());
        Intent intent = new Intent(this, WebSupportArticles.class);
        startActivity(intent);
    }

    public void openMeditation() {
        //statisticsDBHandler.updateHandler(Resources.Meditation.toString());
        Intent intent = new Intent(this, Meditation.class);
        startActivity(intent);
    }

    public void openConnectCounsellors() {
        //statisticsDBHandler.updateHandler(Resources.ConnectCounsellors.toString());
        Intent intent = new Intent(this, ConnectCounsellors.class);
        startActivity(intent);
    }

    public void openStatistics() {
        Intent intent = new Intent(this, ViewStatistics.class);
        startActivity(intent);
    }
}
