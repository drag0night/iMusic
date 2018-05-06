package vanhy.com.imusic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import vanhy.com.imusic.adapter.ChonBaiHatAdapter;
import vanhy.com.imusic.model.BaiHat;

public class AddSongToNewPlaylistActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song_to_new_playlist);
        listView = (ListView) findViewById(R.id.listViewBaiHat);
        ArrayList<BaiHat> list = new ArrayList<BaiHat>();
        ChonBaiHatAdapter adapter = new ChonBaiHatAdapter(this, R.layout.chon_bai_hat_item, list);
        listView.setAdapter(adapter);
    }
}
