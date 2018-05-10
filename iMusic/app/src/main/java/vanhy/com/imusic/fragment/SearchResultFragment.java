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

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import vanhy.com.imusic.NgheNhacActivity;
import vanhy.com.imusic.R;
import vanhy.com.imusic.VolleySingleton;
import vanhy.com.imusic.adapter.BaiHatAdapter;
import vanhy.com.imusic.model.BaiHat;
import vanhy.com.imusic.request.SoundcloudApiRequest;

public class SearchResultFragment extends Fragment {
    private BaseAdapter adapter;

    private Context context;
    private ArrayList<BaiHat> songList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bai_hat, container, false);
        ListView list=view.findViewById(R.id.listViewBaiHat);
        context=getActivity();
        songList=new ArrayList<BaiHat>();
        Bundle bundle = getArguments();
        String keyword= (String) bundle.getSerializable("keyword");

        filter(keyword);
        adapter= new BaiHatAdapter(getActivity(), R.layout.playlist_item,songList);


        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, NgheNhacActivity.class);
                intent.putExtra("songList", songList);
                intent.putExtra("position", position);
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
        request.getSongList(query, new SoundcloudApiRequest.SoundcloudInterface() {
            @Override
            public void onSuccess( Object songs) {
                songList.clear();
                songList.addAll((ArrayList<BaiHat>)songs);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onError(String message) {
                //Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
