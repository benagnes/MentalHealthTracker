package com.example.mentalhealthtracker.mindfulness.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "mindfulness_step",
        foreignKeys = @ForeignKey(
                entity = MindfulnessActivity.class,
                parentColumns = "activityId",
                childColumns = "activity",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {
                @Index(value = {"activity"})
        }
)
public class MindfulnessStep {
    @PrimaryKey(autoGenerate = true)
    private int stepId;
    private int activity;
    private int number;
    private int time;
    private String title;
    private String description;

    public MindfulnessStep(int activity, int number, int time, String title,
                           String description) {
        this.activity = activity;
        this.number = number;
        this.time = time;
        this.title = title;
        this.description = description;
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
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
