package com.github.zyro.crunchbased.util;

import com.github.zyro.crunchbased.R;

import uk.co.senab.actionbarpulltorefresh.library.DefaultHeaderTransformer;

/** A custom header transformer for the pull to refresh library. */
public class HeaderTransformer extends DefaultHeaderTransformer {

    /** Construct with default settings but enable custom colour. */
    public HeaderTransformer() {
        super();
        setProgressBarColor(R.color.crunchbase);
        setProgressBarColorEnabled(true);
    }

}
