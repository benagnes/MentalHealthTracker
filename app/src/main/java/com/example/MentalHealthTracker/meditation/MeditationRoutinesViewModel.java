package com.example.MentalHealthTracker.meditation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.MentalHealthTracker.meditation.db.MeditationDatabase;
import com.example.MentalHealthTracker.meditation.db.MeditationRoutine;

import java.util.List;

public class MeditationRoutinesViewModel extends AndroidViewModel {
    MeditationDatabase db;
    LiveData<List<MeditationRoutine>> routines;

    public MeditationRoutinesViewModel(@NonNull Application application) {
        super(application);
        this.db = MeditationDatabase.getInstance(application);
        this.routines = db.routineDao().getAllMeditationRoutines();
    }

    public LiveData<List<MeditationRoutine>> getRoutines() {
        return routines;
    }
}
