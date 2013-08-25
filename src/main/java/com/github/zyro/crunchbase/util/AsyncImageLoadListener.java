package com.github.zyro.crunchbase.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Listener interface for entities that should be notified when an image bitmap
 * is available and the component where it should be displayed, as determined at
 * some prior point.
 */
public interface AsyncImageLoadListener {

    void imageLoadComplete(final Bitmap bitmap, final ImageView view);

}