package vanhy.com.imusic.model;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class BaiHat implements Comparable<BaiHat>, Serializable{

    private long id;
    private String title;
    private String artist;
    private String artworkUrl;
    private long duration;
    private String streamUrl;
    private int playbackCount;

    public BaiHat(long id, String title, String artist, String artworkUrl, long duration, String streamUrl, int playbackCount) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.artworkUrl = artworkUrl;
        this.duration = duration;
        this.streamUrl = streamUrl;
        this.playbackCount = playbackCount;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getArtworkUrl() {
        return artworkUrl;
    }

    public long getDuration() {
        return duration;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public int getPlaybackCount() {
        return playbackCount;
    }

    @Override
    public int compareTo(@NonNull BaiHat o) {
        return 0;
    }
}
