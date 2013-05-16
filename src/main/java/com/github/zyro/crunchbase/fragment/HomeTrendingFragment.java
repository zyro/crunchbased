package com.github.zyro.crunchbase.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.github.zyro.crunchbase.activity.CompanyActivity_;
import com.github.zyro.crunchbase.activity.HomeActivity;
import com.github.zyro.crunchbase.R;
import com.github.zyro.crunchbase.service.WebClient;
import com.github.zyro.crunchbase.util.HomeData;
import com.googlecode.androidannotations.annotations.*;
import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.home_trending)
public class HomeTrendingFragment extends HomeFragment {

    @SystemService
    protected LayoutInflater inflater;

    @Bean
    protected WebClient webClient;

    protected Adapter adapter = new Adapter();

    @ViewById(R.id.trendingEmpty)
    protected TextView empty;

    protected HomeActivity activity;

    @Override
    public void onViewCreated(final View view, final Bundle savedState) {
        super.onViewCreated(view, savedState);

        if(activity == null) {
            activity = (HomeActivity) getActivity();
        }

        ((ListView) activity.findViewById(R.id.trendingList)).setAdapter(adapter);
    }

    @Override
    public void refreshStarted() {
        empty.setText(R.string.refreshing);
        empty.setVisibility(adapter.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void refreshContents(final HomeData data) {
        if(!data.getTrending().isEmpty()) {
            adapter.clearItems();
        }
        for(final HomeData.Trending item : data.getTrending()) {
            adapter.addItem(item);
        }
    }

    @Override
    public void refreshDone() {
        empty.setText(R.string.no_items);
        empty.setVisibility(adapter.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @ItemClick(R.id.trendingList)
    public void handleTrendingListItemClick(final HomeData.Trending item) {
        if(item.getNamespace().equals("company")) {
            CompanyActivity_.intent(activity).permalink(item.getPermalink()).start();
            activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        }
        else {
            Toast.makeText(activity, item.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private class Adapter extends BaseAdapter {

        /** The data list backing this adapter. */
        private List<HomeData.Trending> data;

        /** Initialize correct state. */
        public Adapter() {
            clearItems();
        }

        /** Clear the entire data set. */
        public void clearItems() {
            data = new ArrayList<HomeData.Trending>();
            notifyDataSetChanged();
        }

        /** Add an item to the adapter. */
        public void addItem(final HomeData.Trending item) {
            data.add(item);
            notifyDataSetChanged();
        }

        /** Create a view for an item the adapter manages. */
        @Override
        public View getView(final int position, View conView,
                            final ViewGroup parent) {
            if(conView == null) {
                conView = inflater.inflate(R.layout.home_trending_item, null);
            }
            final HomeData.Trending item = getItem(position);

            ((TextView) conView.findViewById(R.id.trendingItemName)).setText(
                    item.getName());
            ((TextView) conView.findViewById(R.id.trendingItemType)).setText(
                    WordUtils.capitalize(item.getNamespace().replace("-", " ")));
            ((TextView) conView.findViewById(R.id.trendingItemPoints)).setText(
                    item.getPoints().toString());
            ((TextView) conView.findViewById(R.id.trendingItemPointsLabel)).setText(
                    item.getPoints() == 1 ?
                            activity.getString(R.string.trending_points_singular) :
                            activity.getString(R.string.trending_points_plural));

            return conView;
        }

        /** Get total number of items in the adapter. */
        @Override
        public int getCount() {
            return data.size();
        }

        /** Get an item at a specific position*/
        @Override
        public HomeData.Trending getItem(final int position) {
            return data.get(position);
        }

        /** Get the ID of a given list position. */
        @Override
        public long getItemId(final int position) {
            return position;
        }

    }

}