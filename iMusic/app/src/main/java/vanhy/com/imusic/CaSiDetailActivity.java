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

public class CaSiDetailActivity extends AppCompatActivity {

    private ListView listview;
    private ImageView btnBack;
    private ImageView btnMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ca_si_detail);
        listview = (ListView) findViewById(R.id.listViewBaiHat);
        ArrayList<BaiHat> list = new ArrayList<BaiHat>();
        BaiHatInPlaylistAdapter adapter = new BaiHatInPlaylistAdapter(this, R.layout.bai_hat_in_playlist_item, list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CaSiDetailActivity.this, NgheNhacActivity.class);
                startActivity(intent);
            }
        });

        btnMore = (ImageView) findViewById(R.id.btnImageMore);
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog dialog = new BottomSheetDialog(CaSiDetailActivity.this);
                Context context = CaSiDetailActivity.this;
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.bottom_sheet_casi_detail, null);
                dialog.setContentView(view);
                dialog.show();
            }
        });
    }
}
