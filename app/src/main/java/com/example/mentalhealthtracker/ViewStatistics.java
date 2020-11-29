package com.example.mentalhealthtracker;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mentalhealthtracker.Statistics.StatisticsDBHandler;

public class ViewStatistics extends AppCompatActivity {
    // attributes
    private static final String appBarTitle = "View Statistics";

    private StatisticsDBHandler statisticsDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_statistics);

        ActionBar mainActionBar = getSupportActionBar();
        if (mainActionBar != null) {
            mainActionBar.setTitle(appBarTitle);
            mainActionBar.setDisplayHomeAsUpEnabled(true);
        }

        statisticsDBHandler = new StatisticsDBHandler(this, null);
    }

    public void onButtonClick() {

    }
}
