package com.example.MentalHealthTracker.mindfulness;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MentalHealthTracker.R;
import com.example.MentalHealthTracker.mindfulness.db.MindfulnessActivity;

import java.util.ArrayList;
import java.util.Objects;

public class Mindfulness extends AppCompatActivity {
    // attributes
    private static final String appBarTitle = "Mindfulness";
    private MindfulnessActivityAdapter adapter;
    private MindfulnessViewModel mindfulnessViewModel;

    // methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_mindfulness);

        ActionBar mainActionBar = getSupportActionBar();

        if (mainActionBar != null) {
            mainActionBar.setTitle(appBarTitle);
            mainActionBar.setDisplayHomeAsUpEnabled(true);
            mainActionBar.setElevation(0);
        }

        // Setting up activity view
        RecyclerView activitiesView = findViewById(R.id.mindfulnessActivities);
        activitiesView.setHasFixedSize(true);
        RecyclerView.LayoutManager activityLayoutManager = new LinearLayoutManager(this);
        adapter = new MindfulnessActivityAdapter(new ArrayList<>());
        activitiesView.setLayoutManager(activityLayoutManager);
        activitiesView.setAdapter(adapter);

        // Setting up recycler
        mindfulnessViewModel = new ViewModelProvider(this).get(MindfulnessViewModel.class);
        mindfulnessViewModel.getActivities().observe(this, activities -> {
            // Activities were loaded
            adapter.setActivities(activities);
        });
        adapter.setOnClickListener(this::selectActivity);
    }

    // Open a Mindfulness activity
    private void selectActivity(int position) {
        Intent interactiveActivity = new Intent(this, MindfulnessInteractive.class);

        // Get the id for the activity the user selected
        MindfulnessActivity chosenActivity = Objects.requireNonNull(mindfulnessViewModel
                .getActivities().getValue())
                .get(position);
        interactiveActivity.putExtra(MindfulnessInteractive.ACTIVITY_REF,
                Integer.toString(chosenActivity.getActivityId()));

        startActivity(interactiveActivity);
    }
}
