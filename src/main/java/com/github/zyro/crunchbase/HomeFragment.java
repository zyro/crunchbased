package com.github.zyro.crunchbase;

import com.github.zyro.crunchbase.util.HomeData;

/**
 * Interface to allow easy access to generic Home fragment operations without
 * excessive instance checks or casting.
 */
public interface HomeFragment {

    /** Indicate that a refresh operation has started. */
    void refreshStarted();

    /** Allow the fragment to act on new data. */
    void refreshContents(HomeData data);

    /** Indicate that a refresh operation has ended. */
    void refreshDone();

}