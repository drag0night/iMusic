package vanhy.com.imusic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import vanhy.com.imusic.adapter.ChonBaiHatAdapter;
import vanhy.com.imusic.model.BaiHat;

public class AddSongToNewPlaylist extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song_to_new_playlist);
        listView = (ListView) findViewById(R.id.listViewBaiHat);
        ArrayList<BaiHat> list = new ArrayList<BaiHat>();
        list.add(new BaiHat("1 2 3 4","CHI DÂN"));
        list.add(new BaiHat("Ta còn yêu nhau", "ĐỨC PHÚC"));
        list.add(new BaiHat("Năm ấy", "ĐỨC PHÚC"));
        list.add(new BaiHat("Mặt trời của em","PHƯƠNG LY ft JUSTATEE"));
        list.add(new BaiHat("Đã lỡ yêu em nhiều", "JUSTATEE"));
        list.add(new BaiHat("Chạm khẽ tim anh một chút thôi","NOO PHƯỚC THỊNH"));
        list.add(new BaiHat("Vợ người ta", "PHAN MẠNH QUỲNH"));
        list.add(new BaiHat("Ngắm hoa lệ rơi","HOA VINH"));
        ChonBaiHatAdapter adapter = new ChonBaiHatAdapter(this, R.layout.chon_bai_hat_item, list);
        listView.setAdapter(adapter);
    }
}
