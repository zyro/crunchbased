package com.github.zyro.crunchbase.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.zyro.crunchbase.R;
import com.github.zyro.crunchbase.entity.Result;
import com.github.zyro.crunchbase.entity.Search;
import com.github.zyro.crunchbase.util.RefreshMessage;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.SystemService;
import com.googlecode.androidannotations.annotations.UiThread;
import com.koushikdutta.async.future.FutureCallback;

import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.search)
public class SearchActivity extends BaseActivity implements FutureCallback<Search> {

    @SystemService
    protected LayoutInflater inflater;

    protected Adapter adapter = new Adapter();

    /** The search query to display results for. */
    protected String query;

    /** The entity type to limit searches to. */
    protected String entity;

    /** The entity field to limit searches to. */
    protected String field;

    /** The currently visible page. */
    protected int page = 0;

    @Override
    public void onCreate(final Bundle saved) {
        super.onCreate(saved);

        // Process the search query from the intent.
        query = getIntent().getStringExtra("searchQuery");

        // Process the search entity, if one exists.
        final String searchEntity = getIntent().getStringExtra("searchEntity");
        if("Companies".equalsIgnoreCase(searchEntity)) {
            entity = "company";
        }
        else if("People".equalsIgnoreCase(searchEntity)) {
            entity = "person";
        }
        else if("Products".equalsIgnoreCase(searchEntity)) {
            entity = "product";
        }
        else if("Financial Organizations".equalsIgnoreCase(searchEntity)) {
            entity = "financial_org";
        }
        else if("Providers".equalsIgnoreCase(searchEntity)) {
            entity = "provider";
        }
        else {
            entity = null;
        }

        // Process the search field, if one exists.
        final String searchField = getIntent().getStringExtra("searchField");
        if("Name".equalsIgnoreCase(searchField)) {
            field = "name";
        }
        else if("Overview".equalsIgnoreCase(searchField)) {
            field = "overview";
        }
        else if("Homepage URL".equalsIgnoreCase(searchField)) {
            field = "homepage_url";
        }
        else if("Tag List".equalsIgnoreCase(searchField)) {
            field = "tag_list";
        }
        else {
            field = null;
        }
    }

    @AfterViews
    public void initState() {
        getActionBar().setDisplayHomeAsUpEnabled(true);

        final ListView list = (ListView) findViewById(R.id.searchList);
        list.setAdapter(adapter);
        addRefreshableView(list);
        //((PullToRefreshLayout) findViewById(R.id.companyPtr))
        //        .setPullToRefreshAttacher(attacher, this);

        //findViewById(R.id.companyContents).setOnTouchListener(
        //        new SwipeBackListener(this));
        refreshButton();
    }

    @Override
    @Background
    public void refresh() {
        refreshStarted();
        client.getSearchResults(query, 1, entity, field, this);
    }

    @UiThread
    public void refreshStarted() {
        RefreshMessage.hideRefreshFailed(this);
    }

    @UiThread
    public void refreshDone(final Search search) {
        if(!search.getResults().isEmpty() && search.getPage() == 1) {
            adapter.clearItems();
        }

        page = search.getPage();

        for(final Result item : search.getResults()) {
            adapter.addItem(item);
        }

        onRefreshCompleted();
        RefreshMessage.hideRefreshFailed(this);
    }

    @UiThread
    public void refreshFailed() {
        onRefreshCompleted();
        RefreshMessage.showRefreshFailed(this);
    }

    @Override
    public void onCompleted(final Exception e, final Search search) {
        if(e == null) {
            refreshDone(search);
        }
        else {
            refreshFailed();
        }
    }

    private class Adapter extends BaseAdapter {

        /** The data list backing this adapter. */
        private List<Result> data;

        /** Initialize correct state. */
        public Adapter() {
            clearItems();
        }

        /** Clear the entire data set. */
        public void clearItems() {
            data = new ArrayList<Result>();
            notifyDataSetChanged();
        }

        /** Add an item to the adapter. */
        public void addItem(final Result item) {
            data.add(item);
            notifyDataSetChanged();
        }

        /** Create a view for an item the adapter manages. */
        @Override
        public View getView(final int position, View conView,
                            final ViewGroup parent) {
            if(conView == null) {
                conView = inflater.inflate(R.layout.search_item, null);
            }
            final Result item = getItem(position);

            ((TextView) conView.findViewById(R.id.searchName)).setText(
                    item.getName());
            ((TextView) conView.findViewById(R.id.searchType)).setText(
                    WordUtils.capitalize(item.getNamespace().replace("-", " ")));
            if(item.getImage() != null) {
                client.loadImage(item.getImage().getMediumAsset(),
                        (ImageView) conView.findViewById(R.id.searchImage));
            }

            return conView;
        }

        /** Get total number of items in the adapter. */
        @Override
        public int getCount() {
            return data.size();
        }

        /** Get an item at a specific position*/
        @Override
        public Result getItem(final int position) {
            return data.get(position);
        }

        /** Get the ID of a given list position. */
        @Override
        public long getItemId(final int position) {
            return position;
        }

    }

}