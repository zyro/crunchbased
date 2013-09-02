package com.github.zyro.crunchbase.activity;

import android.view.LayoutInflater;

import com.github.zyro.crunchbase.R;
import com.github.zyro.crunchbase.entity.Company;
import com.github.zyro.crunchbase.entity.Search;
import com.github.zyro.crunchbase.util.RefreshMessage;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.SystemService;
import com.googlecode.androidannotations.annotations.UiThread;
import com.koushikdutta.async.future.FutureCallback;

import static org.apache.commons.lang3.StringUtils.isBlank;

@EActivity(R.layout.search)
public class SearchActivity extends BaseActivity implements FutureCallback<Search> {

    @SystemService
    protected LayoutInflater layoutInflater;

    /** The search query to display results for. */
    @Extra("searchQuery")
    protected String query;

    /** The entity type to limit searches to. */
    @Extra("searchEntity")
    protected String entity;

    /** The entity field to limit searches to. */
    @Extra("searchField")
    protected String field;

    /** The currently visible page. */
    protected int page = 0;

    @AfterViews
    public void initState() {
        getActionBar().setDisplayHomeAsUpEnabled(true);

        //((PullToRefreshLayout) findViewById(R.id.companyPtr))
        //        .setPullToRefreshAttacher(attacher, this);

        //findViewById(R.id.companyContents).setOnTouchListener(
        //        new SwipeBackListener(this));
        refreshButton();
    }

    @Override
    @Background
    public void refresh() {
        refreshStarted();
        client.getSearchResults(query, 1, mapEntityType(entity), mapFieldName(field), this);
    }

    @UiThread
    public void refreshStarted() {
        RefreshMessage.hideRefreshFailed(this);
    }

    @UiThread
    public void refreshDone(final Company company) {
        //

        onRefreshCompleted();
        RefreshMessage.hideRefreshFailed(this);
    }

    @UiThread
    public void refreshFailed() {
        onRefreshCompleted();
        RefreshMessage.showRefreshFailed(this);
    }

    @Override
    public void onCompleted(final Exception e, final Search search) {}

    protected static String mapEntityType(final String entity) {
        if("Companies".equalsIgnoreCase(entity)) {
            return "company";
        }
        else if("People".equalsIgnoreCase(entity)) {
            return "person";
        }
        else if("Products".equalsIgnoreCase(entity)) {
            return "product";
        }
        else if("Financial Organizations".equalsIgnoreCase(entity)) {
            return "financial_org";
        }
        else if("Providers".equalsIgnoreCase(entity)) {
            return "provider";
        }
        else {
            return null;
        }
    }

    protected static String mapFieldName(final String field) {
        if("Name".equalsIgnoreCase(field)) {
            return "name";
        }
        else if("Overview".equalsIgnoreCase(field)) {
            return "overview";
        }
        else if("Homepage URL".equalsIgnoreCase(field)) {
            return "homepage_url";
        }
        else if("Tag List".equalsIgnoreCase(field)) {
            return "tag_list";
        }
        else {
            return null;
        }
    }

}