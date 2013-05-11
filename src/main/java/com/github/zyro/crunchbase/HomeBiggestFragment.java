package com.github.zyro.crunchbase;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.github.zyro.crunchbase.service.WebClient;
import com.github.zyro.crunchbase.util.HomeBiggestAdapter;
import com.github.zyro.crunchbase.util.HomeBiggestItem;
import com.github.zyro.crunchbase.util.HomeRecentAdapter;
import com.github.zyro.crunchbase.util.HomeRecentItem;
import com.googlecode.androidannotations.annotations.*;

import java.util.List;

@EFragment(R.layout.home_biggest)
public class HomeBiggestFragment extends Fragment {

    @Bean
    protected WebClient webClient;

    @Bean
    protected HomeBiggestAdapter adapter;

    @ViewById(R.id.biggestEmpty)
    protected TextView biggestEmpty;

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

        ((ListView) activity.findViewById(R.id.biggestList)).setAdapter(adapter);
    }

    @Background
    public void refreshContents() {
        refreshContentsStarted();
        final List<HomeBiggestItem> recentItems = webClient.getBiggest();
        refreshContentsDone(recentItems);
    }

    @UiThread
    public void refreshContentsStarted() {
        activity.invalidateOptionsMenu();
        activity.menu.findItem(R.id.refreshButton).setVisible(false);
        activity.setProgressBarIndeterminateVisibility(true);

        if(adapter.isEmpty()) {
            biggestEmpty.setText(R.string.refreshing);
            biggestEmpty.setVisibility(View.VISIBLE);
        }
    }

    @UiThread
    public void refreshContentsDone(final List<HomeBiggestItem> recent) {
        if(!recent.isEmpty()) {
            adapter.clearRecentItems();
        }
        for(final HomeBiggestItem recentItem : recent) {
            adapter.addItem(recentItem);
        }

        activity.setProgressBarIndeterminateVisibility(false);
        activity.invalidateOptionsMenu();

        biggestEmpty.setText(R.string.no_items);
        biggestEmpty.setVisibility(adapter.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @ItemClick(R.id.biggestList)
    public void handleBiggestListItemClick(final HomeBiggestItem biggestItem) {
        CompanyActivity_.intent(activity).permalink(biggestItem.getPermalink()).start();
        activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

}