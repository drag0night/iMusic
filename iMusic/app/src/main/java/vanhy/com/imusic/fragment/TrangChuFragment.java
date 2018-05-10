package vanhy.com.imusic.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import vanhy.com.imusic.Config;
import vanhy.com.imusic.R;
import vanhy.com.imusic.VolleySingleton;
import vanhy.com.imusic.adapter.HomeRecycleAdapter;
import vanhy.com.imusic.model.BaiHat;
import vanhy.com.imusic.request.SoundcloudApiRequest;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrangChuFragment extends Fragment {
    private RecyclerView countryRV;
    private RecyclerView edmRV;
    private RecyclerView rockRV;
    private RecyclerView popRV;
    private LinearLayout layout_home;
    private Activity context;
    private ImageButton button;
    private Button editsearch;
    private ProgressBar progress_load;

    private RecyclerView.Adapter countryA;
    private RecyclerView.Adapter edmA;
    private RecyclerView.Adapter rockA;
    private RecyclerView.Adapter popA;
    private ArrayList<BaiHat> countryL;
    private ArrayList<BaiHat> popL;
    private ArrayList<BaiHat> rockL;
    private ArrayList<BaiHat> edmL;
    public TrangChuFragment() {
        // Required empty public constructor
    }
    HomeFragment home;
    public void setHomeFragment(HomeFragment homef){home=homef;}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=getActivity();
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);
        button= (ImageButton) view.findViewById(R.id.buttonSearch);
        editsearch= (Button) view.findViewById(R.id.search_button);
        progress_load = (ProgressBar) view.findViewById(R.id.pb_main_loader);
        layout_home = (LinearLayout) view.findViewById(R.id.layout_home);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            home.changeSearch();
            }
        });
        editsearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            home.changeSearch();
            }
        });
        getSongList(Config.POP);
        getSongList(Config.ROCK);
        getSongList(Config.DANCEEDM);
        getSongList(Config.COUNTRY);

        layout_home.setVisibility(View.GONE);
        progress_load.setVisibility(View.VISIBLE);
        popL=new ArrayList<BaiHat>();
        rockL=new ArrayList<BaiHat>();
        edmL=new ArrayList<BaiHat>();
        countryL=new ArrayList<BaiHat>();
        //
        popRV = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        popRV.setLayoutManager(horizontalLayoutManager);
        popA = new HomeRecycleAdapter(getActivity(),popL);
        popRV.setAdapter(popA);
        //
        rockRV = (RecyclerView) view.findViewById(R.id.recyclerView1);
        LinearLayoutManager horizontalLayoutManager1
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rockRV.setLayoutManager(horizontalLayoutManager1);
        rockA = new HomeRecycleAdapter(getActivity(),rockL);
        rockRV.setAdapter(rockA);
        //
        countryRV = (RecyclerView) view.findViewById(R.id.recyclerView2);
        LinearLayoutManager horizontalLayoutManager2
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        countryRV.setLayoutManager(horizontalLayoutManager2);
        countryA = new HomeRecycleAdapter(getActivity(),countryL);
        countryRV.setAdapter(countryA);
        //
        edmRV = (RecyclerView) view.findViewById(R.id.recyclerView3);
        LinearLayoutManager horizontalLayoutManager3
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        edmRV.setLayoutManager(horizontalLayoutManager3);
        edmA = new HomeRecycleAdapter(getActivity(),edmL);
        edmRV.setAdapter(edmA);

        return view;
    }


    public void getSongList(final String query){
        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();
        SoundcloudApiRequest request = new SoundcloudApiRequest(queue);
        request.getTopMusic(query, new SoundcloudApiRequest.SoundcloudInterface() {
            @Override
            public void onSuccess( Object songs) {
                switch(query){
                    case Config.POP:
                        popL.clear();
                        popL.addAll((ArrayList<BaiHat>)songs);
                        popA.notifyDataSetChanged();
                        break;
                    case Config.ROCK:
                        rockL.clear();
                        rockL.addAll((ArrayList<BaiHat>)songs);
                        rockA.notifyDataSetChanged();
                        break;
                    case Config.DANCEEDM:
                        edmL.clear();
                        edmL.addAll((ArrayList<BaiHat>)songs);
                        edmA.notifyDataSetChanged();
                        break;
                    case Config.COUNTRY:
                        countryL.clear();
                        countryL.addAll((ArrayList<BaiHat>)songs);
                        countryA.notifyDataSetChanged();
                        break;
                }
                layout_home.setVisibility(View.VISIBLE);
                progress_load.setVisibility(View.GONE);
            }

            @Override
            public void onError(String message) {
                progress_load.setVisibility(View.GONE);
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
