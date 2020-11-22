package com.example.mentalhealthtracker.music.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class SongRepository {
    private final SongDao songDao;
    private LiveData<List<Song>> allSongs;

    public SongRepository(Application application) {
        SongDatabase db =SongDatabase.getInstance(application);
        songDao = db.songDao();
        allSongs = songDao.getAllSongs();
    }

    public LiveData<List<Song>> getAllSongs() {
        return allSongs;
    }

    // Shuffle the order of all songs
    public void shuffleSongs() {
        // Make sure to do the shuffle in a separate thread to not block the UI thread
        new Thread(() -> {
            List<Song> songList = songDao.getShuffledSongs();
            int i = 0;

            if (songList == null)
                return;

            // Set the new order for each song
            for (Song s: songList) {
                s.setOrder(i);
                i++;
            }

            songDao.update(songList);
        }).start();
    }
}
