package vanhy.com.imusic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

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
                return new BaiHatFragment();
            }

            case 1: {
                return new PlayListFragment();
            }

            case 2: {
                return new CaSiFragment();
            }

            case 3: {
                return new AlbumFragment();
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
