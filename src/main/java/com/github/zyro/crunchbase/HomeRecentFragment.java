package com.github.zyro.crunchbase;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.github.zyro.crunchbase.service.WebClient;
import com.github.zyro.crunchbase.util.RecentAdapter;
import com.github.zyro.crunchbase.util.RecentItem;
import com.googlecode.androidannotations.annotations.*;

import java.util.List;

@EFragment(R.layout.home_recent)
public class HomeRecentFragment extends Fragment {

    @Bean
    WebClient webClient;

    protected RecentAdapter adapter;

    protected HomeActivity activity;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(activity == null) {
            activity = (HomeActivity) getActivity();
        }

        if(adapter == null) {
            adapter = new RecentAdapter(activity);
            refreshContents();
        }

        ((ListView) activity.findViewById(R.id.recentList)).setAdapter(adapter);
    }

    @Background
    public void refreshContents() {
        refreshContentsStarted();
        final String data = webClient.getPageData();
        final List<RecentItem> recentItems = webClient.getRecent(data);
        refreshContentsDone(recentItems);
    }

    @UiThread
    public void refreshContentsStarted() {
        activity.invalidateOptionsMenu();
        activity.menu.findItem(R.id.refreshButton).setVisible(false);
        activity.setProgressBarIndeterminateVisibility(true);

        if(adapter.isEmpty()) {
            final TextView label = (TextView) activity.findViewById(R.id.homeEmpty);
            label.setText(R.string.refreshing);
            label.setVisibility(View.VISIBLE);
        }
    }

    @UiThread
    public void refreshContentsDone(final List<RecentItem> recent) {
        if(!recent.isEmpty()) {
            adapter.clearRecentItems();
        }
        for(final RecentItem recentItem : recent) {
            adapter.addItem(recentItem);
        }

        activity.setProgressBarIndeterminateVisibility(false);
        activity.invalidateOptionsMenu();

        final TextView label = (TextView) activity.findViewById(R.id.homeEmpty);
        label.setText(R.string.no_items);
        label.setVisibility(adapter.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @ItemClick(R.id.recentList)
    public void handleTrendingListItemClick(final RecentItem recentItem) {
        CompanyActivity_.intent(activity).permalink(recentItem.getPermalink()).start();
        activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

}