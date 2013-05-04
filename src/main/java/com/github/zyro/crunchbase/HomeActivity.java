package com.github.zyro.crunchbase;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import com.github.zyro.crunchbase.service.ApiClient;
import com.github.zyro.crunchbase.service.Preferences_;
import com.github.zyro.crunchbase.util.GitHubLinkify;
import com.github.zyro.crunchbase.util.SlidingLayer;
import com.googlecode.androidannotations.annotations.*;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;

@EActivity(R.layout.home)
public class HomeActivity extends Activity {

    @Bean
    ApiClient apiClient;

    @Pref
    Preferences_ preferences;

    @ViewById(R.id.slidingMenu)
    SlidingLayer sl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    }

    @AfterViews
    public void initState() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        ((CheckBox) findViewById(R.id.imagesCheckbox))
                .setChecked(preferences.loadImages().get());
        ((CheckBox) findViewById(R.id.cacheCheckbox))
                .setChecked(preferences.cacheEnabled().get());

        get();
    }

    @Background
    public void get() {
        started();
        String s = apiClient.showCompany("facebook");
        done(s);
    }

    @UiThread
    public void started() {
        setProgressBarIndeterminateVisibility(true);
    }

    @UiThread
    public void done(String s) {
        ((TextView) findViewById(R.id.homeText)).setText(s);
        setProgressBarIndeterminateVisibility(false);
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
        aboutView.setText(getString(R.string.about_full));
        aboutView.setMovementMethod(LinkMovementMethod.getInstance());
        GitHubLinkify.addLinks(aboutView);
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.menu_aboutItem))
                .setView(aboutView)
                .setNeutralButton(getString(R.string.about_dismiss), null)
                .show();
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