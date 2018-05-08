package vanhy.com.imusic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vanhy.com.imusic.R;

/**
 * Created by Admin on 4/27/2018.
 */

public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    public void changeHome(){
        TrangChuFragment frag=new TrangChuFragment();
        frag.setHomeFragment(this);
        getFragmentManager().beginTransaction()
                .replace(R.id.homelayout, frag)
                .addToBackStack(null)
                .commit();

    }

    public void changeSearch(){
        SearchViewFragment frag=new SearchViewFragment();
        frag.setHomeFragment(this);
        getFragmentManager().beginTransaction()
                .replace(R.id.homelayout, frag)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home, container, false);
        changeHome();

        return view;
    }

}
