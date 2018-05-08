package vanhy.com.imusic.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import vanhy.com.imusic.HistoryLoader;
import vanhy.com.imusic.R;

public class SearchViewFragment extends Fragment implements SearchView.OnQueryTextListener {

    // Declare Variables
    //ListView list;
    HomeFragment home;
    public void setHomeFragment(HomeFragment homef){home=homef;}
    Button button;
    SearchView editsearch;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_view, container, false);



        // Pass results to SearchSongAdapter Class
        // Binds the Adapter to the ListView
        //list.setAdapter(adapter);

        // Locate the EditText in listview_main.xml
        editsearch = view.findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);
        button=view.findViewById(R.id.search_button);
        editsearch.setFocusable(true);
        editsearch.requestFocus();
        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(editsearch.getWindowToken(), 0);
            home.changeHome();
            }
        });
        SearchHistoryFragment frag=new SearchHistoryFragment();
        frag.setSearchView(this);
        getFragmentManager().beginTransaction()
                .replace(R.id.frameLayout,frag)
                .addToBackStack(null)
                .commit();
        return view;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
//        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        mgr.hideSoftInputFromWindow(editsearch.getWindowToken(), 0);
        SearchResultTabViewFragment wipeViewFragment=new SearchResultTabViewFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("keyword", query);
        wipeViewFragment.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, wipeViewFragment)
                .addToBackStack(null)
                .commit();
        HistoryLoader.getInstance().addHistory(query,getActivity());

        return false;
    }

    public void doSearch(String newText){
//        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        mgr.hideSoftInputFromWindow(editsearch.getWindowToken(), 0);
        editsearch.setQuery(newText,false);
        if(newText.length()>1){

            SearchResultTabViewFragment wipeViewFragment=new SearchResultTabViewFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("keyword", newText);
            wipeViewFragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, wipeViewFragment)
                    .addToBackStack(null)
                    .commit();
            //SearchResultFragment.adapter.notifyDataSetChanged();

            //editsearch.setIconified(false);
        }
        //editsearch.clearFocus();

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText==null) newText="";
        if(newText.length()==0){
            SearchHistoryFragment frag=new SearchHistoryFragment();
            frag.setSearchView(this);
            getFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout,frag)
                    .addToBackStack(null)
                    .commit();
        }
        else if(newText.length()>1&&newText.charAt(newText.length()-1)==' '){

            SearchResultTabViewFragment wipeViewFragment=new SearchResultTabViewFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("keyword", newText);
            wipeViewFragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, wipeViewFragment)
                    .addToBackStack(null)
                    .commit();
            //SearchResultFragment.adapter.notifyDataSetChanged();

            //editsearch.setIconified(false);

        }
        return false;
    }
}
