package com.github.zyro.crunchbase.activity;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;
import com.github.zyro.crunchbase.fragment.HomeBiggestFragment_;
import com.github.zyro.crunchbase.fragment.HomeRecentFragment_;
import com.github.zyro.crunchbase.fragment.HomeTrendingFragment_;
import com.github.zyro.crunchbase.R;
import com.github.zyro.crunchbase.fragment.HomeFragment;
import com.github.zyro.crunchbase.service.ClientException;
import com.github.zyro.crunchbase.util.FormatUtils;
import com.github.zyro.crunchbase.util.HomeData;
import com.googlecode.androidannotations.annotations.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/** Activity to handle the application home area. */
@EActivity(R.layout.home)
public class HomeActivity extends BaseActivity {

    /** Pager adapter for home page tabs. */
    protected HomePagerAdapter adapter;

    /** Perform additional custom configuration. */
    @Override
    public void onConfigurationChanged(final Configuration config) {
        super.onConfigurationChanged(config);
        forceTabs();
    }

    /** Force tabs embedded in action bar regardless of screen orientation. */
    public void forceTabs() {
        try {
            final ActionBar actionBar = getActionBar();
            final Method setHasEmbeddedTabsMethod = actionBar.getClass()
                    .getDeclaredMethod("setHasEmbeddedTabs", boolean.class);
            setHasEmbeddedTabsMethod.setAccessible(true);
            setHasEmbeddedTabsMethod.invoke(actionBar, true);
        }
        catch(final Exception e) {} // Don't care if it doesn't work.
    }

    /** Initialize tabs and associated view pager. */
    @AfterViews
    public void initState() {
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        forceTabs();

        adapter = new HomePagerAdapter(getSupportFragmentManager());

        final ViewPager pager = (ViewPager) findViewById(R.id.homeContainer);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(adapter.getCount());
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int index) {
                getActionBar().setSelectedNavigationItem(index);
            }
        });

        final ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                pager.setCurrentItem(tab.getPosition());
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

        refreshButton();
    }

    /** Begin a data refresh attempt. */
    @Override
    @Background
    public void refresh() {
        refreshStarted();
        try {
            final HomeData data = client.getHomeData();
            refreshDone(data);
        }
        catch(final ClientException e) {
            Log.e("", "FAIL", e);
            refreshFailed();
        }
    }

    /** Refresh started action. */
    @UiThread
    public void refreshStarted() {
        for(final HomeFragment fragment : adapter.getAll()) {
            fragment.refreshStarted();
        }
    }

    /** Refresh complete action. */
    @UiThread
    public void refreshDone(final HomeData data) {
        for(final HomeFragment fragment : adapter.getAll()) {
            fragment.refreshContents(data);
            fragment.refreshDone();
        }

        onRefreshCompleted();
    }

    /** Refresh failed action. */
    @UiThread
    public void refreshFailed() {
        onRefreshCompleted();

        Toast.makeText(this, getString(R.string.refresh_failed),
                Toast.LENGTH_SHORT).show();
    }

    /** Adapter for tab fragments on the application home screen. */
    private class HomePagerAdapter extends FragmentPagerAdapter {

        /** Internal list of fragments. */
        private List<HomeFragment> fragments;

        /** Initialise with appropriate fragment manager instance. */
        public HomePagerAdapter(final FragmentManager fragmentManager) {
            super(fragmentManager);

            fragments = new ArrayList<HomeFragment>();
            fragments.add(new HomeTrendingFragment_());
            fragments.add(new HomeRecentFragment_());
            fragments.add(new HomeBiggestFragment_());

            this.notifyDataSetChanged();
        }

        /** Get the full list of fragments this adapter is holding. */
        public List<HomeFragment> getAll() {
            return fragments;
        }

        /** Get the fragment at a specific page/tab index. */
        @Override
        public HomeFragment getItem(final int index) {
            return fragments.get(index);
        }

        /** Get the total number of pages/tabs. */
        @Override
        public int getCount() {
            return fragments.size();
        }

    }

}