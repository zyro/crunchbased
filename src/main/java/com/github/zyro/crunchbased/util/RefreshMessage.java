package com.github.zyro.crunchbased.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.github.zyro.crunchbased.R;
import com.github.zyro.crunchbased.activity.BaseActivity;

/** Helper to display a 'refresh failed' message on activities. */
public class RefreshMessage {

    /**
     * Show a 'refresh failed' message at the top of a given activity. When
     * clicked, the message view will trigger BaseActivity.refreshButton().
     *
     * @param activity The BaseActivity to attach the message to.
     */
    public static void showRefreshFailed(final BaseActivity activity) {
        final LayoutInflater inflater = (LayoutInflater)
                activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.refresh_message, null);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                removeMessage(activity, view);
                activity.refreshButton();
            }
        });

        addMessage(activity, view);
    }

    /**
     * Hide the 'refresh failed' message at the top of a given activity, if one
     * exists.
     *
     * @param activity The BaseActivity to remove the message from.
     */
    public static void hideRefreshFailed(final BaseActivity activity) {
        final View view = activity.findViewById(R.id.refreshMessage);
        if(view != null) {
            removeMessage(activity, view);
        }
    }

    /**
     * Add a 'refresh failed' view to a given activity.
     *
     * @param activity The BaseActivity to attach the message to.
     * @param view The message view to attach.
     */
    protected static void addMessage(final BaseActivity activity,
                                     final View view) {
        activity.addContentView(view, new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
        view.startAnimation(AnimationUtils.loadAnimation(activity,
                R.anim.slide_top_in));
    }

    /**
     * Remove a 'refresh failed' view from a given activity.
     *
     * @param activity The BaseActivity to remove the message from.
     * @param view The message view to remove.
     */
    protected static void removeMessage(final BaseActivity activity,
                                        final View view) {
        view.startAnimation(AnimationUtils.loadAnimation(activity,
                R.anim.slide_top_out));
        ((ViewGroup) view.getParent()).removeView(view);
    }

}