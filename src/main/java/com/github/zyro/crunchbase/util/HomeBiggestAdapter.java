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
public class HomeBiggestAdapter extends BaseAdapter {

    /** The inflater to use as a layout source. */
    private LayoutInflater inflater;

    /** The data list backing this adapter. */
    private List<HomeBiggestItem> data;

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
        data = new ArrayList<HomeBiggestItem>();
        notifyDataSetChanged();
    }

    public void addItem(final HomeBiggestItem item) {
        data.add(item);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View conView,
                        final ViewGroup parent) {
        if(conView == null) {
            conView = inflater.inflate(R.layout.biggest_item, null);
        }
        final HomeBiggestItem item = getItem(position);

        ((TextView) conView.findViewById(R.id.biggestItemName)).setText(
                item.getName());
        ((TextView) conView.findViewById(R.id.biggestItemBusiness)).setText(
                item.getSubtext());
        ((TextView) conView.findViewById(R.id.biggestItemFunds)).setText(
                item.getFunds());

        return conView;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public HomeBiggestItem getItem(final int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

}