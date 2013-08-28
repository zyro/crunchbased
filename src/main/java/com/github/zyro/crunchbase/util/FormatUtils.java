package com.github.zyro.crunchbase.util;

import android.app.Activity;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import com.github.zyro.crunchbase.R;
import com.github.zyro.crunchbase.activity.CompanyActivity_;
import com.github.zyro.crunchbase.activity.PersonActivity_;
import com.github.zyro.crunchbase.entity.Company;
import com.github.zyro.crunchbase.entity.Person;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

/** Various static utility methods to format or convert miscellaneous data. */
public class FormatUtils {

    /** Strictly a static class. */
    private FormatUtils() {}

    // The date/time format to convert timestamps to.
    private static final SimpleDateFormat FORMAT =
            new SimpleDateFormat("dd/MM/yyyy '@' HH:mm:ss");

    // Date symbols used to convert month numbers to names.
    private static final DateFormatSymbols SYMBOLS = new DateFormatSymbols();

    /**
     * Format a long millisecond timestamp based on existing configuration.
     * Target date/time format is: dd/MM/yyyy '@' HH:mm:ss
     *
     * @param timestamp A long value representing a timestamp.
     * @return A String containing the formatted date/time.
     */
    public static String formatTimestamp(final long timestamp) {
        return FORMAT.format(new Date(timestamp));
    }

    /**
     * Convert a month number to the corresponding month name.
     *
     * @param monthNumber An Integer representing a month name.
     * @return A String containing the month name if the input is a non-null
     *         number X (where 1 <= X and 12 >= X), null otherwise.
     */
    public static String monthNumberToName(final Integer monthNumber) {
        if(monthNumber == null || monthNumber < 1 || monthNumber > 12) {
            return null;
        }
        return SYMBOLS.getMonths()[monthNumber - 1];
    }

    /**
     * Attempt to extract a human-readable date of founding from a Company.
     *
     * @param company The Company to extract the date founded from.
     * @param fallback The fallback String to return if date extraction fails.
     * @return A String representing the date founded of the company, or the
     *         fallback string if it cannot be determined.
     */
    public static String extractDateFounded(final Company company,
                                            final String fallback) {
        return prettyFormatDate(company.getFounded_day(),
                                company.getFounded_month(),
                                company.getFounded_year(),
                                fallback);
    }

    /**
     * Attempt to extract a human-readable date of birth from a Person.
     *
     * @param person The Person to extract the date of birth from.
     * @param fallback The fallback String to return if date extraction fails.
     * @return A String representing the date of birth of the person, or the
     *         fallback string if it cannot be determined.
     */
    public static String extractDateBorn(final Person person,
                                         final String fallback) {
        return prettyFormatDate(person.getBorn_day(),
                                person.getBorn_month(),
                                person.getBorn_year(),
                                fallback);
    }

    /**
     * Create a pretty string representation of a given day/month/year date.
     * Will NOT validate the parameters to ensure they form a sensible date.
     *
     * @param day The day of the required date.
     * @param month The month of the required date.
     * @param year The year of the required date.
     * @param fallback The fallback String to return if date formatting fails.
     * @return A String representing the desired date, or the fallback string if
     *         it cannot be determined.
     */
    public static String prettyFormatDate(final Integer day,
                                          final Integer month,
                                          final Integer year,
                                          final String fallback) {
        String date = "";
        date += day != null ? day + " " : "";
        date += month != null ? monthNumberToName(month) + " " : "";
        date += year != null ? year : "";
        return StringUtils.isBlank(date) ? fallback : date;
    }

    /**
     * Remove leading and trailing whitespace characters (not just spaces) from
     * a given piece of text. Particularly useful for removing the trailing
     * whitespace from HTML-formatted strings.
     *
     * @param text The text to trim.
     * @return The trimmed text.
     */
    public static CharSequence trim(final CharSequence text) {
        int from = 0;
        int to = text.length();

        while(from < to && Character.isWhitespace(text.charAt(from))) {
            from++;
        }

        while(to > from && Character.isWhitespace(text.charAt(to - 1))) {
            to--;
        }

        return text.subSequence(from, to);
    }

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
        // Perform some link cleanup and HTML-ify text.
        final SpannableString s = new SpannableString(Html.fromHtml(text
                .replace("href=\"/", "href=\"http://www.crunchbase.com/")
                .replace("href=\"www", "href=\"http://www")));

        // Extract URL spans.
        final URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);
        for(final URLSpan span: spans) {
            // Decide which internal activity this span should launch.
            final Class<? extends Activity> activityClassToLaunch;
            if(span.getURL().contains("www.crunchbase.com/company/")) {
                activityClassToLaunch = CompanyActivity_.class;
            }
            else if(span.getURL().contains("www.crunchbase.com/person/")) {
                activityClassToLaunch = PersonActivity_.class;
            }
            else {
                // Ignore any span that can stay as it is.
                continue;
            }

            // Remove the old URL span.
            int start = s.getSpanStart(span);
            int end = s.getSpanEnd(span);
            s.removeSpan(span);

            // Replace it with custom color and activity launcher spans.
            final ActivityLauncherSpan activitySpan = new ActivityLauncherSpan(
                    activity, span.getURL(), activityClassToLaunch);
            final ForegroundColorSpan fgSpan = new ForegroundColorSpan(
                    activity.getResources().getColor(R.color.crunchbase));
            s.setSpan(activitySpan, start, end, 0);
            s.setSpan(fgSpan, start, end, 0);
        }

        return s;
    }

}