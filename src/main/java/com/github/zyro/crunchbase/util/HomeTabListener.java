package com.github.zyro.crunchbase.util;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import lombok.AllArgsConstructor;

/** Listener to attach or detach a fragment on the Home Activity. */
@AllArgsConstructor
public class HomeTabListener implements ActionBar.TabListener {

    /** A reference to the fragment this instance is listening to. */
    protected Fragment fragment;

    /** The view identifier to initially add the fragment to. */
    protected int fragmentContainerViewId;

    /** Attach the fragment when the tab is selected. */
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        if(fragment.isDetached()) {
            ft.attach(fragment);
        }
        else {
            ft.add(fragmentContainerViewId, fragment);
        }
    }

    /** Detach the fragment when the tab is unselected. */
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.detach(fragment);
    }

    /** No action when the tab is reselected. */
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {}

}