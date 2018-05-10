package vanhy.com.imusic;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vanhy.com.imusic.adapter.BaiHatInPlaylistAdapter;
import vanhy.com.imusic.model.Album;
import vanhy.com.imusic.model.BaiHat;
import vanhy.com.imusic.request.SoundcloudApiRequest;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumDetailActivity extends AppCompatActivity {

    private ListView listview;
    private ArrayList<BaiHat> listBh;
    private ProgressBar progressBar;
    private LinearLayout layout_list_track;
    private ImageView btnBack;
    private ImageView btnMore;
    private Album playlist;
    private ImageView imgPlaylist;
    private TextView txtTenpl;
    private BaiHatInPlaylistAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_detail);
        progressBar = (ProgressBar) findViewById(R.id.pb_main_loader);
        layout_list_track = (LinearLayout) findViewById(R.id.layout_list_track);
        layout_list_track.setVisibility(View.GONE);
        listview = (ListView) findViewById(R.id.listViewBaiHat);
        btnBack = (ImageView) findViewById(R.id.btnImageBack);
        btnMore = (ImageView) findViewById(R.id.btnImageMore);
        imgPlaylist = (ImageView) findViewById(R.id.imgPlaylist);
        txtTenpl = (TextView) findViewById(R.id.txtTenpl);
        listBh=new ArrayList<BaiHat>();
        playlist = (Album) getIntent().getSerializableExtra("playlist");
        Picasso.with(this).load(playlist.getArtworkUrl()).placeholder(R.drawable.music_placeholder).into(imgPlaylist);
        txtTenpl.setText(playlist.getTitle());
        ////////listBh = playlist.getListBh();
        getSongList(Long.toString(playlist.getId()));
        adapter = new BaiHatInPlaylistAdapter(this, R.layout.bai_hat_in_playlist_item, listBh);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AlbumDetailActivity.this, NgheNhacActivity.class);
                intent.putExtra("songList", listBh);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void getSongList(String query){
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue = VolleySingleton.getInstance(this).getRequestQueue();
        SoundcloudApiRequest request = new SoundcloudApiRequest(queue);
        request.getAlbum(query, new SoundcloudApiRequest.SoundcloudInterface() {
            @Override
            public void onSuccess( Object songs) {
                listBh.clear();
                listBh.addAll((ArrayList<BaiHat>)songs);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                layout_list_track.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(String message) {
                progressBar.setVisibility(View.GONE);
                layout_list_track.setVisibility(View.VISIBLE);
                //Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
