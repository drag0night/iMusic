package vanhy.com.imusic.model;

import java.util.ArrayList;

public class Playlist {

    private String ten;
    private int sl;

    public Playlist(String ten, int sl) {
        this.ten = ten;
        this.sl = sl;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }
}
