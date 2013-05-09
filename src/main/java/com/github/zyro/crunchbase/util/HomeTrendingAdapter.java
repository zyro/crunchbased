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
import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.List;

@EBean
@NoArgsConstructor
public class HomeTrendingAdapter extends BaseAdapter {

    /** The inflater to use as a layout source. */
    private LayoutInflater inflater;

    /** The data list backing this adapter. */
    private List<HomeTrendingItem> data;

    /** Context used to lookup resources. */
    @RootContext
    protected Context context;

    @AfterInject
    public void initState() {
        inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        clearTrendingItems();
    }

    /** Clear the entire data set. */
    public void clearTrendingItems() {
        data = new ArrayList<HomeTrendingItem>();
        notifyDataSetChanged();
    }

    public void addItem(final HomeTrendingItem item) {
        data.add(item);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View conView,
                        final ViewGroup parent) {
        if(conView == null) {
            conView = inflater.inflate(R.layout.trending_item, null);
        }
        final HomeTrendingItem item = getItem(position);

        ((TextView) conView.findViewById(R.id.trendingItemName)).setText(
                item.getName());
        ((TextView) conView.findViewById(R.id.trendingItemType)).setText(
                WordUtils.capitalize(item.getNamespace().replace("-", " ")));
        ((TextView) conView.findViewById(R.id.trendingItemPoints)).setText(
                item.getPoints().toString());
        ((TextView) conView.findViewById(R.id.trendingItemPointsLabel)).setText(
                item.getPoints() == 1 ?
                        context.getString(R.string.trending_points_singular) :
                        context.getString(R.string.trending_points_plural));

        return conView;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public HomeTrendingItem getItem(final int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

}