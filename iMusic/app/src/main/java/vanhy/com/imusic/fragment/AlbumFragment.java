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
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import vanhy.com.imusic.AlbumDetailActivity;
import vanhy.com.imusic.CaSiDetailActivity;
import vanhy.com.imusic.PlaylistDetailActivity;
import vanhy.com.imusic.R;
import vanhy.com.imusic.VolleySingleton;
import vanhy.com.imusic.adapter.AlbumAdapter;
import vanhy.com.imusic.adapter.CaSiAdapter;
import vanhy.com.imusic.model.Album;
import vanhy.com.imusic.model.CaSi;
import vanhy.com.imusic.request.SoundcloudApiRequest;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment {


    private Activity context;
    private ListView listview;
    private ArrayList<Album> list;
    private AlbumAdapter adapter;
    private static final AlbumFragment instance = new AlbumFragment();

    public AlbumFragment() {
        // Required empty public constructor
    }

    public static AlbumFragment getInstance() {
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        context = getActivity();
        listview = (ListView) view.findViewById(R.id.listViewAlbum);
        list = new ArrayList<Album>();
        filter("Hot 2018");
        adapter = new AlbumAdapter(context, list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, AlbumDetailActivity.class);
                intent.putExtra("playlist", list.get(position));
                startActivity(intent);
            }
        });
        return view;
    }

    public void filter(String query){

        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();
        SoundcloudApiRequest request = new SoundcloudApiRequest(queue);
        request.getAlbumList(query, new SoundcloudApiRequest.SoundcloudInterface() {
            @Override
            public void onSuccess(Object songs) {
                list.clear();
                list.addAll( (ArrayList<Album>) songs);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
