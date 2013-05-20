package com.github.zyro.crunchbase.activity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.zyro.crunchbase.R;
import com.github.zyro.crunchbase.entity.Company;
import com.github.zyro.crunchbase.entity.Image;
import com.github.zyro.crunchbase.util.SwipeBackListener;
import com.googlecode.androidannotations.annotations.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EActivity(R.layout.company)
public class CompanyActivity extends BaseActivity {

    /** The permalink of the company being displayed. */
    protected String permalink;

    protected Map<Image, Bitmap> images = new HashMap<Image, Bitmap>();

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
        final Company company = apiClient.getCompany(permalink);
        if(company.getImage() != null) {
            Bitmap b = webClient.getLargeImage(company.getImage());
            images.put(company.getImage(), b);
        }
        refreshCompanyDetailsDone(company);
    }

    @UiThread
    public void refreshCompanyDetailsStarted() {
        invalidateOptionsMenu();
        menu.findItem(R.id.refreshButton).setVisible(false);
        setProgressBarIndeterminateVisibility(true);

        //if(adapter.isEmpty()) {
        //    final TextView label = (TextView) findViewById(R.id.companyEmpty);
        //    label.setText(R.string.refreshing);
        //    label.setVisibility(View.VISIBLE);
        //}
    }

    @UiThread
    public void refreshCompanyDetailsDone(final Company company) {
        ((ImageView) findViewById(R.id.companyImage)).setImageBitmap(images.get(company.getImage()));
        findViewById(R.id.companyImage).invalidate();

        ((TextView) findViewById(R.id.companyName)).setText(company.getName());

        final TextView companyUrl = (TextView) findViewById(R.id.companyUrl);
        companyUrl.setText(company.getHomepage_url());
        companyUrl.setMovementMethod(LinkMovementMethod.getInstance());
        Linkify.addLinks(companyUrl, Linkify.WEB_URLS);

        ((TextView) findViewById(R.id.companyFounded)).setText(
                getString(R.string.company_founded) + company.getFounded_day() +
                "/" + company.getFounded_month() + "/" + company.getFounded_year());

        ((TextView) findViewById(R.id.companyEmployees)).setText(
                getString(R.string.company_employees) + company.getNumber_of_employees());

        ((TextView) findViewById(R.id.companyTotalMoneyRaised)).setText(
                getString(R.string.company_total_money) + company.getTotal_money_raised());

        final TextView companyOverview = (TextView) findViewById(R.id.companyOverview);
        final String overview = company.getOverview().replace("href=\"/",
                "href=\"http://www.crunchbase.com/");
        companyOverview.setText(Html.fromHtml(overview));
        companyOverview.setMovementMethod(LinkMovementMethod.getInstance());

        ((TextView) findViewById(R.id.companyRaw)).setText(company.toString());

        //tfindViewById(R.id.companyOverviewHolder).invalidate();

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