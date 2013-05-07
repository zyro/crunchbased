package com.github.zyro.crunchbase;

import android.app.ActionBar;
import android.os.Bundle;
import com.github.zyro.crunchbase.util.HomeTabListener;
import com.googlecode.androidannotations.annotations.*;

@EActivity(R.layout.home)
public class HomeActivity extends BaseActivity {

    public HomeTrendingFragment trendingFragment;

    public HomeRecentFragment recentFragment;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        trendingFragment = new HomeTrendingFragment_();
        recentFragment = new HomeRecentFragment_();

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab = actionBar.newTab()
                .setText(R.string.trending_header)
                .setTabListener(new HomeTabListener(trendingFragment, R.id.homeFragmentContainer));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.recent_header)
                .setTabListener(new HomeTabListener(recentFragment, R.id.homeFragmentContainer));
        actionBar.addTab(tab);
    }

    @OptionsItem(R.id.refreshButton)
    public void refreshButton() {
        if(!trendingFragment.isDetached()) {
            trendingFragment.refreshContents();
        }
        else if(!recentFragment.isDetached()) {
            recentFragment.refreshContents();
        }
    }

    @OptionsItem(android.R.id.home)
    public void homeButton() {
        super.toggleSlidingMenu();
    }

}