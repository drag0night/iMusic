package vanhy.com.imusic.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import vanhy.com.imusic.AlbumDetailActivity;
import vanhy.com.imusic.R;
import vanhy.com.imusic.adapter.AlbumAdapter;
import vanhy.com.imusic.model.Album;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment {

    private Activity context;
    private ListView listview;
    private ArrayList<Album> list;


    public AlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        context = getActivity();
        listview = (ListView) view.findViewById(R.id.listViewAlbum);
        list = new ArrayList<Album>();
        list.add(new Album("TA CÒN YÊU NHAU", 2));
        list.add(new Album("MẶT TRỜI CỦA EM", 1));
        list.add(new Album("HOA VINH OFFICAL", 1));
        AlbumAdapter adapter = new AlbumAdapter(context, R.layout.album_item, list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, AlbumDetailActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
