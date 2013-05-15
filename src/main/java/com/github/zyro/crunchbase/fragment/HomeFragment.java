package com.github.zyro.crunchbase.fragment;

import android.support.v4.app.Fragment;
import com.github.zyro.crunchbase.util.HomeData;

/**
 * Abstract class to allow easy access to generic Home fragment operations
 * without excessive instance checks or casting.
 */
public abstract class HomeFragment extends Fragment {

    /** Indicate that a refresh operation has started. */
    public abstract void refreshStarted();

    /** Allow the fragment to act on new data. */
    public abstract void refreshContents(final HomeData data);

    /** Indicate that a refresh operation has ended. */
    public abstract void refreshDone();

}