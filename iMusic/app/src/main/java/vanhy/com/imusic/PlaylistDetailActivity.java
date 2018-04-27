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

import java.util.ArrayList;

import vanhy.com.imusic.adapter.BaiHatInPlaylistAdapter;
import vanhy.com.imusic.model.BaiHat;

public class PlaylistDetailActivity extends AppCompatActivity {

    private ListView listview;
    private ArrayList<BaiHat> listBh;
    private ImageView btnBack;
    private ImageView btnMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_detail);

        listview = (ListView) findViewById(R.id.listViewBaiHat);
        btnBack = (ImageView) findViewById(R.id.btnImageBack);
        btnMore = (ImageView) findViewById(R.id.btnImageMore);

        listBh = new ArrayList<BaiHat>();
        listBh.add(new BaiHat("1 2 3 4","CHI DÂN"));
        listBh.add(new BaiHat("Ta còn yêu nhau", "ĐỨC PHÚC"));
        listBh.add(new BaiHat("Năm ấy", "ĐỨC PHÚC"));
        listBh.add(new BaiHat("Mặt trời của em","PHƯƠNG LY ft JUSTATEE"));
        listBh.add(new BaiHat("Đã lỡ yêu em nhiều", "JUSTATEE"));
        listBh.add(new BaiHat("Chạm khẽ tim anh một chút thôi","NOO PHƯỚC THỊNH"));
        listBh.add(new BaiHat("Vợ người ta", "PHAN MẠNH QUỲNH"));
        listBh.add(new BaiHat("Ngắm hoa lệ rơi","HOA VINH"));
        BaiHatInPlaylistAdapter adapter = new BaiHatInPlaylistAdapter(this, R.layout.bai_hat_in_playlist_item, listBh);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PlaylistDetailActivity.this, NgheNhacActivity.class);
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
    }
}
