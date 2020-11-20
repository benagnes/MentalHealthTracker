package com.example.mentalhealthtracker.music.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "songs")
public class Song {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String title;

    @ColumnInfo
    private String artists;

    @ColumnInfo
    private String thumbnailURL;

    @ColumnInfo
    private int localAudioFileId;

    @ColumnInfo
    private int order;

    @Ignore
    private boolean isPlaying;

    public Song(String title, String artists, String thumbnailURL, int localAudioFileId, int order) {
        this.title = title;
        this.artists = artists;
        this.thumbnailURL = thumbnailURL;
        this.isPlaying = false;
        this.localAudioFileId = localAudioFileId;
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public int getLocalAudioFileId() {
        return localAudioFileId;
    }

    public void setLocalAudioFileId(int localAudioFileId) {
        this.localAudioFileId = localAudioFileId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
