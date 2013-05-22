package com.github.zyro.crunchbase.util;

import android.app.Activity;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import com.github.zyro.crunchbase.R;
import com.github.zyro.crunchbase.activity.CompanyActivity_;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** Utilities to apply custom linking logic to text. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CrunchBaseLinkify {

    /**
     * Clean up the text by ensuring links are valid, process it as HTML, then
     * replace any known CrunchBase link URL spans with activity launcher spans.
     *
     * @param text The text String to process.
     * @param activity The Activity instance that will appear to launch other
     *                 activities via the relevant spans.
     * @return A SpannableString that may be added to a View.
     */
    public static SpannableString htmlLinkify(final String text,
                                              final Activity activity) {
        // perform some link cleanup and HTML-ify text.
        final SpannableString s = new SpannableString(Html.fromHtml(text
                .replace("href=\"/","href=\"http://www.crunchbase.com/")
                .replace("href=\"www","href=\"http://www")));

        // Extract URL spans.
        final URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);
        for(final URLSpan span: spans) {
            // Ignore any span that can stay as it is.
            if(!span.getURL().contains("www.crunchbase.com/company/")) {
                continue;
            }

            // Remove the old URL span.
            int start = s.getSpanStart(span);
            int end = s.getSpanEnd(span);
            s.removeSpan(span);

            // Replace it with custom color and activity launcher spans.
            final ActivityLauncherSpan activitySpan = new ActivityLauncherSpan(
                    activity, span.getURL(), CompanyActivity_.class);
            final ForegroundColorSpan fgSpan = new ForegroundColorSpan(
                    activity.getResources().getColor(R.color.crunchbase));
            s.setSpan(activitySpan, start, end, 0);
            s.setSpan(fgSpan, start, end, 0);
        }

        return s;
    }

}