package com.github.zyro.crunchbased.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import com.github.zyro.crunchbased.R;

/**
 * A span to replace URLSpan in a TextView to directly launch an activity rather
 * than allow the system to determine the best handler for the underlying link.
 */
public class ActivityLauncherSpan extends ClickableSpan {

    /** The parent activity that launches the desired activity. */
    protected Activity parentActivity;

    /** The URL to pass to the launched activity. */
    protected String url;

    /** The launched activity class. */
    protected Class<? extends Activity> activityClass;

    /**
     * Create a span that on click will launch a specified internal activity.
     *
     * @param parentActivity The activity to use as the source of the intent.
     * @param url The underlying URL to pass to the launched activity.
     * @param activityClass The activity class to launch on span click.
     */
    public ActivityLauncherSpan(final Activity parentActivity, final String url,
                                final Class<? extends Activity> activityClass) {
        this.parentActivity = parentActivity;
        this.url = url;
        this.activityClass = activityClass;
    }

    /** Launch the specified activity when this span is clicked. */
    @Override
    public void onClick(final View view) {
        final Intent intent = new Intent(parentActivity, activityClass);
        intent.setData(Uri.parse(url));
        parentActivity.startActivity(intent);
        parentActivity.overridePendingTransition(
                R.anim.slide_right_in, R.anim.slide_left_out);
    }

    /** Override to set custom style elements. */
    @Override
    public void updateDrawState(final TextPaint textPaint) {
        super.updateDrawState(textPaint);
        textPaint.setFakeBoldText(true);
    }

}