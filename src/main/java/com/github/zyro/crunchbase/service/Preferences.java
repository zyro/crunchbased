package com.github.zyro.crunchbase.service;

import com.googlecode.androidannotations.annotations.sharedpreferences.*;

@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface Preferences {

    @DefaultBoolean(true)
    boolean loadImages();

    @DefaultBoolean(false)
    boolean cacheEnabled();

}