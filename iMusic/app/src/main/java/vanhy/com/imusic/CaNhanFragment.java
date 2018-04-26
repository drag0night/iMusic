package vanhy.com.imusic;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class CaNhanFragment extends Fragment {


    public CaNhanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ca_nhan, container, false);
        TabLayout tab = (TabLayout) view.findViewById(R.id.tabLayoutCaNhan);
        tab.addTab(tab.newTab().setText("Bài Hát"));
        tab.addTab(tab.newTab().setText("Playlist"));
        tab.addTab(tab.newTab().setText("Ca Sĩ"));
        tab.addTab(tab.newTab().setText("Album"));
        tab.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewpager = (ViewPager) view.findViewById(R.id.viewPagerCaNhan);
        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager(), tab.getTabCount());
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

}
