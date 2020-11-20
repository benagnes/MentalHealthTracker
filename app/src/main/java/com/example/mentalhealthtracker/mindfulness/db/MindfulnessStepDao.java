package com.example.mentalhealthtracker.mindfulness.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MindfulnessStepDao {
    @Query("SELECT * FROM mindfulness_step WHERE activity=:activityId ORDER BY number")
    LiveData<List<MindfulnessStep>> findStepsForActivity(final int activityId);

    @Insert
    void insertAll(MindfulnessStep... steps);
}
