package vanhy.com.imusic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerMainAdapter extends FragmentStatePagerAdapter{
    private static int numOfTabs;

    public PagerMainAdapter(FragmentManager fm, int num) {
        super(fm);
        this.numOfTabs = num;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return new TrangChuFragment();
            }

            case 1: {
                return new CaNhanFragment();
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
