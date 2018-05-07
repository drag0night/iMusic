package vanhy.com.imusic.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;

import vanhy.com.imusic.R;
import vanhy.com.imusic.adapter.HomeRecycleAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrangChuFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView1;
    private RecyclerView mRecyclerView2;
    private RecyclerView mRecyclerView3;

    Button button;
    Button editsearch;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mAdapter1;
    private RecyclerView.Adapter mAdapter2;
    private RecyclerView.Adapter mAdapter3;

    public TrangChuFragment() {
        // Required empty public constructor
    }
    HomeFragment home;
    public void setHomeFragment(HomeFragment homef){home=homef;}

    ArrayList<String> personNames = new ArrayList<String>(Arrays.asList("Person 1", "Person 2", "Person 3", "Person 4", "Person 5", "Person 6", "Person 7","Person 8", "Person 9", "Person 10", "Person 11", "Person 12", "Person 13", "Person 14"));
    ArrayList<Integer> personImages = new ArrayList<Integer>(Arrays.asList(
            R.mipmap.ducphuc, R.mipmap.ducphuc, R.mipmap.ducphuc, R.mipmap.ducphuc,
            R.mipmap.ducphuc, R.mipmap.ducphuc, R.mipmap.ducphuc, R.mipmap.ducphuc,
            R.mipmap.ducphuc, R.mipmap.ducphuc, R.mipmap.ducphuc, R.mipmap.ducphuc,
            R.mipmap.ducphuc, R.mipmap.ducphuc));


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_trang_chu, container, false);
        button=view.findViewById(R.id.button);
        editsearch=view.findViewById(R.id.search_button);
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
        //
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(horizontalLayoutManager);
        mAdapter = new HomeRecycleAdapter(getActivity(),personNames,personImages);
        mRecyclerView.setAdapter(mAdapter);
        //
        mRecyclerView1 = (RecyclerView) view.findViewById(R.id.recyclerView1);
        LinearLayoutManager horizontalLayoutManager1
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView1.setLayoutManager(horizontalLayoutManager1);
        mAdapter1 = new HomeRecycleAdapter(getActivity(),personNames,personImages);
        mRecyclerView1.setAdapter(mAdapter);
        //
        mRecyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerView2);
        LinearLayoutManager horizontalLayoutManager2
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView2.setLayoutManager(horizontalLayoutManager2);
        mAdapter2 = new HomeRecycleAdapter(getActivity(),personNames,personImages);
        mRecyclerView2.setAdapter(mAdapter);
        //
        mRecyclerView3 = (RecyclerView) view.findViewById(R.id.recyclerView3);
        LinearLayoutManager horizontalLayoutManager3
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView3.setLayoutManager(horizontalLayoutManager3);
        mAdapter3 = new HomeRecycleAdapter(getActivity(),personNames,personImages);
        mRecyclerView3.setAdapter(mAdapter);

        return view;
    }

}
