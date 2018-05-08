package vanhy.com.imusic.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import vanhy.com.imusic.NgheNhacActivity;
import vanhy.com.imusic.R;
import vanhy.com.imusic.VolleySingleton;
import vanhy.com.imusic.adapter.BaiHatAdapter;
import vanhy.com.imusic.model.BaiHat;
import vanhy.com.imusic.request.SoundcloudApiRequest;


/**
 * A simple {@link Fragment} subclass.
 */
public class BaiHatFragment extends Fragment {

    private Activity context;
    private  ListView listview;
    private static final String TAG = "APP";
    private RecyclerView recycler;
    private BaiHatAdapter adapter;
    private ArrayList<BaiHat> songList;
    private TextView textTenbh, textCasi;
    private ImageView imgBh;
    private static final BaiHatFragment instance = new BaiHatFragment();
    private ProgressBar progressBar;

    public BaiHatFragment() {
        // Required empty public constructor
    }

    public static BaiHatFragment getInstance() {
        return instance;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bai_hat, container, false);
        context = getActivity();
        listview = (ListView) view.findViewById(R.id.listViewBaiHat);
        progressBar = (ProgressBar) view.findViewById(R.id.pb_main_loader);
        songList = new ArrayList<BaiHat>();
        getSongList("Đức Phúc");
        adapter = new BaiHatAdapter(context, R.layout.playlist_item, songList);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, NgheNhacActivity.class);
                intent.putExtra("songList", songList);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void prepareSong(BaiHat baiHat) {
        textTenbh.setVisibility(View.GONE);
        textTenbh.setText(baiHat.getTitle());
        textCasi.setText(baiHat.getArtist());
    }

    public void getSongList(String query){
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();
        SoundcloudApiRequest request = new SoundcloudApiRequest(queue);
        request.getSongList(query, new SoundcloudApiRequest.SoundcloudInterface() {
            @Override
            public void onSuccess(ArrayList<BaiHat> songs) {
                progressBar.setVisibility(View.GONE);
                songList.clear();
                songList.addAll(songs);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
