package com.github.zyro.crunchbase;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomePagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public HomePagerAdapter(final FragmentManager fragmentManager) {
        super(fragmentManager);

        fragments = new ArrayList<Fragment>();
        fragments.add(new HomeTrendingFragment_());
        fragments.add(new HomeRecentFragment_());

        this.notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(final int index) {
        return fragments.get(index);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public Fragment getActiveFragment(final int index) {
        return fragments.get(index);
    }

}