package com.example.MentalHealthTracker.meditation.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MeditationRoutineDao {
    @Query("SELECT * FROM meditation_routine")
    LiveData<List<MeditationRoutine>> getAllMeditationRoutines();

    @Insert
    void insertAll(MeditationRoutine... routines);
}
