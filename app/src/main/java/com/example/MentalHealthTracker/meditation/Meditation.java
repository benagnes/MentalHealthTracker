package com.example.MentalHealthTracker.meditation;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MentalHealthTracker.R;
import com.example.MentalHealthTracker.meditation.db.MeditationRoutine;

import java.util.ArrayList;
import java.util.Objects;

public class Meditation extends AppCompatActivity {
    // attributes
    private static final String appBarTitle = "Meditation";
    private MeditationRoutinesAdapter adapter;
    private MeditationRoutinesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);

        ActionBar mainActionBar = getSupportActionBar();
        if (mainActionBar != null) {
            mainActionBar.setTitle(appBarTitle);
            mainActionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Setting up activity view
        RecyclerView activitiesView = findViewById(R.id.meditationRoutines);
        activitiesView.setHasFixedSize(true);
        RecyclerView.LayoutManager activityLayoutManager = new LinearLayoutManager(this);
        adapter = new MeditationRoutinesAdapter(new ArrayList<>());
        activitiesView.setLayoutManager(activityLayoutManager);
        activitiesView.setAdapter(adapter);

        // Setting up recycler
        viewModel = new ViewModelProvider(this).get(MeditationRoutinesViewModel.class);
        viewModel.getRoutines().observe(this, routines -> {
            // Activities were loaded
            adapter.setRoutines(routines);
        });
        adapter.setOnClickListener(this::selectMeditationRoutine);
    }

    // Open a Mindfulness activity
    private void selectMeditationRoutine(int position) {
        Intent meditationRoutine = new Intent(this, MeditationPlayer.class);

        // Get the id for the activity the user selected
        MeditationRoutine chosenActivity = Objects.requireNonNull(viewModel.getRoutines().getValue())
                .get(position);
        meditationRoutine.putExtra(MeditationPlayer.ROUTINE_REF,
                Integer.toString(chosenActivity.getRoutineId()));

        startActivity(meditationRoutine);
    }
}
