package com.github.zyro.crunchbase.activity;

import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
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

        final TextView companyOverview = (TextView) findViewById(R.id.companyOverview);
        companyOverview.setText(FormatUtils.htmlLinkify(company.getOverview(), this));
        companyOverview.setMovementMethod(LinkMovementMethod.getInstance());
        findViewById(R.id.companyOverviewEmpty).setVisibility(
                companyOverview.getText().length() == 0 ? View.VISIBLE : View.GONE);

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