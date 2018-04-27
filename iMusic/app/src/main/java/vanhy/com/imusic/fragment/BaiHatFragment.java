package vanhy.com.imusic.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import vanhy.com.imusic.NgheNhacActivity;
import vanhy.com.imusic.R;
import vanhy.com.imusic.adapter.BaiHatAdapter;
import vanhy.com.imusic.adapter.PlaylistAdapter;
import vanhy.com.imusic.model.BaiHat;
import vanhy.com.imusic.model.Playlist;


/**
 * A simple {@link Fragment} subclass.
 */
public class BaiHatFragment extends Fragment {

    private Activity context;
    private  ListView listview;

    public BaiHatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bai_hat, container, false);
        context = getActivity();
        ArrayList<BaiHat> list = new ArrayList<BaiHat>();
        list.add(new BaiHat("1 2 3 4","CHI DÂN"));
        list.add(new BaiHat("Ta còn yêu nhau", "ĐỨC PHÚC"));
        list.add(new BaiHat("Năm ấy", "ĐỨC PHÚC"));
        list.add(new BaiHat("Mặt trời của em","PHƯƠNG LY ft JUSTATEE"));
        list.add(new BaiHat("Đã lỡ yêu em nhiều", "JUSTATEE"));
        list.add(new BaiHat("Chạm khẽ tim anh một chút thôi","NOO PHƯỚC THỊNH"));
        list.add(new BaiHat("Vợ người ta", "PHAN MẠNH QUỲNH"));
        list.add(new BaiHat("Ngắm hoa lệ rơi","HOA VINH"));
        listview = (ListView) view.findViewById(R.id.listViewBaiHat);
        BaiHatAdapter adapter = new BaiHatAdapter(context, R.layout.playlist_item, list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, NgheNhacActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
