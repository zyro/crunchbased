package com.github.zyro.crunchbase;

import android.view.*;
import android.widget.*;
import com.github.zyro.crunchbase.util.RecentAdapter;
import com.github.zyro.crunchbase.util.RecentItem;
import com.github.zyro.crunchbase.util.TrendingAdapter;
import com.github.zyro.crunchbase.util.TrendingItem;
import com.googlecode.androidannotations.annotations.*;

import java.util.List;

@EActivity(R.layout.home)
public class HomeActivity extends BaseActivity {

    protected TrendingAdapter adapter;

    @AfterViews
    public void initState() {
        adapter = new TrendingAdapter(this);
        ((ListView) findViewById(R.id.trendingList)).setAdapter(adapter);

        refreshContents();
    }

    @Background
    public void refreshContents() {
        refreshContentsStarted();
        final String data = webClient.getPageData();
        final List<TrendingItem> trendingItems = webClient.getTrending(data);
        final List<RecentItem> recentItems = webClient.getRecent(data);
        refreshContentsDone(trendingItems, recentItems);
    }

    @UiThread
    public void refreshContentsStarted() {
        invalidateOptionsMenu();
        menu.findItem(R.id.refreshButton).setVisible(false);
        setProgressBarIndeterminateVisibility(true);

        if(adapter.isEmpty()) {
            final TextView label = (TextView) findViewById(R.id.trendingEmpty);
            label.setText(R.string.refreshing);
            label.setVisibility(View.VISIBLE);
        }
    }

    @UiThread
    public void refreshContentsDone(final List<TrendingItem> trending,
                                    final List<RecentItem> recent) {
        if(!trending.isEmpty()) {
            adapter.clearTrendingItems();
        }
        for(final TrendingItem trendingItem : trending) {
            adapter.addItem(trendingItem);
        }

        setProgressBarIndeterminateVisibility(false);
        invalidateOptionsMenu();

        final TextView label = (TextView) findViewById(R.id.trendingEmpty);
        label.setText(R.string.no_items);
        label.setVisibility(adapter.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @ItemClick(R.id.trendingList)
    public void handleTrendingListItemClick(final TrendingItem trendingItem) {
        if(trendingItem.getNamespace().equals("company")) {
            CompanyActivity_.intent(this).permalink(trendingItem.getPermalink()).start();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        }
        else {
            Toast.makeText(this, trendingItem.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @OptionsItem(R.id.refreshButton)
    public void refreshButton() {
        refreshContents();
    }

    @OptionsItem(android.R.id.home)
    public void homeButton() {
        super.toggleSlidingMenu();
    }

}