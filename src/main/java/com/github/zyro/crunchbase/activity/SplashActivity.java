package com.github.zyro.crunchbase.activity;

import android.app.Activity;
import com.github.zyro.crunchbase.activity.HomeActivity_;
import com.github.zyro.crunchbase.R;
import com.googlecode.androidannotations.annotations.*;

/** Splash screen activity, will display logo and go to Home after a delay. */
@EActivity(R.layout.splash)
@NoTitle
public class SplashActivity extends Activity {

    /** Start the timer as soon as display is complete. */
    @AfterViews
    protected void continueToHome() {
        continueToHomeAfterDelay();
    }

    /** After a 2 second delay, launch the Home activity and finish this one. */
    @UiThread(delay = 2000)
    protected void continueToHomeAfterDelay() {
        HomeActivity_.intent(this).start();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        this.finish();
    }

}