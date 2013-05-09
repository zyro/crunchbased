package com.github.zyro.crunchbase;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.github.zyro.crunchbase.service.WebClient;
import com.github.zyro.crunchbase.util.HomeRecentAdapter;
import com.github.zyro.crunchbase.util.HomeRecentItem;
import com.googlecode.androidannotations.annotations.*;

import java.util.List;

@EFragment(R.layout.home_recent)
public class HomeRecentFragment extends Fragment {

    @Bean
    protected WebClient webClient;

    @Bean
    protected HomeRecentAdapter adapter;

    @ViewById(R.id.recentEmpty)
    protected TextView recentEmpty;

    protected HomeActivity activity;

    @Override
    public void onViewCreated(final View view, final Bundle savedState) {
        super.onViewCreated(view, savedState);

        if(activity == null) {
            activity = (HomeActivity) getActivity();
        }

        if(adapter.isEmpty()) {
            refreshContents();
        }

        ((ListView) activity.findViewById(R.id.recentList)).setAdapter(adapter);
    }

    @Background
    public void refreshContents() {
        refreshContentsStarted();
        final List<HomeRecentItem> recentItems = webClient.getRecent();
        refreshContentsDone(recentItems);
    }

    @UiThread
    public void refreshContentsStarted() {
        activity.invalidateOptionsMenu();
        activity.menu.findItem(R.id.refreshButton).setVisible(false);
        activity.setProgressBarIndeterminateVisibility(true);

        if(adapter.isEmpty()) {
            recentEmpty.setText(R.string.refreshing);
            recentEmpty.setVisibility(View.VISIBLE);
        }
    }

    @UiThread
    public void refreshContentsDone(final List<HomeRecentItem> recent) {
        if(!recent.isEmpty()) {
            adapter.clearRecentItems();
        }
        for(final HomeRecentItem recentItem : recent) {
            adapter.addItem(recentItem);
        }

        activity.setProgressBarIndeterminateVisibility(false);
        activity.invalidateOptionsMenu();

        recentEmpty.setText(R.string.no_items);
        recentEmpty.setVisibility(adapter.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @ItemClick(R.id.recentList)
    public void handleTrendingListItemClick(final HomeRecentItem recentItem) {
        CompanyActivity_.intent(activity).permalink(recentItem.getPermalink()).start();
        activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

}