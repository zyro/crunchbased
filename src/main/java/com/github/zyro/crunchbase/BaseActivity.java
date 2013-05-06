package com.github.zyro.crunchbase;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import com.github.zyro.crunchbase.service.ApiClient;
import com.github.zyro.crunchbase.service.Preferences_;
import com.github.zyro.crunchbase.service.WebClient;
import com.github.zyro.crunchbase.util.SlidingLayer;
import com.googlecode.androidannotations.annotations.*;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Common behaviour, should not be used directly as an Activity. Behaves
 * strangely if made 'abstract', likely due to AndroidAnnotations.
 */
@EActivity
public class BaseActivity extends Activity {

    /** Reference to the options Menu. Initialized when menu is inflated. */
    protected Menu menu;

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
    @ViewById(R.id.slidingMenu)
    protected SlidingLayer sl;

    /** Request common window features, BaseActivity has no view of its own. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
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
        ((CheckBox) findViewById(R.id.imagesCheckbox))
                .setChecked(preferences.loadImages().get());
        ((CheckBox) findViewById(R.id.cacheCheckbox))
                .setChecked(preferences.cacheEnabled().get());
    }

    /** Listener for the action bar search button. */
    @OptionsItem(R.id.searchButton)
    public void searchButton() {
        Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
    }

    /** Listener for sliding menu checkbox items. */
    public void onCheckboxClicked(final View view) {
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

    /** Listener for the sliding menu 'About' button. */
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

    /** Utility method to toggle the sliding menu. */
    public void toggleSlidingMenu() {
        if(sl.isOpened()) {
            sl.closeLayer(true);
        }
        else {
            sl.openLayer(true);
        }
    }

    /** Back button listener. Close sliding menu if open, call super if not. */
    @Override
    public void onBackPressed() {
        if(sl.isOpened()) {
            sl.closeLayer(true);
        }
        else {
            super.onBackPressed();
            if(!isTaskRoot()) {
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        }
    }

    /** Key listener specifically for Menu button. Toggles the sliding menu. */
    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_MENU) {
            toggleSlidingMenu();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}