package com.example.MentalHealthTracker;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.MentalHealthTracker.Statistics.StatisticsDBHandler;

public class ViewStatistics extends AppCompatActivity {
    // attributes
    private static final String appBarTitle = "View Statistics";

    private StatisticsDBHandler statisticsDBHandler;

    private TextView YogaTimesUsed;
    private TextView MoodTrackerTimesUsed;
    private TextView PlayMusicTimesUsed;
    private TextView BreathingExercisesTimesUsed;
    private TextView MindfulnessTimesUsed;
    private TextView ExercisesTimesUsed;
    private TextView WebSupportArticlesTimesUsed;
    private TextView MeditationTimesUsed;
    private TextView ConnectWithCounsellorsTimesUsed;

    private PopupWindow confirmResetAllWindow;
    private LinearLayout confirmResetAllLayout;

    // methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_statistics);

        ActionBar mainActionBar = getSupportActionBar();
        if (mainActionBar != null) {
            mainActionBar.setTitle(appBarTitle);
            mainActionBar.setDisplayHomeAsUpEnabled(true);
        }

        statisticsDBHandler = StatisticsDBHandler.getInstance(this);

        YogaTimesUsed = findViewById(R.id.YogaTimesUsed);
        PlayMusicTimesUsed = findViewById(R.id.MusicTimesUsed);
        MoodTrackerTimesUsed = findViewById(R.id.MoodTrackerTimesUsed);
        BreathingExercisesTimesUsed = findViewById(R.id.BreathingExercisesTimesUsed);
        MindfulnessTimesUsed = findViewById(R.id.MindfulnessTimesUsed);
        ExercisesTimesUsed = findViewById(R.id.ExercisesTimesUsed);
        WebSupportArticlesTimesUsed = findViewById(R.id.WebSupportArticlesTimesUsed);
        MeditationTimesUsed = findViewById(R.id.MeditationTimesUsed);
        ConnectWithCounsellorsTimesUsed = findViewById(R.id.ConnectwithCounsellorsTimesUsed);

        int yogaUsed = statisticsDBHandler.getTimesUsedHandler(R.string.yoga);
        YogaTimesUsed.setText(String.valueOf(yogaUsed));

        int playMusicUsed = statisticsDBHandler.getTimesUsedHandler(R.string.playmusic);
        PlayMusicTimesUsed.setText(String.valueOf(playMusicUsed));

        int moodTrackerUsed = statisticsDBHandler.getTimesUsedHandler(R.string.moodtracker);
        MoodTrackerTimesUsed.setText(String.valueOf(moodTrackerUsed));

        int breathingExercisesUsed = statisticsDBHandler.getTimesUsedHandler(R.string.breathingexercises);
        BreathingExercisesTimesUsed.setText(String.valueOf(breathingExercisesUsed));

        int mindfulnessUsed = statisticsDBHandler.getTimesUsedHandler(R.string.mindfulness);
        MindfulnessTimesUsed.setText(String.valueOf(mindfulnessUsed));

        int exercisesUsed = statisticsDBHandler.getTimesUsedHandler(R.string.exercises);
        ExercisesTimesUsed.setText(String.valueOf(exercisesUsed));

        int webSupportArticlesUsed = statisticsDBHandler.getTimesUsedHandler(R.string.websupportarticles);
        WebSupportArticlesTimesUsed.setText(String.valueOf(webSupportArticlesUsed));

        int meditationUsed = statisticsDBHandler.getTimesUsedHandler(R.string.meditation);
        MeditationTimesUsed.setText(String.valueOf(meditationUsed));

        int connectWithCounsellorsUsed = statisticsDBHandler.getTimesUsedHandler(R.string.counsellors);
        ConnectWithCounsellorsTimesUsed.setText(String.valueOf(connectWithCounsellorsUsed));

        confirmResetAllWindow = new PopupWindow(this);
        confirmResetAllLayout = new LinearLayout(this);
        LinearLayout.LayoutParams reset_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        confirmResetAllLayout.setOrientation(LinearLayout.VERTICAL);

        Button cancelResetAllStatistics = new Button(this);
        cancelResetAllStatistics.setText(R.string.cancelResetAllStats);
        cancelResetAllStatistics.setOnClickListener( v -> confirmResetAllWindow.dismiss());

        Button confirmResetAllStatistics = new Button(this);
        confirmResetAllStatistics.setText(R.string.confirmResetAllStats);
        confirmResetAllStatistics.setOnClickListener( v -> {
            resetAll();
            confirmResetAllWindow.dismiss();
        });

        confirmResetAllLayout.addView(cancelResetAllStatistics, reset_params);
        confirmResetAllLayout.addView(confirmResetAllStatistics, reset_params);
        confirmResetAllWindow.setContentView(confirmResetAllLayout);

        Button resetAllButton = findViewById(R.id.resetAllStatisticsButton);
        resetAllButton.setOnClickListener( v -> {
            confirmResetAllWindow.setWidth(500);
            confirmResetAllWindow.setHeight(400);
            confirmResetAllWindow.showAtLocation(confirmResetAllLayout, Gravity.CENTER, 0, 0);
        });
    }

    public void resetAll() {
        statisticsDBHandler.resetAllHandler();

        YogaTimesUsed.setText(String.valueOf(0));
        PlayMusicTimesUsed.setText(String.valueOf(0));
        MoodTrackerTimesUsed.setText(String.valueOf(0));
        BreathingExercisesTimesUsed.setText(String.valueOf(0));
        MindfulnessTimesUsed.setText(String.valueOf(0));
        ExercisesTimesUsed.setText(String.valueOf(0));
        ConnectWithCounsellorsTimesUsed.setText(String.valueOf(0));
        MeditationTimesUsed.setText(String.valueOf(0));
        WebSupportArticlesTimesUsed.setText(String.valueOf(0));
    }


    @Override
    protected void onStop() {
        super.onStop();
        statisticsDBHandler.closeConnection();
    }
}
