package com.github.zyro.crunchbased.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.github.zyro.crunchbased.R;
import com.github.zyro.crunchbased.entity.FundingRound;
import com.github.zyro.crunchbased.util.ActivityLauncherListener;
import com.github.zyro.crunchbased.util.RefreshMessage;
import com.github.zyro.crunchbased.entity.Company;
import com.github.zyro.crunchbased.entity.PersonShort;
import com.github.zyro.crunchbased.entity.RelationshipToPerson;
import com.github.zyro.crunchbased.util.FormatUtils;
import com.googlecode.androidannotations.annotations.*;

import org.apache.commons.lang3.text.WordUtils;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;

@EActivity(R.layout.company)
public class CompanyActivity extends BaseActivity<Company> {

    @SystemService
    protected LayoutInflater layoutInflater;

    /** The permalink of the company being displayed. */
    protected String permalink;

    @Override
    public void onCreate(final Bundle saved) {
        super.onCreate(saved);
        final Uri data = getIntent().getData();
        final List<String> params = data.getPathSegments();
        permalink = params.get(params.size() - 1);
    }

    @AfterViews
    public void initState() {
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        ((PullToRefreshLayout) findViewById(R.id.companyPtr))
                .setPullToRefreshAttacher(attacher, this);

        //findViewById(R.id.companyContents).setOnTouchListener(
        //        new SwipeBackListener(this));
        refreshButton();
    }

    @Override
    @Background
    public void refresh() {
        refreshStarted();
        client.getCompany(permalink, this);
    }

    @UiThread
    public void refreshDone(final Company company) {
        // Header

        if(company.getImage() != null) {
            client.loadImage(company.getImage().getLargeAsset(),
                    (ImageView) findViewById(R.id.companyImage));
        }

        ((TextView) findViewById(R.id.companyName)).setText(company.getName());

        final TextView companyUrl = (TextView) findViewById(R.id.companyUrl);
        companyUrl.setText(company.getHomepage_url());
        companyUrl.setMovementMethod(LinkMovementMethod.getInstance());
        Linkify.addLinks(companyUrl, Linkify.WEB_URLS);

        ((TextView) findViewById(R.id.companyFounded)).setText(
                getString(R.string.company_founded) +
                FormatUtils.formatDate(company.getFounded_day(),
                        company.getFounded_month(), company.getFounded_year(),
                        getString(R.string.unknown)));

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
            if(relationship.getIs_past() != null && relationship.getIs_past()) {
                continue;
            }

            final PersonShort person = relationship.getPerson();
            final View personItem = layoutInflater.inflate(R.layout.person_item, null);

            ((TextView) personItem.findViewById(R.id.personName)).setText(
                    person.getFirst_name() + " " + person.getLast_name());

            ((TextView) personItem.findViewById(R.id.personTitle)).setText(
                    relationship.getTitle());

            if(person.getImage() != null) {
                client.loadImage(person.getImage().getSmallAsset(),
                        (ImageView) personItem.findViewById(R.id.personImage));
            }
            personItem.setOnClickListener(new ActivityLauncherListener(
                    this, "person", person.getPermalink()));

            peopleHolder.addView(personItem);

            // Limit the size of the key people list.
            if(peopleHolder.getChildCount() >= 5) {
                break;
            }
        }
        peopleHolder.setVisibility(peopleHolder.getChildCount() > 0 ? LinearLayout.VISIBLE : LinearLayout.GONE);
        findViewById(R.id.companyPeopleLabel).setVisibility(
                peopleHolder.getChildCount() > 0 ? TextView.VISIBLE : TextView.GONE);

        // Funding Rounds

        final LinearLayout fundingHolder = (LinearLayout) findViewById(R.id.companyFundingHolder);
        fundingHolder.removeAllViews();
        Collections.sort(company.getFunding_rounds());
        for(final FundingRound fundingRound : company.getFunding_rounds()) {
            final View fundingItem = layoutInflater.inflate(R.layout.funding_item, null);

            ((TextView) fundingItem.findViewById(R.id.roundName)).setText(
                    (fundingRound.getRound_code().length() == 1 ?
                            getString(R.string.company_round) : "") +
                    WordUtils.capitalize(fundingRound.getRound_code()
                            .replace("_", " ")));

            ((TextView) fundingItem.findViewById(R.id.roundDate)).setText(
                    FormatUtils.formatDate(fundingRound.getFunded_day(),
                            fundingRound.getFunded_month(),
                            fundingRound.getFunded_year(), ""));

            if(isNotBlank(fundingRound.getRaised_currency_code()) &&
                    fundingRound.getRaised_amount() != null) {
                final NumberFormat format = NumberFormat.getCurrencyInstance();
                format.setCurrency(Currency.getInstance(
                        fundingRound.getRaised_currency_code()));
                ((TextView) fundingItem.findViewById(R.id.roundAmount)).setText(
                        format.format(fundingRound.getRaised_amount()));
            }
            else {
                ((TextView) fundingItem.findViewById(R.id.roundAmount)).setText(
                        getString(R.string.unknown));
            }

            fundingHolder.addView(fundingItem);
        }
        fundingHolder.setVisibility(fundingHolder.getChildCount() > 0 ? LinearLayout.VISIBLE : LinearLayout.GONE);
        findViewById(R.id.companyFundingLabel).setVisibility(
                fundingHolder.getChildCount() > 0 ? TextView.VISIBLE : TextView.GONE);

        findViewById(R.id.companyContents).setVisibility(View.VISIBLE);
        onRefreshCompleted();
        RefreshMessage.hideRefreshFailed(this);
    }

}