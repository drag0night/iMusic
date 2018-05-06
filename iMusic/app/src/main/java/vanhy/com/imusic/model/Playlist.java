package vanhy.com.imusic.model;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Playlist implements Comparable<BaiHat>, Serializable {

    private int id;
    private String ten;
    private ArrayList<BaiHat> listBh;

    public Playlist(int id, String ten, ArrayList<BaiHat> listBh) {
        this.id = id;
        this.ten = ten;
        this.listBh = listBh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public ArrayList<BaiHat> getListBh() {
        return listBh;
    }

    public void setListBh(ArrayList<BaiHat> listBh) {
        this.listBh = listBh;
    }

    @Override
    public int compareTo(@NonNull BaiHat o) {
        return 0;
    }
}
