package com.example.mentalhealthtracker.music;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mentalhealthtracker.music.db.Song;
import com.example.mentalhealthtracker.music.db.SongRepository;

import java.util.List;

public class SongViewModel extends AndroidViewModel {
    private SongRepository repository;
    private LiveData<List<Song>> allSongs;
    private int selectedSongIndex;
    private boolean isSongSelected;
    private int playerProgress;
    private boolean shuffleInProgress;
    private int selectedSongId;

    // Mini player
    private MusicPlayerState playerState;
    private MusicPlayerUIState playerUIState;

    public SongViewModel(@NonNull Application application) {
        super(application);
        repository = new SongRepository(application);
        allSongs = repository.getAllSongs();
        selectedSongIndex = -1;
        selectedSongId = -1;
        isSongSelected = false;
        shuffleInProgress = false;
        playerState = MusicPlayerState.NOT_INITIALIZED;
        playerUIState = MusicPlayerUIState.NO_PLAYER;
    }

    public LiveData<List<Song>> getAllSongs() {
        return allSongs;
    }

    public boolean isSongSelected() {
        return isSongSelected;
    }

    public void setSongSelected(boolean songSelected) {
        isSongSelected = songSelected;
    }

    public int getSelectedSongIndex() {
        return selectedSongIndex;
    }

    public void setSelectedSongIndex(int selectedSongIndex) {
        this.selectedSongIndex = selectedSongIndex;
    }

    public MusicPlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(MusicPlayerState playerState) {
        this.playerState = playerState;
    }

    public int getPlayerProgress() {
        return playerProgress;
    }

    public void setPlayerProgress(int playerProgress) {
        this.playerProgress = playerProgress;
    }

    public MusicPlayerUIState getPlayerUIState() {
        return playerUIState;
    }

    public void setPlayerUIState(MusicPlayerUIState playerUIState) {
        this.playerUIState = playerUIState;
    }

    public boolean isShuffleInProgress() {
        return shuffleInProgress;
    }

    public void setShuffleInProgress(boolean shuffleInProgress) {
        this.shuffleInProgress = shuffleInProgress;
    }

    public SongRepository getRepository() {
        return repository;
    }

    public int getSelectedSongId() {
        return selectedSongId;
    }

    public void setSelectedSongId(int selectedSongId) {
        this.selectedSongId = selectedSongId;
    }
}
