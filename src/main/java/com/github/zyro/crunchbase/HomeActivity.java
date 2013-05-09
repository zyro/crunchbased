package com.github.zyro.crunchbase;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import com.googlecode.androidannotations.annotations.*;

@EActivity(R.layout.home)
public class HomeActivity extends BaseActivity {

    /*GestureDetector.SimpleOnGestureListener simpleOnGestureListener
            = new GestureDetector.SimpleOnGestureListener(){

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            float sensitvity = 50;

            // TODO Auto-generated method stub
            if((e1.getX() - e2.getX()) >= sensitvity){
                Log.e("AndroidRuntime", "LEFT");
            }else if((e2.getX() - e1.getX()) >= sensitvity){
                Log.e("AndroidRuntime", "RIGHT");
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };

    GestureDetector gestureDetector
            = new GestureDetector(simpleOnGestureListener);

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }*/

    @ViewById(R.id.homeFragmentContainer)
    protected ViewPager viewPager;

    @AfterViews
    public void initState() {
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        viewPager.setAdapter(new HomePagerAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int index) {
                getActionBar().setSelectedNavigationItem(index);
            }
        });

        final ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {}
            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {}
        };

        final ActionBar.Tab trendingTab = actionBar.newTab()
                .setText(R.string.trending_header)
                .setTabListener(tabListener);
        actionBar.addTab(trendingTab);

        final ActionBar.Tab recentTab = actionBar.newTab()
                .setText(R.string.recent_header)
                .setTabListener(tabListener);
        actionBar.addTab(recentTab);
    }

    @OptionsItem(R.id.refreshButton)
    public void refreshButton() {
        final Fragment current = ((HomePagerAdapter) viewPager.getAdapter())
                .getActiveFragment(getActionBar().getSelectedNavigationIndex());
        if(current instanceof HomeTrendingFragment) {
            ((HomeTrendingFragment) current).refreshContents();
        }
        else if(current instanceof HomeRecentFragment) {
            ((HomeRecentFragment) current).refreshContents();
        }
    }

    @OptionsItem(android.R.id.home)
    public void homeButton() {
        super.toggleSlidingMenu();
    }

}