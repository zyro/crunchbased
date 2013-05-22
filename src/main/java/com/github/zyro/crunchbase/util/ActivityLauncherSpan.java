package com.github.zyro.crunchbase.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import com.github.zyro.crunchbase.R;
import lombok.AllArgsConstructor;

/**
 * A span to replace URLSpan in a TextView to directly launch an activity rather
 * than allow the system to determine the best handler for a link.
 */
@AllArgsConstructor
public class ActivityLauncherSpan extends ClickableSpan {

    /** The parent activity that launches the desired activity. */
    protected Activity parentActivity;

    /** The URL to pass to the launched activity. */
    protected String url;

    /** The launched activity class. */
    protected Class<? extends Activity> activityClass;

    /** Launch the specified activity when this span is clicked. */
    @Override
    public void onClick(final View view) {
        final Intent intent = new Intent(parentActivity, activityClass);
        intent.setData(Uri.parse(url));
        parentActivity.startActivity(intent);
        parentActivity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }

    /** Override to set custom style elements. */
    @Override
    public void updateDrawState(final TextPaint textPaint) {
        super.updateDrawState(textPaint);
        textPaint.setFakeBoldText(true);
    }

}