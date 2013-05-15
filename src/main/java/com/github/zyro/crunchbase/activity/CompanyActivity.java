package com.github.zyro.crunchbase.activity;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.zyro.crunchbase.R;
import com.github.zyro.crunchbase.entity.Company;
import com.github.zyro.crunchbase.entity.Image;
import com.github.zyro.crunchbase.util.SwipeBackListener;
import com.googlecode.androidannotations.annotations.*;

import java.util.HashMap;
import java.util.Map;

@EActivity(R.layout.company)
public class CompanyActivity extends BaseActivity {

    /** The permalink of the company being displayed. */
    @Extra
    protected String permalink;

    protected Map<Image, Bitmap> images = new HashMap<Image, Bitmap>();

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
        ((ImageView) findViewById(R.id.companyImage)).invalidate();
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