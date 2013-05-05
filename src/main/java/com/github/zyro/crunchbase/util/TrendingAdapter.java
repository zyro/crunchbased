package com.github.zyro.crunchbase.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.github.zyro.crunchbase.R;
import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.List;

public class TrendingAdapter extends BaseAdapter {

    /** The inflater to use as a layout source. */
    private LayoutInflater inflater;

    /** The data list backing this adapter. */
    private List<TrendingItem> data;

    /** Constructor to properly initialise state of adapter. */
    public TrendingAdapter(final Context context) {
        super();
        inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        clearTrendingItems();
    }

    /** Clear the entire data set. */
    public void clearTrendingItems() {
        data = new ArrayList<TrendingItem>();
        notifyDataSetChanged();
    }

    public void addItem(final TrendingItem item) {
        data.add(item);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View conView,
                        final ViewGroup parent) {
        if(conView == null) {
            conView = inflater.inflate(R.layout.trending_item, null);
        }
        final TrendingItem item = getItem(position);

        ((TextView) conView.findViewById(R.id.trendingItemName)).setText(
                item.getName());
        ((TextView) conView.findViewById(R.id.trendingItemType)).setText(
                WordUtils.capitalize(item.getNamespace().replace("-", " ")));
        ((TextView) conView.findViewById(R.id.trendingItemPoints)).setText(
                item.getPoints().toString());

        return conView;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public TrendingItem getItem(final int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

}