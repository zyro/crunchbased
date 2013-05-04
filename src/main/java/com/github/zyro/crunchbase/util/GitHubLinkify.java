package com.github.zyro.crunchbase.util;

import android.text.util.Linkify;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Utility to perform custom calls to Linkify for GitHub repositories. */
public class GitHubLinkify {

    /** Pattern to match "user/repo" elements in text. */
    public static final Pattern GH_PATTERN =
            Pattern.compile("[a-zA-Z0-9\\-]+/[a-zA-Z0-9\\-]+");

    /** Transform filter to prepend website to matched elements. */
    public static final Linkify.TransformFilter GH_TRANSFORM_FILTER =
            new Linkify.TransformFilter() {
                @Override
                public String transformUrl(Matcher match, String url) {
                    return "https://github.com/" + url;
                }
            };

    /**
     * Static utility method to convert matching patterns into links to GitHub
     * repositories. Will not Linkify any other links.
     *
     * @param textView The {@code TextView} to Linkify.
     */
    public static void addLinks(final TextView textView) {
        Linkify.addLinks(textView, GH_PATTERN, "", null, GH_TRANSFORM_FILTER);
    }

}