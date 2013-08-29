package com.github.zyro.crunchbase.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import com.github.zyro.crunchbase.R;
import com.github.zyro.crunchbase.service.CrunchbaseClient;
import com.github.zyro.crunchbase.service.Preferences_;
import com.github.zyro.crunchbase.util.HeaderTransformer;
import com.googlecode.androidannotations.annotations.*;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

/** Common behaviour, encapsulated in an abstract Activity. */
@EActivity
public abstract class BaseActivity extends FragmentActivity
        implements PullToRefreshAttacher.OnRefreshListener{

    /** Access to remote data. */
    @Bean
    protected CrunchbaseClient client;

    /** Access to application preferences. */
    @Pref
    protected Preferences_ preferences;

    protected PullToRefreshAttacher attacher;

    /** Reference to the options menu. Initialized when menu is inflated. */
    protected Menu menu;

    /** Request common window features, BaseActivity has no view of its own. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final PullToRefreshAttacher.Options options =
                new PullToRefreshAttacher.Options();
        options.headerTransformer = new HeaderTransformer();
        attacher = PullToRefreshAttacher.get(this, options);
    }

    /** Create the options menu, store a reference to it for later use. */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        this.menu = menu;
        return true;
    }

    /** Initialize the state of the options menu items based on stored prefs. */
    @AfterViews
    public void initMenuState() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        /*((Switch) findViewById(R.id.loadImagesSwitch))
                .setChecked(preferences.loadImages().get());
        ((Switch) findViewById(R.id.cacheImagesSwitch))
                .setChecked(preferences.cacheImages().get());
        ((Switch) findViewById(R.id.cacheDataSwitch))
                .setChecked(preferences.cacheData().get());*/
    }

    public void addRefreshableView(final View view) {
        attacher.addRefreshableView(view, this);
    }

    @Override
    public void onRefreshStarted(final View view) {
        refreshButton();
    }

    public void onRefreshCompleted() {
        attacher.setRefreshComplete();
    }

    public abstract void refresh();

    @OptionsItem(R.id.refreshButton)
    public void refreshButton() {
        attacher.setRefreshing(true);
        refresh();
    }

    /** Listener for the action bar search button. */
    @OptionsItem(R.id.searchButton)
    public void searchButton() {
        Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
    }

}