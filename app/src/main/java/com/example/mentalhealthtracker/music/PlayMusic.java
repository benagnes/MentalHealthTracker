package com.example.mentalhealthtracker.music;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mentalhealthtracker.R;
import com.example.mentalhealthtracker.timing.MediaTimestamp;
import com.example.mentalhealthtracker.timing.MediaTiming;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class PlayMusic extends AppCompatActivity {
    private MediaPlayer musicPlayer;
    private MusicPlayerState currentMediaPlayerState;
    private final Handler mediaHandler = new Handler(Looper.getMainLooper());
    private int lastMediaLengthCache;

    private SeekBar mediaSeekBar;
    private TextView mediaLengthText;
    private TextView mediaRunTimeText;
    private FloatingActionButton playerControlButton;

    private static final String appBarTitle = "Music Player";
    // How often we should update the SeekBar and time labels
    private static final int playerUIUpdatePeriod = 500;

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

        lastMediaLengthCache = 0;
        currentMediaPlayerState = MusicPlayerState.NOT_INITIALIZED;
        mediaSeekBar = findViewById(R.id.musicSeekBar);
        mediaLengthText = findViewById(R.id.mediaTimeLength);
        mediaRunTimeText = findViewById(R.id.mediaTimePlayed);
        playerControlButton = findViewById(R.id.floatingActionButton);

        mediaSeekBar.setEnabled(false);
        setPlayerControlButtonIconPlay();
    }

    /**
     * The listener for all changes with the SeekBar
     */
    private final SeekBar.OnSeekBarChangeListener seekBarListener =  new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (musicPlayer != null && fromUser) {
                musicPlayer.seekTo(progress * 1000);
            } else if (lastMediaLengthCache > 0 &&
                    currentMediaPlayerState == MusicPlayerState.NOT_INITIALIZED) {
                // For an uninitialized MediaPlayer, if we previously played a song,
                // adjust the label to reflect the changes in the SeekBar
                mediaRunTimeText.setText(
                        MediaTiming.formatMediaTimestamp(
                                new MediaTimestamp(progress * 1000)
                        )
                );
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) { }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) { }
    };

    /**
     * Update the music player UI
     */
    private final Runnable updateUIThread = new Runnable() {
        @Override
        public void run() {
            if (musicPlayer != null) {
                MediaTimestamp currentProgress = new MediaTimestamp(
                        musicPlayer.getCurrentPosition());
                mediaSeekBar.setProgress(currentProgress.getTotalAsSeconds());
                mediaRunTimeText.setText(MediaTiming.formatMediaTimestamp(currentProgress));
            }

            mediaHandler.postDelayed(this, playerUIUpdatePeriod);
        }
    };

    // Moves the media state machine forward
    public void changeMediaState(View v) {
        switch (currentMediaPlayerState) {
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

    public void playMusic() {
        if (musicPlayer == null) {
            musicPlayer = MediaPlayer.create(this, R.raw.file_example);
            musicPlayer.setOnCompletionListener(mp -> {
                currentMediaPlayerState = MusicPlayerState.FINISHED;
                stopMediaPlayer();
            });

            lastMediaLengthCache = musicPlayer.getDuration();

            MediaTimestamp timestamp = new MediaTimestamp(musicPlayer.getDuration());
            mediaLengthText.setText(MediaTiming.formatMediaTimestamp(timestamp));

            mediaSeekBar.setMax(timestamp.getTotalAsSeconds());
            mediaSeekBar.setOnSeekBarChangeListener(seekBarListener);
            mediaSeekBar.setEnabled(true);
        }

        // Reset our song to the start if we're not at the end
        if (mediaSeekBar.getProgress() < mediaSeekBar.getMax())
            musicPlayer.seekTo( mediaSeekBar.getProgress() * 1000);

        musicPlayer.start();
        currentMediaPlayerState = MusicPlayerState.PLAYING;
        setPlayerControlButtonIconPause();

        // Create thread to update the SeekBar and time labels
        runOnUiThread(updateUIThread);
    }

    /**
     * Pauses the currently playing song
     */
    public void pauseMusic() {
        if (musicPlayer == null)
            return;

        musicPlayer.pause();
        currentMediaPlayerState = MusicPlayerState.PAUSED;
        setPlayerControlButtonIconPlay();
    }

    /**
     * Frees up the MediaPlayer resources
     */
    public void stopMediaPlayer() {
        if (musicPlayer == null)
            return;

        // Make sure our UI is synced up at the end
        mediaRunTimeText.setText(
                MediaTiming.formatMediaTimestamp(
                        new MediaTimestamp(musicPlayer.getDuration())
                )
        );
        mediaSeekBar.setProgress(mediaSeekBar.getMax());
        setPlayerControlButtonIconPlay();

        musicPlayer.release();
        musicPlayer = null;
        currentMediaPlayerState = MusicPlayerState.NOT_INITIALIZED;
    }

    // Set the main control button to pause icon
    private void setPlayerControlButtonIconPause() {
        playerControlButton.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
    }

    // Set the main control button to play icon
    private void setPlayerControlButtonIconPlay() {
        playerControlButton.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
    }

    // Cleanup the media player when the activity stops
    public void onStop() {
        if (musicPlayer != null)
            stopMediaPlayer();
        super.onStop();
    }
}
