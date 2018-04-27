package vanhy.com.imusic;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageButton;

import vanhy.com.imusic.adapter.PagerMainAdapter;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnImageMore;
    private TabLayout tabMain;
    private ViewPager pagerMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnImageMore = (ImageButton) findViewById(R.id.btnImageMore);
        tabMain = (TabLayout) findViewById(R.id.tabLayoutMain);
        tabMain.addTab(tabMain.newTab().setIcon(R.drawable.ic_home));
        tabMain.addTab(tabMain.newTab().setIcon(R.drawable.ic_people));
        pagerMain = (ViewPager) findViewById(R.id.viewPagerMain);

        PagerMainAdapter adaptermain = new PagerMainAdapter(getSupportFragmentManager(), tabMain.getTabCount());
        pagerMain.setAdapter(adaptermain);
        pagerMain.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabMain));
        tabMain.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pagerMain.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.popup_menu_detail, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
}
