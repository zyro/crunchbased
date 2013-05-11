package com.github.zyro.crunchbase;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import com.googlecode.androidannotations.annotations.*;

@EActivity(R.layout.home)
public class HomeActivity extends BaseActivity {

    @ViewById(R.id.homeFragmentContainer)
    protected ViewPager viewPager;

    @AfterViews
    public void initState() {
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        viewPager.setAdapter(new HomePagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(3);
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

        final ActionBar.Tab biggestTab = actionBar.newTab()
                .setText(R.string.biggest_header)
                .setTabListener(tabListener);
        actionBar.addTab(biggestTab);
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
        else if(current instanceof HomeBiggestFragment) {
            ((HomeBiggestFragment) current).refreshContents();
        }
    }

    @OptionsItem(android.R.id.home)
    public void homeButton() {
        slidingMenu.toggle();
    }

}