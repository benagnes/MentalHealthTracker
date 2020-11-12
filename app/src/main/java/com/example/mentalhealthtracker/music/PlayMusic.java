package com.example.mentalhealthtracker.music;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mentalhealthtracker.R;
import com.example.mentalhealthtracker.music.db.Song;
import com.example.mentalhealthtracker.timing.MediaTimestamp;
import com.example.mentalhealthtracker.timing.MediaTiming;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlayMusic extends AppCompatActivity {
    private MediaPlayer musicPlayer;
    private ArrayList<Song> songLibrary = new ArrayList<>();
    private SongListItemAdapter songListAdapter;
    private SongViewModel songViewModel;

    // Mini music player components
    private ConstraintLayout musicBar;
    private ProgressBar miniPlayerProgressBar;
    private ImageButton miniPlayerControlButton;
    private SeekBar fpProgressBar;
    private FloatingActionButton fpPlayerControlButton;
    private ImageView fpSongThumbnail;
    private TextView fpSongTitle;
    private TextView fpSongArtists;
    private TextView fpPlayTime;
    private TextView fpTimeLeft;

    private static final String appBarTitle = "Music Player";
    private static final String TAG = "MusicPlayer";
    // How often we should update the SeekBar and time labels
    private static final int playerUIUpdatePeriod = 500;
    // UI Thread handler
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        // The top menu of the view
        ActionBar mainActionBar = getSupportActionBar();

        if (mainActionBar != null) {
            mainActionBar.setTitle(appBarTitle);
            mainActionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Initializing music player components
        musicBar = findViewById(R.id.musicBar);
        miniPlayerProgressBar = findViewById(R.id.miniPlayerProgress);
        miniPlayerControlButton = findViewById(R.id.miniPlayerControlButton);
        ImageButton miniPlayerSkipForwardButton = findViewById(R.id.miniPlayerSkipForwardButton);
        miniPlayerControlButton.setOnClickListener((e) -> changeMediaState());
        miniPlayerSkipForwardButton.setOnClickListener((e) -> skipToNextSong());

        // Initialize the music library
        RecyclerView songListView = findViewById(R.id.songListRecyclerView);
        songListView.setHasFixedSize(true);
        RecyclerView.LayoutManager songLayoutManager = new LinearLayoutManager(this);
        songListAdapter = new SongListItemAdapter(new ArrayList<>());
        songListView.setLayoutManager(songLayoutManager);
        songListView.setAdapter(songListAdapter);
        musicBar.setVisibility(View.GONE);
        musicBar.setOnClickListener((l) -> attachFullMusicPlayer());

        // Setting up the view model
        songViewModel = new ViewModelProvider(this).get(SongViewModel.class);
        songViewModel.getAllSongs().observe(this, songs -> {
            songListAdapter.setSongs(songs);
            songLibrary = (ArrayList<Song>) songs;

            Log.i(TAG, "Song library loaded with " + songLibrary.size() + " songs");

            // Update the Mini Player after we receiver all the data
            if (!songViewModel.isSongSelected())
                musicBar.setVisibility(View.GONE);
            else {
                musicBar.setVisibility(View.VISIBLE);

                initializeMusicPlayer();
                setMusicPlayerUI();

                MusicPlayerState previousState = songViewModel.getPlayerState();

                if (previousState == MusicPlayerState.PLAYING)
                    playMusic();
            }
        });
        songListAdapter.setOnSongClickListener(this::selectSong);
    }

    private void selectSong(int position) {
        if (position == songViewModel.getSelectedSongIndex())
            return;

        // Save the previous state before we make any switches
        MusicPlayerState previousState = songViewModel.getPlayerState();
        // Reset our progress to the start.
        setPlayerProgress(0);
        songViewModel.setPlayerProgress(0);

        // Stop the current media player, and reset the data source if it's currently running
        // or if it's paused.
        if (musicPlayer != null && (songViewModel.getPlayerState() == MusicPlayerState.PLAYING ||
                songViewModel.getPlayerState() == MusicPlayerState.PAUSED)) {
            stopMediaPlayer();
            initializeMusicPlayer();
        }

        Song clickedSong = songLibrary.get(position);
        int itrIndex = 0;
        // Unset all other previously playing songs
        for (Song song : songLibrary) {
            if (song.isPlaying() && (song.getId() != clickedSong.getId())) {
                songLibrary.get(itrIndex).setPlaying(false);
                songListAdapter.notifyItemChanged(itrIndex);

                Log.v(TAG, "Previously playing song unloaded with id:"
                        + song.getId() + " and index:" + itrIndex);
            }
            itrIndex++;
        }

        clickedSong.setPlaying(true);
        songListAdapter.notifyItemChanged(position);
        songViewModel.setSelectedSongIndex(position);

        if (songViewModel.getPlayerUIState() == MusicPlayerUIState.NO_PLAYER)
            songViewModel.setPlayerUIState(MusicPlayerUIState.MINI_PLAYER);

        if (previousState == MusicPlayerState.PLAYING)
            playMusic();
        else if (previousState == MusicPlayerState.NOT_INITIALIZED)
            initializeMusicPlayer();

        setMusicPlayerUI();

        // Display the music bar if it's not already displayed
        if (!songViewModel.isSongSelected()) {
            songViewModel.setSongSelected(true);
            musicBar.setVisibility(View.VISIBLE);
        }

        Log.v(TAG, "Song selection loaded with id:" + clickedSong.getId()
                + " at index:" + position);
    }

    // Attach the full screen music player onto our current view
    private void attachFullMusicPlayer() {
        // Added view
        View fp = findViewById(R.id.fullMusicPlayer);

        // Inflate the view
        LayoutInflater inflater = (LayoutInflater)      this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View childLayout = inflater.inflate(R.layout.full_music_player,
                (ViewGroup) fp);
        addContentView(childLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        // Full music player
        fpProgressBar = findViewById(R.id.fpSeekBar);
        fpPlayerControlButton = findViewById(R.id.fpControlButton);
        FloatingActionButton fpPlayerSkipBackwardButton = findViewById(R.id.fpSkipBackward);
        FloatingActionButton fpPlayerSkipForwardButton = findViewById(R.id.fpSkipForward);
        fpSongThumbnail = findViewById(R.id.fpSongThumbnail);
        fpSongTitle = findViewById(R.id.fpSongTitle);
        fpSongArtists = findViewById(R.id.fpSongArtists);
        fpPlayTime = findViewById(R.id.fpPlayTimeText);
        fpTimeLeft = findViewById(R.id.fpPlayTimeLeftText);
        ImageButton fpMinimizeButton = findViewById(R.id.fpCloseButton);

        fpMinimizeButton.setOnClickListener((l) -> detachFullMusicPlayer());
        fpPlayerControlButton.setOnClickListener((l) -> changeMediaState());
        fpPlayerSkipForwardButton.setOnClickListener((l) -> skipToNextSong());
        fpPlayerSkipBackwardButton.setOnClickListener((l) -> skipToPreviousSong());

        songViewModel.setPlayerUIState(MusicPlayerUIState.FULL_PLAYER);
        fpProgressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser)
                    return;
                int progressMillis = progress * 1000;
                songViewModel.setPlayerProgress(progressMillis);

                if (songViewModel.getPlayerUIState() == MusicPlayerUIState.FULL_PLAYER)
                    fpPlayTime.setText(MediaTiming.formatMediaTimestamp(
                            new MediaTimestamp(progressMillis)
                    ));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (musicPlayer == null)
                    return;
                if (songViewModel.getPlayerState() == MusicPlayerState.PLAYING) {
                    musicPlayer.pause();
                    handler.removeCallbacks(updateUIThread);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (musicPlayer == null)
                    return;

                musicPlayer.seekTo(songViewModel.getPlayerProgress());
                if (songViewModel.getPlayerState() == MusicPlayerState.PLAYING) {
                    handler.post(updateUIThread);
                    musicPlayer.start();
                }
            }
        });

        // Update the main play/pause button
        if (songViewModel.getPlayerState() == MusicPlayerState.PLAYING)
            setPlayerControlButtonIconPause();
        else
            setPlayerControlButtonIconPlay();

        // The full player can only be accessed if a song is playing, so we always update
        // the UI state here.
        setMusicPlayerUI();

        Log.v(TAG, "Attached full music player");
    }

    // Remove the full screen music player from the view
    private void detachFullMusicPlayer() {
        songViewModel.setPlayerUIState(MusicPlayerUIState.MINI_PLAYER);
        setMiniPlayerUI();
        
        // Remove the view
        ((ViewGroup) findViewById(R.id.fullMusicPlayer).getParent())
                .removeView(findViewById(R.id.fullMusicPlayer));

        Log.v(TAG, "Detached full music player");
    }

    private void skipToNextSong() {
        int nextSongIndex = (songViewModel.getSelectedSongIndex() + 1) % songLibrary.size();
        // Select the next song
        selectSong(nextSongIndex);
    }

    private void skipToPreviousSong() {
        int nextSongIndex = (((songViewModel.getSelectedSongIndex() - 1) % songLibrary.size()) +
                songLibrary.size()) % songLibrary.size();
        // Select the next song
        selectSong(nextSongIndex);
    }

    // Updates the UI based on the type of player that we have currently open
    private void setMusicPlayerUI() {
        switch (songViewModel.getPlayerUIState()) {
            case MINI_PLAYER: {
                setMiniPlayerUI();
                break;
            }
            case FULL_PLAYER: {
                setFullPlayerUI();
                break;
            }
            default: {
                Log.w(TAG, "Music player UI initialization called on an illegal player state "
                        + songViewModel.getPlayerUIState().toString());
            }
        }
    }

    // Handle setup for the mini player
    private void setMiniPlayerUI() {
        Song selectedSong = songLibrary.get(songViewModel.getSelectedSongIndex());

        Picasso.get().load(selectedSong.getThumbnailURL()).into((ImageView)
                findViewById(R.id.miniPlayerThumbnail));
        ((TextView) findViewById(R.id.miniHeader)).setText(selectedSong.getTitle());
        ((TextView) findViewById(R.id.miniplayerSubHeader)).setText(selectedSong.getArtists());

        MediaTimestamp maxDuration = new MediaTimestamp(musicPlayer.getDuration());
        MediaTimestamp currentProgress = new MediaTimestamp(musicPlayer.getCurrentPosition());

        miniPlayerProgressBar.setMax(maxDuration.getTotalAsSeconds());
        miniPlayerProgressBar.setProgress(currentProgress.getTotalAsSeconds());

        // Update the main play/pause button
        if (songViewModel.getPlayerState() == MusicPlayerState.PLAYING)
            setPlayerControlButtonIconPause();
        else
            setPlayerControlButtonIconPlay();
    }

    // Handle setup for the full screen player
    private void setFullPlayerUI() {
        Song selectedSong = songLibrary.get(songViewModel.getSelectedSongIndex());

        Picasso.get().load(selectedSong.getThumbnailURL()).into(fpSongThumbnail);
        fpSongTitle.setText(selectedSong.getTitle());
        fpSongArtists.setText(selectedSong.getArtists());

        MediaTimestamp maxDuration = new MediaTimestamp(musicPlayer.getDuration());
        MediaTimestamp currentProgress = new MediaTimestamp(musicPlayer.getCurrentPosition());

        fpProgressBar.setMax(maxDuration.getTotalAsSeconds());
        fpProgressBar.setProgress(currentProgress.getTotalAsSeconds());
        fpTimeLeft.setText(MediaTiming.formatMediaTimestamp(maxDuration));
        fpPlayTime.setText(MediaTiming.formatMediaTimestamp(currentProgress));
    }

    // Sync the media progress bar with the music player
    private void setPlayerProgress(int progress) {
        if (musicPlayer != null) {
            switch (songViewModel.getPlayerUIState()) {
                case MINI_PLAYER: {
                    miniPlayerProgressBar.setProgress(progress);
                    break;
                }
                case FULL_PLAYER: {
                    fpProgressBar.setProgress(progress);
                    break;
                }
            }
        }
    }

    // Set the maximum player progress
    private void setPlayerProgressMax(int progress) {
        if (musicPlayer != null) {
            switch (songViewModel.getPlayerUIState()) {
                case MINI_PLAYER: {
                    miniPlayerProgressBar.setMax(progress);
                    break;
                }
                case FULL_PLAYER: {
                    fpProgressBar.setMax(progress);
                    break;
                }
            }
        }
    }

    // A separate thread for updating the UI components
    private final Runnable updateUIThread = new Runnable() {
        @Override
        public void run() {
            if (musicPlayer != null) {
                handler.post(() -> {
                    MediaTimestamp currentProgress = new MediaTimestamp(
                            musicPlayer.getCurrentPosition());
                    setPlayerProgress(currentProgress.getTotalAsSeconds());

                    if (songViewModel.getPlayerUIState() == MusicPlayerUIState.FULL_PLAYER)
                        fpPlayTime.setText(MediaTiming.formatMediaTimestamp(currentProgress));
                });
            }

            handler.postDelayed(this, playerUIUpdatePeriod);
        }
    };

    // Moves the media state machine forward
    public void changeMediaState() {
        switch (songViewModel.getPlayerState()) {
            case PLAYING: {
                pauseMusic();
                break;
            }
            case FINISHED: case NOT_INITIALIZED:
            case PAUSED: {
                playMusic();
                break;
            }
        }
    }

    // Start playing music
    public void playMusic() {
        if (musicPlayer == null)
            initializeMusicPlayer();

        musicPlayer.start();
        songViewModel.setPlayerState(MusicPlayerState.PLAYING);
        setPlayerControlButtonIconPause();

        handler.post(updateUIThread);
    }

    // Initializes a media player
    public void initializeMusicPlayer() {
        if (musicPlayer != null) {
            Log.e(TAG, "Music player initialization attempted, but a media player exists");
            return;
        }

        musicPlayer = MediaPlayer.create(this, songLibrary.get(
                songViewModel.getSelectedSongIndex()).getLocalAudioFileId());
        musicPlayer.setOnCompletionListener(mp -> skipToNextSong());
        musicPlayer.seekTo(songViewModel.getPlayerProgress());

        MediaTimestamp timestamp = new MediaTimestamp(musicPlayer.getDuration());
        setPlayerProgressMax(timestamp.getTotalAsSeconds());
    }

    // Pauses the music player, and handles any cleanup needed
    private void pauseMusic() {
        if (musicPlayer == null) {
            Log.w(TAG, "A pause was attempted on a non initialized music player");
            return;
        }

        musicPlayer.pause();
        songViewModel.setPlayerState(MusicPlayerState.PAUSED);
        handler.removeCallbacks(updateUIThread);
        setPlayerControlButtonIconPlay();
    }

    // Stops the media player, and releases all resources
    private void stopMediaPlayer() {
        if (musicPlayer == null) {
            Log.w(TAG, "A stop was attempted on a non initialized music player");
            return;
        }

        musicPlayer.reset();
        musicPlayer.release();
        musicPlayer = null;
        handler.removeCallbacks(updateUIThread);
        setPlayerControlButtonIconPlay();
    }

    // Set the main control button to pause icon
    private void setPlayerControlButtonIconPause() {
        switch (songViewModel.getPlayerUIState()) {
            case MINI_PLAYER: {
                miniPlayerControlButton.setImageResource(R.drawable.ic_baseline_pause_24);
                break;
            }
            case FULL_PLAYER: {
                fpPlayerControlButton.setImageResource(R.drawable.ic_baseline_pause_24);
                break;
            }
            default: {
                Log.w(TAG, "Player control button was set with illegal player UI state " +
                        songViewModel.getPlayerUIState().toString());
            }
        }
    }

    // Set the main control button to play icon
    private void setPlayerControlButtonIconPlay() {
        switch (songViewModel.getPlayerUIState()) {
            case MINI_PLAYER: {
                miniPlayerControlButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                break;
            }
            case FULL_PLAYER: {
                fpPlayerControlButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                break;
            }
            default: {
                Log.w(TAG, "Player control button was set with illegal player UI state " +
                        songViewModel.getPlayerUIState().toString());
            }
        }
    }

    // When the app stops, make sure the state is saved
    @Override
    protected void onStop() {
        super.onStop();

        if (musicPlayer != null && (songViewModel.getPlayerState() == MusicPlayerState.PLAYING ||
                songViewModel.getPlayerState() == MusicPlayerState.PAUSED)) {
            if (musicPlayer.isPlaying())
                musicPlayer.pause();
            songViewModel.setPlayerProgress(musicPlayer.getCurrentPosition());
            stopMediaPlayer();
        } else {
            setPlayerProgress(0);
        }
    }

    // When the app resumes, restore the state
    @Override
    protected void onStart() {
        super.onStart();
        MusicPlayerState previousState = songViewModel.getPlayerState();

        //Player was playing before a pause
        if (previousState == MusicPlayerState.PLAYING || previousState == MusicPlayerState.PAUSED) {
            if (songLibrary.size() != 0) {
                initializeMusicPlayer();
                if (previousState == MusicPlayerState.PLAYING)
                    playMusic();
            }
        }
    }
}
