package vanhy.com.imusic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import vanhy.com.imusic.fragment.BaiHatFragment;
import vanhy.com.imusic.fragment.AlbumFragment;
import vanhy.com.imusic.fragment.FavoriteFragment;
import vanhy.com.imusic.fragment.PlayListFragment;

public class PagerAdapter extends FragmentStatePagerAdapter{

    private static int numOfTabs;

    public PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return BaiHatFragment.getInstance();
            }

            case 1: {
                return AlbumFragment.getInstance();
            }

            case 2: {
                return PlayListFragment.getInstance();
            }

            case 3: {
                return FavoriteFragment.getInstance();
            }

            default: {
                return null;
            }
        }
    }

    @Override
    public int getCount() {
        return this.numOfTabs;
    }
}
