package com.github.zyro.crunchbase.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewConfiguration;

import com.github.zyro.crunchbase.R;
import com.github.zyro.crunchbase.service.CrunchbaseClient;
import com.github.zyro.crunchbase.service.Preferences_;
import com.github.zyro.crunchbase.util.HeaderTransformer;
import com.github.zyro.crunchbase.util.SearchDialog;
import com.googlecode.androidannotations.annotations.*;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;

import java.lang.reflect.Field;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

/** Common behaviour, encapsulated in an abstract Activity. */
@EActivity
@OptionsMenu(R.menu.menu)
public abstract class BaseActivity extends FragmentActivity
        implements PullToRefreshAttacher.OnRefreshListener{

    /** Access to remote data. */
    @Bean
    protected CrunchbaseClient client;

    /** Access to application preferences. */
    @Pref
    protected Preferences_ preferences;

    protected PullToRefreshAttacher attacher;

    /** Request common window features, BaseActivity has no view of its own. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Force the overflow menu even if there is a physical menu button.
        try {
            final ViewConfiguration config = ViewConfiguration.get(this);
            final Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        }
        catch(final Exception e) {} // Don't care if it doesn't work.

        final PullToRefreshAttacher.Options options =
                new PullToRefreshAttacher.Options();
        options.headerTransformer = new HeaderTransformer();
        attacher = PullToRefreshAttacher.get(this, options);
    }

    /** Initialize common state elements. */
    @AfterViews
    public void initBaseState() {
        getActionBar().setDisplayShowTitleEnabled(false);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        /*((Switch) findViewById(R.id.loadImagesSwitch))
                .setChecked(preferences.loadImages().get());*/
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

    @OptionsItem(android.R.id.home)
    public void homeButton() {
        super.onBackPressed();
    }

    @OptionsItem(R.id.refreshButton)
    public void refreshButton() {
        attacher.setRefreshing(true);
        refresh();
    }

    /** Listener for the action bar search button. */
    @OptionsItem(R.id.searchButton)
    public void searchButton() {
        SearchDialog.showSearchDialog(this);
    }

}