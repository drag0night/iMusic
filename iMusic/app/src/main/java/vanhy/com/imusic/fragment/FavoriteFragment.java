package vanhy.com.imusic.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import vanhy.com.imusic.NgheNhacActivity;
import vanhy.com.imusic.OnAddedToDB;
import vanhy.com.imusic.R;
import vanhy.com.imusic.SQLite.SQLite;
import vanhy.com.imusic.adapter.BaiHatAdapter;
import vanhy.com.imusic.adapter.FavoriteAdapter;
import vanhy.com.imusic.model.BaiHat;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements OnAddedToDB{

    private ListView listViewFavorite;

    private static Context context;
    private ArrayList<BaiHat> songList;
    private static final FavoriteFragment instance = new FavoriteFragment();
    private FavoriteAdapter adapter;

    public static FavoriteFragment getInstance() {
        return instance;
    }


    public FavoriteFragment() {
        // Required empty public constructor
    }

    public Context getContext() {
        return context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        context = getActivity();
        initView(view);
        songList = SQLite.getAllFavorite(context);
        adapter = new FavoriteAdapter(context, R.layout.bai_hat_in_playlist_item, songList);
        listViewFavorite.setAdapter(adapter);
        itemclick(listViewFavorite);
        return view;
    }

    private void initView(View view) {
        listViewFavorite = (ListView) view.findViewById(R.id.listViewFavorite);
    }

    private void itemclick(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, NgheNhacActivity.class);
                intent.putExtra("songList", songList);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRefresh() {
        songList = SQLite.getAllFavorite(context);
        adapter = new FavoriteAdapter(context, R.layout.bai_hat_in_playlist_item, songList);
        listViewFavorite.setAdapter(adapter);
    }
}
