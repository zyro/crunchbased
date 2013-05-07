package com.github.zyro.crunchbase;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.github.zyro.crunchbase.service.WebClient;
import com.github.zyro.crunchbase.util.TrendingAdapter;
import com.github.zyro.crunchbase.util.TrendingItem;
import com.googlecode.androidannotations.annotations.*;

import java.util.List;

@EFragment(R.layout.home_trending)
public class HomeTrendingFragment extends Fragment {

    @Bean
    WebClient webClient;

    protected TrendingAdapter adapter;

    protected HomeActivity activity;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(activity == null) {
            activity = (HomeActivity) getActivity();
        }

        if(adapter == null) {
            adapter = new TrendingAdapter(activity);
            refreshContents();
        }

        ((ListView) activity.findViewById(R.id.trendingList)).setAdapter(adapter);
    }

    @Background
    public void refreshContents() {
        refreshContentsStarted();
        final String data = webClient.getPageData();
        final List<TrendingItem> trendingItems = webClient.getTrending(data);
        refreshContentsDone(trendingItems);
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
    public void refreshContentsDone(final List<TrendingItem> trending) {
        if(!trending.isEmpty()) {
            adapter.clearTrendingItems();
        }
        for(final TrendingItem trendingItem : trending) {
            adapter.addItem(trendingItem);
        }

        activity.setProgressBarIndeterminateVisibility(false);
        activity.invalidateOptionsMenu();

        final TextView label = (TextView) activity.findViewById(R.id.homeEmpty);
        label.setText(R.string.no_items);
        label.setVisibility(adapter.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @ItemClick(R.id.trendingList)
    public void handleTrendingListItemClick(final TrendingItem trendingItem) {
        if(trendingItem.getNamespace().equals("company")) {
            CompanyActivity_.intent(activity).permalink(trendingItem.getPermalink()).start();
            activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        }
        else {
            Toast.makeText(activity, trendingItem.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}