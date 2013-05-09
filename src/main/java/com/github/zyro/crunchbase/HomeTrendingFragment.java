package com.github.zyro.crunchbase;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.github.zyro.crunchbase.service.WebClient;
import com.github.zyro.crunchbase.util.HomeTrendingAdapter;
import com.github.zyro.crunchbase.util.HomeTrendingItem;
import com.googlecode.androidannotations.annotations.*;

import java.util.List;

@EFragment(R.layout.home_trending)
public class HomeTrendingFragment extends Fragment {

    @Bean
    protected WebClient webClient;

    @Bean
    protected HomeTrendingAdapter adapter;

    @ViewById(R.id.trendingEmpty)
    protected TextView trendingEmpty;

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

        ((ListView) activity.findViewById(R.id.trendingList)).setAdapter(adapter);
    }

    @Background
    public void refreshContents() {
        refreshContentsStarted();
        final List<HomeTrendingItem> trendingItems = webClient.getTrending();
        refreshContentsDone(trendingItems);
    }

    @UiThread
    public void refreshContentsStarted() {
        activity.invalidateOptionsMenu();
        activity.menu.findItem(R.id.refreshButton).setVisible(false);
        activity.setProgressBarIndeterminateVisibility(true);

        if(adapter.isEmpty()) {
            trendingEmpty.setText(R.string.refreshing);
            trendingEmpty.setVisibility(View.VISIBLE);
        }
    }

    @UiThread
    public void refreshContentsDone(final List<HomeTrendingItem> trending) {
        if(!trending.isEmpty()) {
            adapter.clearTrendingItems();
        }
        for(final HomeTrendingItem trendingItem : trending) {
            adapter.addItem(trendingItem);
        }

        activity.setProgressBarIndeterminateVisibility(false);
        activity.invalidateOptionsMenu();

        trendingEmpty.setText(R.string.no_items);
        trendingEmpty.setVisibility(adapter.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @ItemClick(R.id.trendingList)
    public void handleTrendingListItemClick(final HomeTrendingItem trendingItem) {
        if(trendingItem.getNamespace().equals("company")) {
            CompanyActivity_.intent(activity).permalink(trendingItem.getPermalink()).start();
            activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        }
        else {
            Toast.makeText(activity, trendingItem.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}