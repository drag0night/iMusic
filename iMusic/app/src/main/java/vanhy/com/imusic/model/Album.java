package vanhy.com.imusic.model;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable{
    private long id;
    private String title;
    private String artist;
    private String artworkUrl;
    private long track_count;
    private ArrayList<BaiHat> listBh;

    public Album(long id, String title, String artist, String artworkUrl, long trackcount) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.artworkUrl = artworkUrl;
        this.track_count = trackcount;
    }

    public Album(long id, String title, String artist, String artworkUrl, long track_count, ArrayList<BaiHat> listBh) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.artworkUrl = artworkUrl;
        this.track_count = track_count;
        this.listBh = listBh;
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

    public long getTrachCount() {
        return track_count;
    }

    public ArrayList<BaiHat> getListBh() {
        return listBh;
    }

    public void setListBh(ArrayList<BaiHat> listBh) {
        this.listBh = listBh;
    }
}
