package vanhy.com.imusic.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import vanhy.com.imusic.fragment.SearchAlbumFragment;
import vanhy.com.imusic.fragment.SearchResultFragment;

public class SearchPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private String keyword;



    public SearchPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    public void setKeyword(String _adapter){
        keyword=_adapter;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle;
        switch (position) {
            case 0:
                SearchResultFragment tab = new SearchResultFragment();
                bundle = new Bundle();
                bundle.putSerializable("keyword", keyword);
                tab.setArguments(bundle);
                //tab.adapter.notifyDataSetChanged();
                return tab;
            case 1:
                SearchAlbumFragment tab1 = new SearchAlbumFragment();
                bundle = new Bundle();
                bundle.putSerializable("keyword", keyword);
                tab1.setArguments(bundle);
                //tab.adapter.notifyDataSetChanged();
                return tab1;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}