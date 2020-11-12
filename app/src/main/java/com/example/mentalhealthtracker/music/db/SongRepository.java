package com.example.mentalhealthtracker.music.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SongRepository {
    private SongDao songDao;
    private LiveData<List<Song>> allSongs;

    public SongRepository(Application application) {
        SongDatabase db =SongDatabase.getInstance(application);
        songDao = db.songDao();
        allSongs = songDao.getAllSongs();
    }

    public LiveData<List<Song>> getAllSongs() {
        return allSongs;
    }
}
