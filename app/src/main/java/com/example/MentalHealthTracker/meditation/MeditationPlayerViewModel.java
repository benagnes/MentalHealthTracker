package com.example.MentalHealthTracker.meditation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.MentalHealthTracker.meditation.db.MeditationDatabase;
import com.example.MentalHealthTracker.meditation.db.MeditationStep;

import java.util.List;

public class MeditationPlayerViewModel extends AndroidViewModel {
    private final MeditationDatabase db;
    private LiveData<List<MeditationStep>> steps;
    private MeditationPlayerState state;
    private int routineId;
    private int currentStep;
    private long currentTime;

    public MeditationPlayerViewModel(@NonNull Application application) {
        super(application);
        db = MeditationDatabase.getInstance(application);
        state = MeditationPlayerState.NOT_STARTED;
        currentStep = 0;
    }

    public LiveData<List<MeditationStep>> getSteps() {
        return steps;
    }

    public void loadSteps() {
        steps = db.stepDao().findStepsForRoutine(this.routineId);
    }

    public int getRoutineId() {
        return routineId;
    }

    public void setRoutineId(int routineId) {
        this.routineId = routineId;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public MeditationPlayerState getState() {
        return state;
    }

    public void setState(MeditationPlayerState state) {
        this.state = state;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }
}
