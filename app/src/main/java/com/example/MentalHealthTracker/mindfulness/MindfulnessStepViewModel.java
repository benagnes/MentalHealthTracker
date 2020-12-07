package com.example.MentalHealthTracker.mindfulness;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.MentalHealthTracker.mindfulness.db.MindfulnessDatabase;
import com.example.MentalHealthTracker.mindfulness.db.MindfulnessStep;

import java.util.List;

public class MindfulnessStepViewModel extends AndroidViewModel {
    private final MindfulnessDatabase db;
    private LiveData<List<MindfulnessStep>> steps;
    private int currentStep;
    private long timeLeft;
    private InteractiveActivityState state;

    public MindfulnessStepViewModel(@NonNull Application application) {
        super(application);
        this.db = MindfulnessDatabase.getInstance(application);
        this.currentStep = 0;
        this.timeLeft = 0;
        this.state = InteractiveActivityState.NOT_STARTED;
    }

    public LiveData<List<MindfulnessStep>> getSteps() {
        return steps;
    }

    public void setStepsForActivity(int activityId) {
        this.steps = this.db.stepDao().findStepsForActivity(activityId);
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    public long getCurrentTime() {
        return timeLeft;
    }

    public void setCurrentTime(long currentTime) {
        this.timeLeft = currentTime;
    }

    public InteractiveActivityState getState() {
        return state;
    }

    public void setState(InteractiveActivityState state) {
        this.state = state;
    }

    public enum InteractiveActivityState {
        NOT_STARTED,
        PAUSED,
        STARTED
    }
}
