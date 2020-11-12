package com.example.mentalhealthtracker.music;

/**
 * Holds a Song item. Contains metadata about the song. The media player can use this information
 * to display some information to the user about the type of song they're playing.
 */
public class SongListItem {
    private final int thumbnail;
    private final String thumbnailURL;
    private final String title;
    private final String artists;
    private boolean isPlaying;

    public SongListItem(int thumbnail, String thumbnailURL, String title, String artists) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.artists = artists;
        this.thumbnailURL = thumbnailURL;
        this.isPlaying = false;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public String getArtists() {
        return artists;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
