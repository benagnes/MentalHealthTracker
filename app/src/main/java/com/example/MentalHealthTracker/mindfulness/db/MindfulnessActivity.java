package com.example.MentalHealthTracker.mindfulness.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mindfulness_activity")
public class MindfulnessActivity {
    @PrimaryKey
    private int activityId;
    private String title;
    private String description;

    public MindfulnessActivity(int activityId, String title, String description) {
        this.activityId = activityId;
        this.title = title;
        this.description = description;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
