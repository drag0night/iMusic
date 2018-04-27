package vanhy.com.imusic.model;

public class Album {
    private String ten;
    private int sl;

    public Album(String ten, int sl) {
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
