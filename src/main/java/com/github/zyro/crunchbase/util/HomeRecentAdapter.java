package com.github.zyro.crunchbase.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.github.zyro.crunchbase.R;
import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EBean
@NoArgsConstructor
public class HomeRecentAdapter extends BaseAdapter {

    /** The inflater to use as a layout source. */
    private LayoutInflater inflater;

    /** The data list backing this adapter. */
    private List<HomeRecentItem> data;

    /** Context used to lookup resources. */
    @RootContext
    protected Context context;

    @AfterInject
    public void initState() {
        inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        clearRecentItems();
    }

    /** Clear the entire data set. */
    public void clearRecentItems() {
        data = new ArrayList<HomeRecentItem>();
        notifyDataSetChanged();
    }

    public void addItem(final HomeRecentItem item) {
        data.add(item);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View conView,
                        final ViewGroup parent) {
        if(conView == null) {
            conView = inflater.inflate(R.layout.recent_item, null);
        }
        final HomeRecentItem item = getItem(position);

        ((TextView) conView.findViewById(R.id.recentItemName)).setText(
                item.getName());
        ((TextView) conView.findViewById(R.id.recentItemBusiness)).setText(
                item.getSubtext());
        ((TextView) conView.findViewById(R.id.recentItemFunds)).setText(
                item.getFunds());

        return conView;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public HomeRecentItem getItem(final int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

}