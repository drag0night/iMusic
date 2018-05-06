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

import vanhy.com.imusic.CaSiDetailActivity;
import vanhy.com.imusic.R;
import vanhy.com.imusic.adapter.CaSiAdapter;
import vanhy.com.imusic.model.CaSi;


/**
 * A simple {@link Fragment} subclass.
 */
public class CaSiFragment extends Fragment {


    private Activity context;
    private ListView listview;
    private ArrayList<CaSi> list;
    private static final CaSiFragment instance = new CaSiFragment();

    public CaSiFragment() {
        // Required empty public constructor
    }

    public static CaSiFragment getInstance() {
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ca_si, container, false);
        context = getActivity();
        listview = (ListView) view.findViewById(R.id.listViewcaSi);
        list = new ArrayList<CaSi>();
        list.add(new CaSi("CHI DÂN", 1));
        list.add(new CaSi("ĐỨC PHÚC", 2));
        list.add(new CaSi("PHƯƠNG LY ft JUSTATEE", 1));
        list.add(new CaSi("JUSTATEE", 1));
        list.add(new CaSi("NOO PHƯỚC THỊNH", 1));
        list.add(new CaSi("PHAN MẠNH QUỲNH", 1));
        list.add(new CaSi("HOA VINH", 1));
        CaSiAdapter adapter = new CaSiAdapter(context, R.layout.ca_si_item, list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, CaSiDetailActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
