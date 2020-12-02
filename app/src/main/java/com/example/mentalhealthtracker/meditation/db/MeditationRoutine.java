package com.example.mentalhealthtracker.meditation.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "meditation_routine")
public class MeditationRoutine {
    @PrimaryKey
    private int routineId;
    private String routineName;
    private String routineDescription;

    public MeditationRoutine(int routineId, String routineName, String routineDescription) {
        this.routineId = routineId;
        this.routineName = routineName;
        this.routineDescription = routineDescription;
    }

    public int getRoutineId() {
        return routineId;
    }

    public void setRoutineId(int routineId) {
        this.routineId = routineId;
    }

    public String getRoutineName() {
        return routineName;
    }

    public void setRoutineName(String routineName) {
        this.routineName = routineName;
    }

    public String getRoutineDescription() {
        return routineDescription;
    }

    public void setRoutineDescription(String routineDescription) {
        this.routineDescription = routineDescription;
    }
}
