package com.github.zyro.crunchbase.util;

import android.widget.AbsListView;

/** Listener interface for 'load more' list behaviour. */
public class LoadMoreScrollListener implements AbsListView.OnScrollListener {

    protected LoadMoreListener listener;

    public LoadMoreScrollListener(final LoadMoreListener listener) {
        this.listener = listener;
    }

    @Override
    public void onScrollStateChanged(final AbsListView view,
                                     final int scrollState) {}

    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem,
                         final int visibleItemCount, final int totalItemCount) {
        final int lastInScreen = firstVisibleItem + visibleItemCount;
        if(lastInScreen == totalItemCount) {
            listener.loadMore();
        }
    }

}