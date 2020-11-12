package com.example.mentalhealthtracker.music.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SongDao {
    @Query("SELECT * FROM songs")
    LiveData<List<Song>> getAllSongs();

    @Insert
    void insertAll(Song... songs);

    @Delete
    void delete(Song song);
}
