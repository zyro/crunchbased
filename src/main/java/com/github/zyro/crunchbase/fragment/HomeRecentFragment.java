package com.github.zyro.crunchbase.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.github.zyro.crunchbase.activity.HomeActivity;
import com.github.zyro.crunchbase.R;
import com.github.zyro.crunchbase.util.ActivityLauncherListener;
import com.github.zyro.crunchbase.util.HomeData;
import com.googlecode.androidannotations.annotations.*;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.home_recent)
public class HomeRecentFragment extends HomeFragment {

    @SystemService
    protected LayoutInflater inflater;

    protected Adapter adapter = new Adapter();

    protected HomeActivity activity;

    @Override
    public void onViewCreated(final View view, final Bundle savedState) {
        super.onViewCreated(view, savedState);

        if(activity == null) {
            activity = (HomeActivity) getActivity();
        }

        final ListView list = (ListView) activity.findViewById(R.id.recentList);
        list.setAdapter(adapter);
        activity.addRefreshableView(list);
    }

    @Override
    public void refreshStarted() {}

    @Override
    public void refreshContents(final HomeData data) {
        if(!data.getRecent().isEmpty()) {
            adapter.clearItems();
        }
        for(final HomeData.Recent item : data.getRecent()) {
            adapter.addItem(item);
        }
    }

    @Override
    public void refreshDone() {}

    @ItemClick(R.id.recentList)
    public void handleRecentListItemClick(final HomeData.Recent item) {
        new ActivityLauncherListener(activity, "company", item.getPermalink()).launchNow();
    }

    private class Adapter extends BaseAdapter {

        /** The data list backing this adapter. */
        private List<HomeData.Recent> data;

        /** Initialize correct state. */
        public Adapter() {
            clearItems();
        }

        /** Clear the entire data set. */
        public void clearItems() {
            data = new ArrayList<HomeData.Recent>();
            notifyDataSetChanged();
        }

        /** Add an item to the adapter. */
        public void addItem(final HomeData.Recent item) {
            data.add(item);
            notifyDataSetChanged();
        }

        /** Create a view for an item the adapter manages. */
        @Override
        public View getView(final int position, View conView,
                            final ViewGroup parent) {
            if(conView == null) {
                conView = inflater.inflate(R.layout.home_recent_item, null);
            }
            final HomeData.Recent item = getItem(position);

            ((TextView) conView.findViewById(R.id.recentItemName)).setText(
                    item.getName());
            ((TextView) conView.findViewById(R.id.recentItemBusiness)).setText(
                    item.getSubtext());
            ((TextView) conView.findViewById(R.id.recentItemFunds)).setText(
                    item.getFunds());

            return conView;
        }

        /** Get total number of items in the adapter. */
        @Override
        public int getCount() {
            return data.size();
        }

        /** Get an item at a specific position. */
        @Override
        public HomeData.Recent getItem(final int position) {
            return data.get(position);
        }

        /** Get the ID of a given list position. */
        @Override
        public long getItemId(final int position) {
            return position;
        }

    }

}