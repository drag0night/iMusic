package vanhy.com.imusic.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import vanhy.com.imusic.AlbumDetailActivity;
import vanhy.com.imusic.R;
import vanhy.com.imusic.VolleySingleton;
import vanhy.com.imusic.adapter.AlbumAdapter;
import vanhy.com.imusic.model.Album;
import vanhy.com.imusic.request.SoundcloudApiRequest;

public class SearchAlbumFragment extends Fragment {
    private BaseAdapter adapter;
    private ListView list;
    private Context context;
    private ArrayList<Album> songList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_list, container, false);
        list=view.findViewById(R.id.albumListView);
        context=getActivity();
        songList=new ArrayList<Album>();
        Bundle bundle = getArguments();
        String keyword= (String) bundle.getSerializable("keyword");

        filter(keyword);
        adapter= new AlbumAdapter(getActivity(),songList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, AlbumDetailActivity.class);
                intent.putExtra("playlist", songList.get(position));
                startActivity(intent);
            }
        });
        //adapter.notifyDataSetChanged();

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    public void filter(String query){

        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();
        SoundcloudApiRequest request = new SoundcloudApiRequest(queue);
        request.getAlbumList(query, new SoundcloudApiRequest.SoundcloudInterface() {
            @Override
            public void onSuccess(Object songs) {
                songList.clear();
                songList.addAll((ArrayList<Album>)songs);
                adapter.notifyDataSetChanged();
            }



            @Override
            public void onError(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
