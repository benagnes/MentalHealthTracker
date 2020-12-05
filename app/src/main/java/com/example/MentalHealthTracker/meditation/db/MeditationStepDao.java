package com.example.MentalHealthTracker.meditation.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MeditationStepDao {
    @Query("SELECT * FROM meditation_step WHERE routine=:routineId")
    LiveData<List<MeditationStep>> findStepsForRoutine(final int routineId);

    @Insert
    void insertAll(MeditationStep... steps);
}
