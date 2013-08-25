package com.github.zyro.crunchbase.activity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.github.zyro.crunchbase.R;
import com.github.zyro.crunchbase.service.ApiClient;
import com.github.zyro.crunchbase.service.Preferences_;
import com.github.zyro.crunchbase.service.WebClient;
import com.googlecode.androidannotations.annotations.*;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/** Common behaviour, encapsulated in an abstract Activity. */
@EActivity
public abstract class BaseActivity extends FragmentActivity {

    /** Access to remote API request functions. */
    @Bean
    protected ApiClient apiClient;

    /** Access to remote Web Page request functions. */
    @Bean
    protected WebClient webClient;

    /** Access to application preferences. */
    @Pref
    protected Preferences_ preferences;

    /** Reference to the sliding menu to bind open/close actions to. */
    protected SlidingMenu slidingMenu;

    /** Reference to the options menu. Initialized when menu is inflated. */
    protected Menu menu;

    /** Request common window features, BaseActivity has no view of its own. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        slidingMenu.setBehindOffsetRes(R.dimen.layer_offset);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        slidingMenu.setMenu(R.layout.sliding_menu);
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
        ((Switch) findViewById(R.id.imagesSwitch))
                .setChecked(preferences.loadImages().get());
        ((Switch) findViewById(R.id.cacheSwitch))
                .setChecked(preferences.cacheEnabled().get());
    }

    /** Listener for the action bar search button. */
    @OptionsItem(R.id.searchButton)
    public void searchButton() {
        Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
    }

    /** Listener for sliding menu Switch items. */
    public void onSwitchClicked(final View view) {
        final boolean on = ((Switch) view).isChecked();
        switch(view.getId()) {
            case R.id.imagesSwitch:
                preferences.loadImages().put(on);
                break;
            case R.id.cacheSwitch:
                preferences.cacheEnabled().put(on);
                Toast.makeText(this, "Cache " + on, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /** Listener for the sliding menu 'About' button. */
    @Click(R.id.aboutItem)
    public void aboutItem() {
        if(slidingMenu.isMenuShowing()) {
            slidingMenu.toggle();
        }
        final TextView aboutView = new TextView(this);
        aboutView.setText(R.string.about_text);
        aboutView.setHighlightColor(Color.alpha(0));
        aboutView.setMovementMethod(LinkMovementMethod.getInstance());
        Linkify.addLinks(aboutView, Linkify.WEB_URLS);
        new AlertDialog.Builder(this).setView(aboutView)
                .setNeutralButton(getString(R.string.about_dismiss), null)
                .setTitle(getString(R.string.menu_aboutItem)).show();
    }

    /** Back button listener. Close sliding menu if open, call super if not. */
    @Override
    public void onBackPressed() {
        if(slidingMenu.isMenuShowing()) {
            slidingMenu.toggle();
        }
        else {
            super.onBackPressed();
            //if(!isTaskRoot()) {
            //    overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            //}
        }
    }

    /** Key listener specifically for Menu button. Toggles the sliding menu. */
    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_MENU) {
            slidingMenu.toggle();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}