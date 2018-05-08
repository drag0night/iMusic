package vanhy.com.imusic;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import vanhy.com.imusic.SQLite.SQLite;
import vanhy.com.imusic.adapter.BaiHatInPlaylistAdapter;
import vanhy.com.imusic.fragment.PlayListFragment;
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
                final Context context = PlaylistDetailActivity.this;
                final BottomSheetDialog dialog = new BottomSheetDialog(context);
                View view = ((LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.bottom_sheet_list_playlist, null);
                ImageView imgPl = (ImageView) view.findViewById(R.id.imageView_BoS) ;
                TextView textTenPl = (TextView) view.findViewById(R.id.textViewTenPlaylist_BoS);
                TextView textSl = (TextView) view.findViewById(R.id.textViewSL_BoS);
                LinearLayout btnEdit = (LinearLayout) view.findViewById(R.id.btnImageEdit);
                LinearLayout btnXoa = (LinearLayout) view.findViewById(R.id.btnImageXoa);
                if (listBh.size() == 0) {
                    imgPl.setImageResource(R.drawable.music_placeholder);
                } else {
                    Picasso.with(PlaylistDetailActivity.this).load(listBh.get(0).getArtworkUrl()).placeholder(R.drawable.music_placeholder).into(imgPl);
                }
                textTenPl.setText(playlist.getTen());
                textSl.setText(playlist.getListBh().size()+" bài hát");
                dialog.setContentView(view);
                dialog.show();
                btnXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (SQLite.deletePlaylist(PlaylistDetailActivity.this, playlist)) {
                            OnAddedToDB onAddedToDB = PlayListFragment.getInstance();
                            onAddedToDB.onRefresh();
                            Toast.makeText(PlaylistDetailActivity.this, "Xóa playlist thành công", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                        dialog.cancel();
                    }
                });
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        final Dialog dialog_edit = new Dialog(context);
                        dialog_edit.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        View temp = getLayoutInflater().inflate(R.layout.add_play_list_dialog, null);
                        TextView tieude = (TextView) temp.findViewById(R.id.textViewTieuDe);
                        final EditText edtTenpl = (EditText) temp.findViewById(R.id.edtTenPlaylist);
                        Button btnXacnhan = (Button) temp.findViewById(R.id.btnThemPlaylist);
                        Button btnBoqua = (Button) temp.findViewById(R.id.btnBoqua);
                        btnXacnhan.setText("Xác nhận");
                        tieude.setText("Sửa tên playlist");
                        dialog_edit.setContentView(temp);
                        dialog_edit.show();
                        btnXacnhan.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String tenpl = edtTenpl.getText().toString();
                                if (!"".equals(tenpl)) {
                                    if (SQLite.checkPlaylistNameExist(context, tenpl)) {
                                        Toast.makeText(context, "Playlist đã tồn tại", Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (SQLite.editNamePlaylist(context, playlist.getId(), tenpl)) {
                                            OnAddedToDB onAddedToDB = PlayListFragment.getInstance();
                                            onAddedToDB.onRefresh();
                                            txtTenpl.setText(tenpl);
                                            Toast.makeText(context, "Đã sửa tên playlist", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                dialog_edit.cancel();
                            }
                        });
                        btnBoqua.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog_edit.cancel();
                            }
                        });
                    }
                });
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
