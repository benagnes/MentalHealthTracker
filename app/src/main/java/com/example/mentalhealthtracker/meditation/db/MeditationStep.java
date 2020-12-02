package com.example.mentalhealthtracker.meditation.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "meditation_step",
        foreignKeys = @ForeignKey(
                entity = MeditationRoutine.class,
                parentColumns = "routineId",
                childColumns = "routine",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {
                @Index(value = {"routine"})
        }
)
public class MeditationStep {
    @PrimaryKey(autoGenerate = true)
    private int stepId;
    private int routine;
    private int order;
    private int time;
    private String title;
    private String description;
    private int image;

    public MeditationStep(int routine, int order, int time, String title, String description,
                          int image) {
        this.routine = routine;
        this.order = order;
        this.time = time;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public int getRoutine() {
        return routine;
    }

    public void setRoutine(int routine) {
        this.routine = routine;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
