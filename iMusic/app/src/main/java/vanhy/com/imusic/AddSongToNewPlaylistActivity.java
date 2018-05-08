package vanhy.com.imusic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import vanhy.com.imusic.SQLite.SQLite;
import vanhy.com.imusic.adapter.ChonBaiHatAdapter;
import vanhy.com.imusic.fragment.PlayListFragment;
import vanhy.com.imusic.model.BaiHat;
import vanhy.com.imusic.request.SoundcloudApiRequest;

public class AddSongToNewPlaylistActivity extends AppCompatActivity {

    private ListView listView;
    private ImageButton btnDone;

    private String tenpl;
    private ArrayList<BaiHat> songList;
    private ArrayList<BaiHat> selectedList;
    private ChonBaiHatAdapter adapter;
    private static final String TAG = "APP";
    private OnAddedToDB onAddedToDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song_to_new_playlist);
        tenpl = getIntent().getStringExtra("tenpl");
        initView();
        selectedList = new ArrayList<BaiHat>();
        songList = new ArrayList<BaiHat>();
        getSongList("Đức Phúc");
        adapter = new ChonBaiHatAdapter(this, R.layout.chon_bai_hat_item, songList);
        listView.setAdapter(adapter);
        done();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listViewBaiHat);
        btnDone = (ImageButton) findViewById(R.id.btnImageDone);
    }

    private void done() {
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedList = adapter.getListSelected();
                SQLite.createPlaylist(AddSongToNewPlaylistActivity.this, tenpl, selectedList);
                onAddedToDB = PlayListFragment.getInstance();
                onAddedToDB.onRefresh();
                onBackPressed();
            }
        });
    }

    public void getSongList(String query){
        RequestQueue queue = VolleySingleton.getInstance(this).getRequestQueue();
        SoundcloudApiRequest request = new SoundcloudApiRequest(queue);
        request.getSongList(query, new SoundcloudApiRequest.SoundcloudInterface() {
            @Override
            public void onSuccess( Object songs) {
                songList.clear();
                songList.addAll((ArrayList<BaiHat>)songs);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(AddSongToNewPlaylistActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
