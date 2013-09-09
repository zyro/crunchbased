package com.github.zyro.crunchbase.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.zyro.crunchbase.R;
import com.github.zyro.crunchbase.entity.Result;
import com.github.zyro.crunchbase.entity.Search;
import com.github.zyro.crunchbase.util.LoadMoreListener;
import com.github.zyro.crunchbase.util.LoadMoreScrollListener;
import com.github.zyro.crunchbase.util.RefreshMessage;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ItemClick;
import com.googlecode.androidannotations.annotations.SystemService;
import com.googlecode.androidannotations.annotations.UiThread;
import com.koushikdutta.async.future.FutureCallback;

import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.search)
public class SearchActivity extends BaseActivity
        implements FutureCallback<Search>, LoadMoreListener {

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

    /** The total number of results available for this search request. */
    protected int results = 0;

    /** Whether the activity is currently loading more data. */
    protected boolean loading = false;

    /** The search results list footer. */
    protected View footer;

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
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setTitle(query);

        footer = inflater.inflate(R.layout.search_footer, null);

        final ListView list = (ListView) findViewById(R.id.searchList);
        list.addFooterView(footer);
        list.setAdapter(adapter);
        list.setOnScrollListener(new LoadMoreScrollListener(this));
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
        loading = true;
        RefreshMessage.hideRefreshFailed(this);
        attacher.setRefreshing(true);
        footer.findViewById(R.id.searchFooterLoading).setVisibility(View.VISIBLE);
        footer.findViewById(R.id.searchFooterNoMore).setVisibility(View.GONE);
        footer.findViewById(R.id.searchFooterFailed).setVisibility(View.GONE);
    }

    @UiThread
    public void refreshDone(final Search search) {
        if(!search.getResults().isEmpty() && search.getPage() == 1) {
            adapter.clearItems();
        }

        page = search.getPage();
        results = search.getTotal();

        for(final Result item : search.getResults()) {
            adapter.addItem(item);
        }

        loading = false;
        onRefreshCompleted();
        RefreshMessage.hideRefreshFailed(this);
        footer.findViewById(R.id.searchFooterLoading).setVisibility(
                results / 10.0 > page ? View.VISIBLE : View.GONE);
        footer.findViewById(R.id.searchFooterNoMore).setVisibility(
                results / 10.0 <= page ? View.VISIBLE : View.GONE);
        footer.findViewById(R.id.searchFooterFailed).setVisibility(View.GONE);

        getActionBar().setSubtitle(results + (results == 1 ?
                getString(R.string.search_results_singular) :
                getString(R.string.search_results_plural)));
    }

    @UiThread
    public void refreshFailed() {
        loading = false;
        onRefreshCompleted();
        RefreshMessage.showRefreshFailed(this);
        footer.findViewById(R.id.searchFooterLoading).setVisibility(View.GONE);
        footer.findViewById(R.id.searchFooterNoMore).setVisibility(View.GONE);
        footer.findViewById(R.id.searchFooterFailed).setVisibility(View.VISIBLE);
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

    @Override
    @Background
    public void loadMore() {
        if(!loading && results / 10.0 > page) {
            refreshStarted();
            client.getSearchResults(query, page + 1, entity, field, this);
        }
    }

    @ItemClick(R.id.searchList)
    public void handleSearchListItemClick(final Result item) {
        // Only search list footer will never have a result.
        if(item == null) {
            if(loading) {
                return;
            }

            if(adapter.isEmpty()) {
                refreshButton();
            }
            else {
                loadMore();
            }
        }
        else if(item.getNamespace().equals("company")) {
            final Intent intent = new Intent(this, CompanyActivity_.class);
            intent.setData(Uri.parse("http://www.crunchbase.com/company/" +
                    item.getPermalink()));
            startActivity(intent);
            this.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        }
        else if(item.getNamespace().equals("financial-organization")) {
            final Intent intent = new Intent(this, FinancialOrganizationActivity_.class);
            intent.setData(Uri.parse("http://www.crunchbase.com/financial-organization/" +
                    item.getPermalink()));
            startActivity(intent);
            this.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        }
        else if(item.getNamespace().equals("person")) {
            final Intent intent = new Intent(this, PersonActivity_.class);
            intent.setData(Uri.parse("http://www.crunchbase.com/person/" +
                    item.getPermalink()));
            startActivity(intent);
            this.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        }
        else {
            Toast.makeText(this, item.toString(), Toast.LENGTH_SHORT).show();
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
                    "person".equalsIgnoreCase(item.getNamespace()) ?
                            item.getFirst_name() + " " + item.getLast_name() :
                            item.getName());
            ((TextView) conView.findViewById(R.id.searchType)).setText(
                    WordUtils.capitalize(item.getNamespace().replace("-", " ")));
            if(item.getImage() != null) {
                client.loadImage(item.getImage().getMediumAsset(),
                        (ImageView) conView.findViewById(R.id.searchImage));
            }
            else {
                ((ImageView) conView.findViewById(R.id.searchImage))
                        .setImageResource(R.drawable.no_image_placeholder);
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