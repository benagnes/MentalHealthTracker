package com.example.mentalhealthtracker.mindfulness;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mentalhealthtracker.mindfulness.db.MindfulnessActivity;
import com.example.mentalhealthtracker.mindfulness.db.MindfulnessDatabase;

import java.util.List;

public class MindfulnessViewModel extends AndroidViewModel {
    private MindfulnessDatabase db;
    private LiveData<List<MindfulnessActivity>> activities;

    public MindfulnessViewModel(@NonNull Application application) {
        super(application);
        this.db = MindfulnessDatabase.getInstance(application);
        this.activities = db.activityDao().getAllMindfulnessActivities();
    }

    public LiveData<List<MindfulnessActivity>> getActivities() {
        return activities;
    }

    public void setActivities(LiveData<List<MindfulnessActivity>> activities) {
        this.activities = activities;
    }
}
