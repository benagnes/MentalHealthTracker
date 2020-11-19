package com.example.mentalhealthtracker.mindfulness.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MindfulnessActivityDao {
    @Query("SELECT * FROM mindfulness_activity")
    LiveData<List<MindfulnessActivity>> getAllMindfulnessActivities();

    @Insert
    void insertAll(MindfulnessActivity... activities);
}
