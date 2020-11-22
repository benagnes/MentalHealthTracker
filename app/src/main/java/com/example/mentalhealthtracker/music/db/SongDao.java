package com.example.mentalhealthtracker.music.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SongDao {
    @Query("SELECT * FROM songs ORDER BY `order` DESC")
    LiveData<List<Song>> getAllSongs();

    @Query("SELECT * FROM songs ORDER BY RANDOM()")
    List<Song> getShuffledSongs();

    @Insert
    void insertAll(Song... songs);

    @Update
    void update(Song... songs);

    @Update
    int update(List<Song> songs);

    @Delete
    void delete(Song song);
}
