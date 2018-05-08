package vanhy.com.imusic.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import vanhy.com.imusic.R;
import vanhy.com.imusic.adapter.SearchHistoryAdapter;

public class SearchHistoryFragment extends Fragment {
    private SearchViewFragment searchView;
    private Activity context;
    private ListView listview;

    public void setSearchView(SearchViewFragment svf){
        searchView=svf;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_history, container, false);
        listview = view.findViewById(R.id.listViewBaiHat);
        SearchHistoryAdapter adapter = new SearchHistoryAdapter(getActivity());
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            searchView.doSearch(parent.getItemAtPosition(position).toString());
            }
        });
        return view;
    }
}