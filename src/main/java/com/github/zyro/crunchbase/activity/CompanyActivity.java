package com.github.zyro.crunchbase.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.zyro.crunchbase.R;
import com.github.zyro.crunchbase.entity.Company;
import com.github.zyro.crunchbase.service.ClientException;
import com.github.zyro.crunchbase.util.FormatUtils;
import com.github.zyro.crunchbase.util.SwipeBackListener;
import com.googlecode.androidannotations.annotations.*;

import java.util.List;

@EActivity(R.layout.company)
public class CompanyActivity extends BaseActivity {

    /** The permalink of the company being displayed. */
    protected String permalink;

    @ViewById(R.id.companyEmpty)
    protected TextView empty;

    @Override
    public void onCreate(final Bundle saved) {
        super.onCreate(saved);
        Uri data = getIntent().getData();
        List<String> params = data.getPathSegments();
        permalink = params.get(params.size() - 1);
    }

    @AfterViews
    public void initState() {
        findViewById(R.id.companyContents).setOnTouchListener(
                new SwipeBackListener(this));
        refreshCompanyDetails();
    }

    @Background
    public void refreshCompanyDetails() {
        refreshCompanyDetailsStarted();
        try {
            final Company company = apiClient.getCompany(permalink);
            if(company.getImage() != null) {
                webClient.loadLargeImage(company.getImage());
            }
            refreshCompanyDetailsDone(company);
        }
        catch(final ClientException e) {
            refreshCompanyDetailsFailed();
        }
    }

    @UiThread
    public void refreshCompanyDetailsStarted() {
        invalidateOptionsMenu();
        menu.findItem(R.id.refreshButton).setVisible(false);
        setProgressBarIndeterminateVisibility(true);

        empty.setText(R.string.refreshing);
    }

    @UiThread
    public void refreshCompanyDetailsDone(final Company company) {
        // Header

        ((ImageView) findViewById(R.id.companyImage)).setImageBitmap(
                company.getImage().getBitmap());

        ((TextView) findViewById(R.id.companyName)).setText(company.getName());

        final TextView companyUrl = (TextView) findViewById(R.id.companyUrl);
        companyUrl.setText(company.getHomepage_url());
        companyUrl.setMovementMethod(LinkMovementMethod.getInstance());
        Linkify.addLinks(companyUrl, Linkify.WEB_URLS);

        ((TextView) findViewById(R.id.companyFounded)).setText(
                getString(R.string.company_founded) +
                (company.getFounded_day() != null ? company.getFounded_day() + " " : "")  +
                (company.getFounded_month() != null ?
                        FormatUtils.monthNumberToName(company.getFounded_month()) + " " : "") +
                (company.getFounded_year() != null ? company.getFounded_year() : getString(R.string.unknown)));

        ((TextView) findViewById(R.id.companyEmployees)).setText(
                getString(R.string.company_employees) +
                (company.getNumber_of_employees() != null ?
                        company.getNumber_of_employees() : getString(R.string.unknown)));

        ((TextView) findViewById(R.id.companyTotalMoneyRaised)).setText(
                getString(R.string.company_total_money) +
                (company.getTotal_money_raised() != null ?
                        company.getTotal_money_raised() : getString(R.string.unknown)));

        // Presence

        final Button companyCrunchbaseUrl = (Button) findViewById(R.id.companyCrunchbaseUrl);
        companyCrunchbaseUrl.setVisibility(company.getCrunchbase_url().isEmpty() ?
                        TextView.GONE : TextView.VISIBLE);
        if(companyCrunchbaseUrl.getVisibility() == TextView.VISIBLE) {
            companyCrunchbaseUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(company.getCrunchbase_url())));
                }
            });
        }

        final Button companyBlogUrl = (Button) findViewById(R.id.companyBlogUrl);
        companyBlogUrl.setVisibility(company.getBlog_url().isEmpty() ?
                TextView.GONE : TextView.VISIBLE);
        if(companyBlogUrl.getVisibility() == TextView.VISIBLE) {
            companyBlogUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(company.getBlog_url())));
                }
            });
        }

        final Button companyTwitter = (Button) findViewById(R.id.companyTwitter);
        companyTwitter.setVisibility(company.getTwitter_username().isEmpty() ?
                TextView.GONE : TextView.VISIBLE);
        if(companyTwitter.getVisibility() == TextView.VISIBLE) {
            companyTwitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://twitter.com/" + company.getTwitter_username())));
                }
            });
        }

        final Button companyEmail = (Button) findViewById(R.id.companyEmail);
        companyEmail.setVisibility(company.getEmail_address().isEmpty() ?
                TextView.GONE : TextView.VISIBLE);
        if(companyEmail.getVisibility() == TextView.VISIBLE) {
            companyEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    startActivity(new Intent(Intent.ACTION_SENDTO,
                            Uri.fromParts("mailto", company.getEmail_address(), null)));
                }
            });
        }

        findViewById(R.id.companyPresenceLabel).setVisibility(
                companyCrunchbaseUrl.getVisibility() == TextView.GONE &&
                companyBlogUrl.getVisibility() == TextView.GONE &&
                companyTwitter.getVisibility() == TextView.GONE &&
                companyEmail.getVisibility() == TextView.GONE ?
                        TextView.GONE : TextView.VISIBLE);

        findViewById(R.id.companyPresenceHolder).setVisibility(
                companyCrunchbaseUrl.getVisibility() == TextView.GONE &&
                companyBlogUrl.getVisibility() == TextView.GONE &&
                companyTwitter.getVisibility() == TextView.GONE &&
                companyEmail.getVisibility() == TextView.GONE ?
                        TextView.GONE : TextView.VISIBLE);

        // Overview

        final TextView companyOverview = (TextView) findViewById(R.id.companyOverview);
        companyOverview.setText(company.getOverview() == null || company.getOverview().trim().isEmpty() ?
                getString(R.string.company_overview_empty) : FormatUtils.htmlLinkify(company.getOverview(), this));
        if(getString(R.string.company_overview_empty).equals(companyOverview.getText())) {
            companyOverview.setGravity(Gravity.CENTER);
        }
        else {
            companyOverview.setText(FormatUtils.trim(companyOverview.getText()));
            companyOverview.setGravity(Gravity.NO_GRAVITY);
            companyOverview.setMovementMethod(LinkMovementMethod.getInstance());
        }

        ((TextView) findViewById(R.id.companyRaw)).setText(company.toString());

        setProgressBarIndeterminateVisibility(false);
        invalidateOptionsMenu();

        empty.setVisibility(View.GONE);
        findViewById(R.id.companyContents).setVisibility(View.VISIBLE);
    }

    @UiThread
    public void refreshCompanyDetailsFailed() {
        invalidateOptionsMenu();
        menu.findItem(R.id.refreshButton).setVisible(false);
        setProgressBarIndeterminateVisibility(true);

        empty.setText(R.string.no_items);
    }

    @OptionsItem(R.id.refreshButton)
    public void refreshButton() {
        refreshCompanyDetails();
    }

    @OptionsItem(android.R.id.home)
    public void homeButton() {
        super.onBackPressed();
    }

}