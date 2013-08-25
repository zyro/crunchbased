package com.github.zyro.crunchbase.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.github.zyro.crunchbase.R;
import com.github.zyro.crunchbase.entity.Company;
import com.github.zyro.crunchbase.entity.Image;
import com.github.zyro.crunchbase.entity.PersonShort;
import com.github.zyro.crunchbase.entity.RelationshipToPerson;
import com.github.zyro.crunchbase.service.ClientException;
import com.github.zyro.crunchbase.util.AsyncImageLoadListener;
import com.github.zyro.crunchbase.util.FormatUtils;
import com.github.zyro.crunchbase.util.SwipeBackListener;
import com.googlecode.androidannotations.annotations.*;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.List;

@EActivity(R.layout.company)
public class CompanyActivity extends BaseActivity implements AsyncImageLoadListener {

    /** The permalink of the company being displayed. */
    protected String permalink;

    @ViewById(R.id.companyEmpty)
    protected TextView empty;

    @SystemService
    protected LayoutInflater layoutInflater;

    @Override
    public void onCreate(final Bundle saved) {
        super.onCreate(saved);
        final Uri data = getIntent().getData();
        final List<String> params = data.getPathSegments();
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
            /*if(company.getImage() != null) {
                webClient.loadImage(company.getImage(), company.getImage().getLargeAsset());
            }
            int count = 0;
            for(final RelationshipToPerson relationship : company.getRelationships()) {
                if(relationship.getIs_past()) {
                    continue;
                }

                if(relationship.getPerson().getImage() != null) {
                    webClient.loadImage(relationship.getPerson().getImage(),
                            relationship.getPerson().getImage().getSmallAsset());
                }
                count++;
                if(count >= 5) {
                    break;
                }
            }*/
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

        if(company.getImage() != null) {
            loadImage(company.getImage().getLargeAsset(),
                    (ImageView) findViewById(R.id.companyImage));
        }

        ((TextView) findViewById(R.id.companyName)).setText(company.getName());

        final TextView companyUrl = (TextView) findViewById(R.id.companyUrl);
        companyUrl.setText(company.getHomepage_url());
        companyUrl.setMovementMethod(LinkMovementMethod.getInstance());
        Linkify.addLinks(companyUrl, Linkify.WEB_URLS);

        ((TextView) findViewById(R.id.companyFounded)).setText(
                getString(R.string.company_founded) +
                FormatUtils.extractDateFounded(company, getString(R.string.unknown)));

        ((TextView) findViewById(R.id.companyEmployees)).setText(
                getString(R.string.company_employees) +
                (company.getNumber_of_employees() != null ?
                        company.getNumber_of_employees() : getString(R.string.unknown)));

        ((TextView) findViewById(R.id.companyTotalMoneyRaised)).setText(
                getString(R.string.company_total_money) +
                (isBlank(company.getTotal_money_raised()) ?
                        getString(R.string.unknown) : company.getTotal_money_raised()));

        // Presence

        final Button companyCrunchbaseUrl = (Button) findViewById(R.id.companyCrunchbaseUrl);
        companyCrunchbaseUrl.setVisibility(isBlank(company.getCrunchbase_url()) ? Button.GONE : Button.VISIBLE);
        if(companyCrunchbaseUrl.getVisibility() == Button.VISIBLE) {
            companyCrunchbaseUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(company.getCrunchbase_url())));
                }
            });
        }

        final Button companyBlogUrl = (Button) findViewById(R.id.companyBlogUrl);
        companyBlogUrl.setVisibility(isBlank(company.getBlog_url()) ? Button.GONE : Button.VISIBLE);
        if(companyBlogUrl.getVisibility() == Button.VISIBLE) {
            companyBlogUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(company.getBlog_url())));
                }
            });
        }

        final Button companyTwitter = (Button) findViewById(R.id.companyTwitter);
        companyTwitter.setVisibility(isBlank(company.getTwitter_username()) ? Button.GONE : Button.VISIBLE);
        if(companyTwitter.getVisibility() == Button.VISIBLE) {
            companyTwitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://twitter.com/" + company.getTwitter_username())));
                }
            });
        }

        final Button companyEmail = (Button) findViewById(R.id.companyEmail);
        companyEmail.setVisibility(isBlank(company.getEmail_address()) ? Button.GONE : Button.VISIBLE);
        if(companyEmail.getVisibility() == Button.VISIBLE) {
            companyEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    startActivity(new Intent(Intent.ACTION_SENDTO,
                            Uri.fromParts("mailto", company.getEmail_address(), null)));
                }
            });
        }

        // Overview

        final TextView companyOverview = (TextView) findViewById(R.id.companyOverview);
        if(isBlank(company.getOverview())) {
            companyOverview.setVisibility(TextView.GONE);
            findViewById(R.id.companyOverviewLabel).setVisibility(TextView.GONE);
        }
        else {
            companyOverview.setText(FormatUtils.trim(FormatUtils.htmlLinkify(company.getOverview(), this)));
            companyOverview.setMovementMethod(LinkMovementMethod.getInstance());
            companyOverview.setVisibility(TextView.VISIBLE);
            findViewById(R.id.companyOverviewLabel).setVisibility(TextView.VISIBLE);
        }

        // People

        final LinearLayout peopleHolder = (LinearLayout) findViewById(R.id.companyPeopleHolder);
        peopleHolder.removeAllViews();
        for(final RelationshipToPerson relationship : company.getRelationships()) {
            if(relationship.getIs_past()) {
                continue;
            }

            final PersonShort person = relationship.getPerson();
            final View personItem = layoutInflater.inflate(R.layout.person_item, null);
            ((TextView) personItem.findViewById(R.id.personName)).setText(
                    person.getFirst_name() + " " + person.getLast_name());
            ((TextView) personItem.findViewById(R.id.personTitle)).setText(
                    relationship.getTitle());
            if(person.getImage() != null) {
                loadImage(person.getImage().getSmallAsset(),
                        (ImageView) personItem.findViewById(R.id.personImage));
                //((ImageView) personItem.findViewById(R.id.personImage)).setImageBitmap(
                //        person.getImage().getBitmap());
            }
            personItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    final Intent intent = new Intent(CompanyActivity.this, PersonActivity_.class);
                    intent.setData(Uri.parse("http://www.crunchbase.com/person/" +
                            person.getPermalink()));
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                }
            });

            peopleHolder.addView(personItem);

            // Limit the size of the key people list.
            if(peopleHolder.getChildCount() >= 5) {
                break;
            }
        }
        peopleHolder.setVisibility(peopleHolder.getChildCount() > 0 ? LinearLayout.VISIBLE : LinearLayout.GONE);
        findViewById(R.id.companyPeopleLabel).setVisibility(
                peopleHolder.getChildCount() > 0 ? TextView.VISIBLE : TextView.GONE);

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

    @Background
    protected void loadImage(final String asset, final ImageView view) {
        try {
            webClient.loadImage(asset, this, view);
        }
        catch(final ClientException e) {} // TODO proper handling
    }

    @Override
    @UiThread
    public void imageLoadComplete(final Bitmap bitmap, final ImageView view) {
        if(bitmap != null && view != null) {
            view.setImageBitmap(bitmap);
        }
    }

}