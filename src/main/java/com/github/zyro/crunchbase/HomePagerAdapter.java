package com.github.zyro.crunchbase;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/** Adapter for tab fragments on the application home screen. */
public class HomePagerAdapter extends FragmentPagerAdapter {

    /** Internal list of fragments. */
    private List<Fragment> fragments;

    /** Initialise with appropriate fragment manager instance. */
    public HomePagerAdapter(final FragmentManager fragmentManager) {
        super(fragmentManager);

        fragments = new ArrayList<Fragment>();
        fragments.add(new HomeTrendingFragment_());
        fragments.add(new HomeRecentFragment_());
        fragments.add(new HomeBiggestFragment_());

        this.notifyDataSetChanged();
    }

    /** Get the fragment at a specific page/tab index. */
    @Override
    public Fragment getItem(final int index) {
        return fragments.get(index);
    }

    /** Get the total number of pages/tabs. */
    @Override
    public int getCount() {
        return fragments.size();
    }

    /** Get the currently active/visible fragment instance. */
    public Fragment getActiveFragment(final int index) {
        return fragments.get(index);
    }

}