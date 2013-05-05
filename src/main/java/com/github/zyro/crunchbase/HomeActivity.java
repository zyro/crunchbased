package com.github.zyro.crunchbase;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.*;
import android.widget.*;
import com.github.zyro.crunchbase.service.ApiClient;
import com.github.zyro.crunchbase.service.Preferences_;
import com.github.zyro.crunchbase.service.WebClient;
import com.github.zyro.crunchbase.util.SlidingLayer;
import com.github.zyro.crunchbase.util.TrendingAdapter;
import com.github.zyro.crunchbase.util.TrendingItem;
import com.googlecode.androidannotations.annotations.*;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

@EActivity(R.layout.home)
public class HomeActivity extends Activity {

    protected TrendingAdapter adapter;

    protected Menu menu;

    @Bean
    protected ApiClient apiClient;

    @Bean
    protected WebClient webClient;

    @Pref
    protected Preferences_ preferences;

    @ViewById(R.id.slidingMenu)
    protected SlidingLayer sl;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        this.menu = menu;
        return true;
    }

    @AfterViews
    public void initState() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        ((CheckBox) findViewById(R.id.imagesCheckbox))
                .setChecked(preferences.loadImages().get());
        ((CheckBox) findViewById(R.id.cacheCheckbox))
                .setChecked(preferences.cacheEnabled().get());

        adapter = new TrendingAdapter(this);
        ((ListView) findViewById(R.id.trendingList)).setAdapter(adapter);

        refreshTrendingList();
    }

    @Background
    public void refreshTrendingList() {
        refreshTrendingListStarted();
        final List<TrendingItem> trendingItems =  webClient.getTrending();
        refreshTrendingListDone(trendingItems);
    }

    @UiThread
    public void refreshTrendingListStarted() {
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
    public void refreshTrendingListDone(final List<TrendingItem> trending) {
        adapter.clearTrendingItems();
        for(final TrendingItem trendingItem : trending) {
            adapter.addItem(trendingItem);
        }

        setProgressBarIndeterminateVisibility(false);
        invalidateOptionsMenu();

        final TextView label = (TextView) findViewById(R.id.trendingEmpty);
        label.setText(R.string.no_items);
        label.setVisibility(adapter.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @OptionsItem(R.id.refreshButton)
    public void refreshButton() {
        refreshTrendingList();
    }

    @OptionsItem(R.id.searchButton)
    public void searchButton() {
        Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
    }

    @ItemClick(R.id.trendingList)
    public void handleTrendingListItemClick(TrendingItem trendingItem) {
        Toast.makeText(this, trendingItem.toString(), Toast.LENGTH_SHORT).show();
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()) {
            case R.id.imagesCheckbox:
                preferences.loadImages().put(checked);
                Toast.makeText(this, "Images " + checked, Toast.LENGTH_SHORT).show();
                break;
            case R.id.cacheCheckbox:
                preferences.cacheEnabled().put(checked);
                Toast.makeText(this, "Cache " + checked, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Click(R.id.aboutItem)
    public void aboutItem() {
        if(sl.isOpened()) {
            sl.closeLayer(true);
        }
        final TextView aboutView = new TextView(this);
        aboutView.setText(Html.fromHtml(getString(R.string.about_html)));
        aboutView.setHighlightColor(Color.alpha(0));
        aboutView.setMovementMethod(LinkMovementMethod.getInstance());
        new AlertDialog.Builder(this).setView(aboutView)
                .setNeutralButton(getString(R.string.about_dismiss), null)
                .setTitle(getString(R.string.menu_aboutItem)).show();
    }

    @OptionsItem(android.R.id.home)
    public void toggleSlidingMenu() {
        if(sl.isOpened()) {
            sl.closeLayer(true);
        }
        else {
            sl.openLayer(true);
        }
    }

    @Override
    public void onBackPressed() {
        if(sl.isOpened()) {
            sl.closeLayer(true);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_MENU) {
            toggleSlidingMenu();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}