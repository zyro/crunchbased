package com.github.zyro.crunchbase.util;

import android.widget.AbsListView;

/** Listener interface for 'load more' list behaviour. */
public class LoadMoreScrollListener implements AbsListView.OnScrollListener {

    /** The listener to notify that a 'load more' is required. */
    protected LoadMoreListener listener;

    /**
     * Initialise the listener with a LoadMoreListener instance that should be
     * notified when a 'load more' action is required.
     *
     * @param listener The listener to notify that a 'load more' is required.
     */
    public LoadMoreScrollListener(final LoadMoreListener listener) {
        this.listener = listener;
    }

    /** Not used. */
    @Override
    public void onScrollStateChanged(final AbsListView view,
                                     final int scrollState) {}

    /**
     * React to scroll events and trigger a 'load more' via the associated
     * LoadMoreListener when the last item is visible.
     *
     * It is up to the listener receiving the request to determine how to handle
     * it. It is entirely at its discretion for example whether there actually
     * are any more items that can be loaded, or if there is already a load in
     * progress and the user is bouncing the list against the bottom of the
     * screen or something.
     */
    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem,
                         final int visibleItemCount, final int totalItemCount) {
        final int lastInScreen = firstVisibleItem + visibleItemCount;
        if(lastInScreen == totalItemCount) {
            listener.loadMore();
        }
    }

}