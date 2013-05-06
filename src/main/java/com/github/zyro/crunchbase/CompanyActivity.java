package com.github.zyro.crunchbase;

import android.widget.TextView;
import com.github.zyro.crunchbase.entity.Company;
import com.googlecode.androidannotations.annotations.*;

@EActivity(R.layout.company)
public class CompanyActivity extends BaseActivity {

    /** The permalink of the company being displayed. */
    @Extra
    protected String permalink;

    @AfterViews
    public void initState() {
        refreshCompanyDetails();
    }

    @Background
    public void refreshCompanyDetails() {
        refreshCompanyDetailsStarted();
        final Company company = apiClient.showCompany(permalink);
        refreshCompanyDetailsDone(company);
    }

    @UiThread
    public void refreshCompanyDetailsStarted() {
        invalidateOptionsMenu();
        menu.findItem(R.id.refreshButton).setVisible(false);
        setProgressBarIndeterminateVisibility(true);

        //if(adapter.isEmpty()) {
        //    final TextView label = (TextView) findViewById(R.id.trendingEmpty);
        //    label.setText(R.string.refreshing);
        //    label.setVisibility(View.VISIBLE);
        //}
    }

    @UiThread
    public void refreshCompanyDetailsDone(final Company company) {
        ((TextView) findViewById(R.id.companyData)).setText(company.toString());

        setProgressBarIndeterminateVisibility(false);
        invalidateOptionsMenu();

        //final TextView label = (TextView) findViewById(R.id.trendingEmpty);
        //label.setText(R.string.no_items);
        //label.setVisibility(adapter.isEmpty() ? View.VISIBLE : View.GONE);
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