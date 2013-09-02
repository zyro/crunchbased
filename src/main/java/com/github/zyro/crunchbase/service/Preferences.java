package com.github.zyro.crunchbase.service;

import com.googlecode.androidannotations.annotations.sharedpreferences.*;

/** Application-wide settings. */
@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface Preferences {

    /** Flag to toggle image loading and display. */
    @DefaultBoolean(true)
    boolean loadImages();

}