package com.github.zyro.crunchbase.util;

import com.github.zyro.crunchbase.R;

import uk.co.senab.actionbarpulltorefresh.library.DefaultHeaderTransformer;

/** A header transformer*/
public class HeaderTransformer extends DefaultHeaderTransformer {

    /** Construct with default settings but enable custom colour. */
    public HeaderTransformer() {
        super();
        setProgressBarColor(R.color.crunchbase);
        setProgressBarColorEnabled(true);
    }

}
