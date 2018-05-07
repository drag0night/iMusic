package vanhy.com.imusic;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vanhy.com.imusic.adapter.BaiHatInPlaylistAdapter;
import vanhy.com.imusic.model.BaiHat;
import vanhy.com.imusic.model.Playlist;

public class PlaylistDetailActivity extends AppCompatActivity {

    private ListView listview;
    private ArrayList<BaiHat> listBh;
    private ImageView btnBack;
    private ImageView btnMore;
    private Playlist playlist;
    private ImageView imgPlaylist;
    private TextView txtTenpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_detail);

        listview = (ListView) findViewById(R.id.listViewBaiHat);
        btnBack = (ImageView) findViewById(R.id.btnImageBack);
        btnMore = (ImageView) findViewById(R.id.btnImageMore);
        imgPlaylist = (ImageView) findViewById(R.id.imgPlaylist);
        txtTenpl = (TextView) findViewById(R.id.txtTenpl);

        playlist = (Playlist) getIntent().getSerializableExtra("playlist");
        if (playlist.getListBh().size() == 0) {
            imgPlaylist.setImageResource(R.drawable.music_placeholder);
        } else {
            Picasso.with(this).load(playlist.getListBh().get(0).getArtworkUrl()).placeholder(R.drawable.music_placeholder).into(imgPlaylist);
        }
        txtTenpl.setText(playlist.getTen());
        listBh = playlist.getListBh();
        BaiHatInPlaylistAdapter adapter = new BaiHatInPlaylistAdapter(this, R.layout.bai_hat_in_playlist_item, listBh);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PlaylistDetailActivity.this, NgheNhacActivity.class);
                intent.putExtra("songList", listBh);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = PlaylistDetailActivity.this;
                BottomSheetDialog dialog = new BottomSheetDialog(context);
                View view = ((LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.bottom_sheet_list_playlist, null);
                dialog.setContentView(view);
                dialog.show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
