package com.github.zyro.crunchbase.util;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Listener that executes an Activity.onBackPressed() when it detects a right
 * swipe on the view component it is monitoring. Scales gestures to screen size.
 */
public class SwipeBackListener extends GestureDetector.SimpleOnGestureListener
        implements View.OnTouchListener {

    /** A reference to the activity to call onBackPressed() for. */
    protected final Activity activity;

    /** Internal gesture detector instance. */
    protected final GestureDetector gestureDetector;

    /** Minimum X-axis pixel distance for a swipe. */
    protected final int swipeMinDistance;

    /** Minimum velocity for a swipe. */
    protected final int swipeThresholdVelocity;

    /** Maximum Y-axis travel for a swipe. */
    protected final int swipeMaxOffPath;

    /** Initialise with an activity. */
    public SwipeBackListener(final Activity activity) {
        this.activity = activity;
        gestureDetector = new GestureDetector(this);

        final ViewConfiguration viewConfig = ViewConfiguration.get(activity);
        swipeMinDistance = viewConfig.getScaledPagingTouchSlop();
        swipeThresholdVelocity = viewConfig.getScaledMinimumFlingVelocity();
        swipeMaxOffPath = viewConfig.getScaledTouchSlop();
    }

    /** On touch handling, will invoke gesture detector. */
    @Override
    public boolean onTouch(final View view, final MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    /** Check if it was a right-fling, if so then execute back button action. */
    @Override
    public boolean onFling(final MotionEvent start, final MotionEvent end,
                           final float velocityX, final float velocityY) {
        if(start != null && end != null &&
                Math.abs(start.getY() - end.getY()) <= swipeMaxOffPath &&
                end.getX() - start.getX() > swipeMinDistance &&
                Math.abs(velocityX) > swipeThresholdVelocity) {
            activity.onBackPressed();
            return true;
        }

        return super.onFling(start, end, velocityX, velocityY);
    }

}