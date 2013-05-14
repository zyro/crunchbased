package com.github.zyro.crunchbase;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.github.zyro.crunchbase.service.WebClient;
import com.github.zyro.crunchbase.util.HomeData;
import com.googlecode.androidannotations.annotations.*;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.home_biggest)
public class HomeBiggestFragment extends Fragment implements HomeFragment {

    @SystemService
    protected LayoutInflater inflater;

    @Bean
    protected WebClient webClient;

    protected Adapter adapter = new Adapter();

    @ViewById(R.id.biggestEmpty)
    protected TextView empty;

    protected HomeActivity activity;

    @Override
    public void onViewCreated(final View view, final Bundle savedState) {
        super.onViewCreated(view, savedState);

        if(activity == null) {
            activity = (HomeActivity) getActivity();
        }

        ((ListView) activity.findViewById(R.id.biggestList)).setAdapter(adapter);
    }

    @Override
    public void refreshStarted() {
        empty.setText(R.string.refreshing);
        empty.setVisibility(adapter.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void refreshContents(final HomeData data) {
        if(!data.getBiggest().isEmpty()) {
            adapter.clearItems();
        }
        for(final HomeData.Biggest item : data.getBiggest()) {
            adapter.addItem(item);
        }
    }

    @Override
    public void refreshDone() {
        empty.setText(R.string.no_items);
        empty.setVisibility(adapter.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @ItemClick(R.id.biggestList)
    public void handleBiggestListItemClick(final HomeData.Biggest item) {
        CompanyActivity_.intent(activity).permalink(item.getPermalink()).start();
        activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    /** Adapter for Biggest-type fragments. */
    private class Adapter extends BaseAdapter {

        /** The data list backing this adapter. */
        private List<HomeData.Biggest> data;

        /** Initialize correct state. */
        public Adapter() {
            clearItems();
        }

        /** Clear the entire data set. */
        public void clearItems() {
            data = new ArrayList<HomeData.Biggest>();
            notifyDataSetChanged();
        }

        /** Add an item to the adapter. */
        public void addItem(final HomeData.Biggest item) {
            data.add(item);
            notifyDataSetChanged();
        }

        /** Create a view for an item the adapter manages. */
        @Override
        public View getView(final int position, View conView,
                            final ViewGroup parent) {
            if(conView == null) {
                conView = inflater.inflate(R.layout.biggest_item, null);
            }
            final HomeData.Biggest item = getItem(position);

            ((TextView) conView.findViewById(R.id.biggestItemName)).setText(
                    item.getName());
            ((TextView) conView.findViewById(R.id.biggestItemBusiness)).setText(
                    item.getSubtext());
            ((TextView) conView.findViewById(R.id.biggestItemFunds)).setText(
                    item.getFunds());

            return conView;
        }

        /** Get total number of items in the adapter. */
        @Override
        public int getCount() {
            return data.size();
        }

        /** Get an item at a specific position*/
        @Override
        public HomeData.Biggest getItem(final int position) {
            return data.get(position);
        }

        /** Get the ID of a given list position. */
        @Override
        public long getItemId(final int position) {
            return position;
        }

    }

}