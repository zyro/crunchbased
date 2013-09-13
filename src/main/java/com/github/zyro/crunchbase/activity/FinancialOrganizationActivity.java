package com.github.zyro.crunchbase.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.zyro.crunchbase.R;
import com.github.zyro.crunchbase.entity.FinancialOrganization;
import com.github.zyro.crunchbase.entity.Fund;
import com.github.zyro.crunchbase.entity.FundingRoundShort;
import com.github.zyro.crunchbase.entity.Investment;
import com.github.zyro.crunchbase.entity.PersonShort;
import com.github.zyro.crunchbase.entity.RelationshipToPerson;
import com.github.zyro.crunchbase.util.ActivityLauncherListener;
import com.github.zyro.crunchbase.util.FormatUtils;
import com.github.zyro.crunchbase.util.RefreshMessage;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.SystemService;
import com.googlecode.androidannotations.annotations.UiThread;
import com.koushikdutta.async.future.FutureCallback;

import org.apache.commons.lang3.text.WordUtils;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@EActivity(R.layout.financial_organization)
public class FinancialOrganizationActivity extends BaseActivity
        implements FutureCallback<FinancialOrganization> {

    @SystemService
    protected LayoutInflater layoutInflater;

    /** The permalink of the financial organization being displayed. */
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

        ((PullToRefreshLayout) findViewById(R.id.financialOrganizationPtr))
                .setPullToRefreshAttacher(attacher, this);

        //findViewById(R.id.financialOrganizationContents).setOnTouchListener(
        //        new SwipeBackListener(this));
        refreshButton();
    }

    @Override
    @Background
    public void refresh() {
        refreshStarted();
        client.getFinancialOrganization(permalink, this);
    }

    @UiThread
    public void refreshStarted() {
        RefreshMessage.hideRefreshFailed(this);
    }

    @UiThread
    public void refreshDone(final FinancialOrganization financialOrganization) {
        // Header

        if(financialOrganization.getImage() != null) {
            client.loadImage(financialOrganization.getImage().getLargeAsset(),
                    (ImageView) findViewById(R.id.financialOrganizationImage));
        }

        ((TextView) findViewById(R.id.financialOrganizationName)).setText(
                financialOrganization.getName());

        final TextView financialOrganizationUrl = (TextView) findViewById(R.id.financialOrganizationUrl);
        financialOrganizationUrl.setText(financialOrganization.getHomepage_url());
        financialOrganizationUrl.setMovementMethod(LinkMovementMethod.getInstance());
        Linkify.addLinks(financialOrganizationUrl, Linkify.WEB_URLS);

        ((TextView) findViewById(R.id.financialOrganizationFounded)).setText(
                getString(R.string.financial_org_founded) +
                FormatUtils.formatDate(financialOrganization.getFounded_day(),
                        financialOrganization.getFounded_month(),
                        financialOrganization.getFounded_year(),
                        getString(R.string.unknown)));

        ((TextView) findViewById(R.id.financialOrganizationEmployees)).setText(
                getString(R.string.financial_org_employees) +
                (financialOrganization.getNumber_of_employees() != null ?
                        financialOrganization.getNumber_of_employees() : getString(R.string.unknown)));

        // Presence

        final Button financialOrganizationCrunchbaseUrl = (Button) findViewById(
                R.id.financialOrganizationCrunchbaseUrl);
        financialOrganizationCrunchbaseUrl.setVisibility(isBlank(
                financialOrganization.getCrunchbase_url()) ? Button.GONE : Button.VISIBLE);
        if(financialOrganizationCrunchbaseUrl.getVisibility() == Button.VISIBLE) {
            financialOrganizationCrunchbaseUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(financialOrganization.getCrunchbase_url())));
                }
            });
        }

        final Button financialOrganizationBlogUrl = (Button) findViewById(
                R.id.financialOrganizationBlogUrl);
        financialOrganizationBlogUrl.setVisibility(isBlank(
                financialOrganization.getBlog_url()) ? Button.GONE : Button.VISIBLE);
        if(financialOrganizationBlogUrl.getVisibility() == Button.VISIBLE) {
            financialOrganizationBlogUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(financialOrganization.getBlog_url())));
                }
            });
        }

        final Button financialOrganizationTwitter = (Button) findViewById(
                R.id.financialOrganizationTwitter);
        financialOrganizationTwitter.setVisibility(isBlank(
                financialOrganization.getTwitter_username()) ? Button.GONE : Button.VISIBLE);
        if(financialOrganizationTwitter.getVisibility() == Button.VISIBLE) {
            financialOrganizationTwitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://twitter.com/" +
                                    financialOrganization.getTwitter_username())));
                }
            });
        }

        final Button financialOrganizationEmail = (Button) findViewById(
                R.id.financialOrganizationEmail);
        financialOrganizationEmail.setVisibility(isBlank(
                financialOrganization.getEmail_address()) ? Button.GONE : Button.VISIBLE);
        if(financialOrganizationEmail.getVisibility() == Button.VISIBLE) {
            financialOrganizationEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    startActivity(new Intent(Intent.ACTION_SENDTO,
                            Uri.fromParts("mailto", financialOrganization.getEmail_address(), null)));
                }
            });
        }

        // Overview

        final TextView financialOrganizationOverview = (TextView) findViewById(
                R.id.financialOrganizationOverview);
        if(isBlank(financialOrganization.getOverview())) {
            financialOrganizationOverview.setVisibility(TextView.GONE);
            findViewById(R.id.financialOrganizationOverviewLabel).setVisibility(TextView.GONE);
        }
        else {
            financialOrganizationOverview.setText(FormatUtils.trim(
                    FormatUtils.htmlLinkify(financialOrganization.getOverview(), this)));
            financialOrganizationOverview.setMovementMethod(LinkMovementMethod.getInstance());
            financialOrganizationOverview.setVisibility(TextView.VISIBLE);
            findViewById(R.id.financialOrganizationOverviewLabel).setVisibility(TextView.VISIBLE);
        }

        // People

        final LinearLayout peopleHolder = (LinearLayout) findViewById(
                R.id.financialOrganizationPeopleHolder);
        peopleHolder.removeAllViews();
        for(final RelationshipToPerson relationship : financialOrganization.getRelationships()) {
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
        findViewById(R.id.financialOrganizationPeopleLabel).setVisibility(
                peopleHolder.getChildCount() > 0 ? TextView.VISIBLE : TextView.GONE);

        // Funds

        final LinearLayout fundsHolder = (LinearLayout) findViewById(R.id.financialOrganizationFundsHolder);
        fundsHolder.removeAllViews();
        Collections.sort(financialOrganization.getFunds());
        for(final Fund fund : financialOrganization.getFunds()) {
            final View fundItem = layoutInflater.inflate(R.layout.fund_item, null);

            ((TextView) fundItem.findViewById(R.id.fundName)).setText(
                    fund.getName());

            ((TextView) fundItem.findViewById(R.id.fundDate)).setText(
                    FormatUtils.formatDate(fund.getFunded_day(),
                            fund.getFunded_month(), fund.getFunded_year(), ""));

            if(isNotBlank(fund.getRaised_currency_code()) &&
                    fund.getRaised_amount() != null) {
                final NumberFormat format = NumberFormat.getCurrencyInstance();
                format.setCurrency(Currency.getInstance(
                        fund.getRaised_currency_code()));
                ((TextView) fundItem.findViewById(R.id.fundAmount)).setText(
                        format.format(fund.getRaised_amount()));
            }
            else {
                ((TextView) fundItem.findViewById(R.id.fundAmount)).setText(
                        getString(R.string.unknown));
            }

            fundsHolder.addView(fundItem);
        }
        fundsHolder.setVisibility(fundsHolder.getChildCount() > 0 ?
                LinearLayout.VISIBLE : LinearLayout.GONE);
        findViewById(R.id.financialOrganizationFundsLabel).setVisibility(
                fundsHolder.getChildCount() > 0 ? TextView.VISIBLE : TextView.GONE);

        // Recently funded

        final LinearLayout recentHolder = (LinearLayout) findViewById(
                R.id.financialOrganizationRecentHolder);
        recentHolder.removeAllViews();
        Collections.sort(financialOrganization.getInvestments());
        for(final Investment investment : financialOrganization.getInvestments()) {
            final FundingRoundShort round = investment.getFunding_round();
            final View recentItem = layoutInflater.inflate(R.layout.recent_item, null);

            ((TextView) recentItem.findViewById(R.id.recentName)).setText(
                    round.getCompany().getName());

            ((TextView) recentItem.findViewById(R.id.recentRound)).setText(
                    (round.getRound_code().length() == 1 ?
                            getString(R.string.financial_org_round) : "") +
                            WordUtils.capitalize(round.getRound_code()
                                    .replace("_", " ")));

            ((TextView) recentItem.findViewById(R.id.recentDate)).setText(
                    FormatUtils.formatDate(round.getFunded_day(),
                            round.getFunded_month(), round.getFunded_year(),
                            getString(R.string.unknown)));

            if(isNotBlank(round.getRaised_currency_code()) &&
                    round.getRaised_amount() != null) {
                final NumberFormat format = NumberFormat.getCurrencyInstance();
                format.setCurrency(Currency.getInstance(
                        round.getRaised_currency_code()));
                ((TextView) recentItem.findViewById(R.id.recentAmount)).setText(
                        format.format(round.getRaised_amount()));
            }
            else {
                ((TextView) recentItem.findViewById(R.id.recentAmount)).setText(
                        getString(R.string.unknown));
            }

            if(round.getCompany().getImage() != null) {
                client.loadImage(round.getCompany().getImage().getSmallAsset(),
                        (ImageView) recentItem.findViewById(R.id.recentImage));
            }
            recentItem.setOnClickListener(new ActivityLauncherListener(
                    this, "company", round.getCompany().getPermalink()));

            recentHolder.addView(recentItem);

            // Limit the size of the key people list.
            if(recentHolder.getChildCount() >= 5) {
                break;
            }
        }
        recentHolder.setVisibility(recentHolder.getChildCount() > 0 ?
                LinearLayout.VISIBLE : LinearLayout.GONE);
        findViewById(R.id.financialOrganizationRecentLabel).setVisibility(
                recentHolder.getChildCount() > 0 ? TextView.VISIBLE : TextView.GONE);

        findViewById(R.id.financialOrganizationContents).setVisibility(View.VISIBLE);
        onRefreshCompleted();
        RefreshMessage.hideRefreshFailed(this);
    }

    @UiThread
    public void refreshFailed() {
        onRefreshCompleted();
        RefreshMessage.showRefreshFailed(this);
    }

    @Override
    public void onCompleted(final Exception e,
                            final FinancialOrganization financialOrganization) {
        if(e == null) {
            refreshDone(financialOrganization);
        }
        else {
            refreshFailed();
        }
    }

}