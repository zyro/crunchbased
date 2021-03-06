package com.github.zyro.crunchbased.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.github.zyro.crunchbased.R;
import com.github.zyro.crunchbased.util.ActivityLauncherListener;
import com.github.zyro.crunchbased.util.RefreshMessage;
import com.github.zyro.crunchbased.entity.*;
import com.github.zyro.crunchbased.util.FormatUtils;
import com.googlecode.androidannotations.annotations.*;

import java.util.List;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;

import static org.apache.commons.lang3.StringUtils.*;

@EActivity(R.layout.person)
public class PersonActivity extends BaseActivity<Person> {

    @SystemService
    protected LayoutInflater layoutInflater;

    /** The permalink of the person being displayed. */
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

        ((PullToRefreshLayout) findViewById(R.id.personPtr))
                .setPullToRefreshAttacher(attacher, this);

        //findViewById(R.id.personContents).setOnTouchListener(
        //        new SwipeBackListener(this));
        refreshButton();
    }

    @Override
    @Background
    public void refresh() {
        refreshStarted();
        client.getPerson(permalink, this);
    }

    @UiThread
    public void refreshDone(final Person person) {
        // Header

        if(person.getImage() != null) {
            client.loadImage(person.getImage().getLargeAsset(),
                    (ImageView) findViewById(R.id.personImage));
        }

        ((TextView) findViewById(R.id.personName)).setText(
                person.getFirst_name() + " " + person.getLast_name());

        ((TextView) findViewById(R.id.personBorn)).setText(
                getString(R.string.person_born) +
                FormatUtils.formatDate(person.getBorn_day(),
                        person.getBorn_month(), person.getBorn_year(),
                        getString(R.string.unknown)));

        ((TextView) findViewById(R.id.personAffiliation)).setText(
                getString(R.string.person_affiliation) +
                (isBlank(person.getAffiliation_name()) ?
                        getString(R.string.unknown) : person.getAffiliation_name()));

        // Presence

        final Button personCrunchbaseUrl = (Button) findViewById(R.id.personCrunchbaseUrl);
        personCrunchbaseUrl.setVisibility(isBlank(person.getCrunchbase_url()) ? Button.GONE : Button.VISIBLE);
        if(personCrunchbaseUrl.getVisibility() == Button.VISIBLE) {
            personCrunchbaseUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(person.getCrunchbase_url())));
                }
            });
        }

        final Button personBlogUrl = (Button) findViewById(R.id.personBlogUrl);
        personBlogUrl.setVisibility(isBlank(person.getBlog_url()) ? Button.GONE : Button.VISIBLE);
        if(personBlogUrl.getVisibility() == Button.VISIBLE) {
            personBlogUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(person.getBlog_url())));
                }
            });
        }

        // Determine web presence elements.
        String twitter = person.getTwitter_username();
        String facebook = "";
        String linkedin = "";
        if(person.getWeb_presences() != null) {
            for(final WebPresence presence : person.getWeb_presences()) {
                final String title = presence.getTitle();
                final String url = presence.getExternal_url();
                if(isNotBlank(title) && isNotBlank(url)) {
                    if(containsIgnoreCase(title, "twitter")) {
                        final String[] elem = url.split("/");
                        twitter = elem[elem.length - 1];
                    }
                    else if(containsIgnoreCase(title, "facebook")) {
                        facebook = url;
                    }
                    else if(containsIgnoreCase(title, "linkedin")) {
                        linkedin = url;
                    }
                }
            }
        }

        final String twitterUser = twitter;
        final Button personTwitter = (Button) findViewById(R.id.personTwitter);
        personTwitter.setVisibility(isBlank(twitterUser) ? Button.GONE : Button.VISIBLE);
        if(personTwitter.getVisibility() == Button.VISIBLE) {
            personTwitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://twitter.com/" + twitterUser)));
                }
            });
        }

        final String facebookUrl = facebook;
        final Button personFacebook = (Button) findViewById(R.id.personFacebook);
        personFacebook.setVisibility(isBlank(facebookUrl) ? Button.GONE : Button.VISIBLE);
        if(personFacebook.getVisibility() == Button.VISIBLE) {
            personFacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)));
                }
            });
        }

        final String linkedinUrl = linkedin;
        final Button personLinkedIn = (Button) findViewById(R.id.personLinkedIn);
        personLinkedIn.setVisibility(isBlank(linkedinUrl) ? Button.GONE : Button.VISIBLE);
        if(personLinkedIn.getVisibility() == Button.VISIBLE) {
            personLinkedIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(linkedinUrl)));
                }
            });
        }

        // Overview

        final TextView personOverview = (TextView) findViewById(R.id.personOverview);
        if(isBlank(person.getOverview())) {
            personOverview.setVisibility(TextView.GONE);
            findViewById(R.id.personOverviewLabel).setVisibility(TextView.GONE);
        }
        else {
            personOverview.setText(FormatUtils.trim(FormatUtils.htmlLinkify(person.getOverview(), this)));
            personOverview.setMovementMethod(LinkMovementMethod.getInstance());
            personOverview.setVisibility(TextView.VISIBLE);
            findViewById(R.id.personOverviewLabel).setVisibility(TextView.VISIBLE);
        }

        // Companies

        final LinearLayout companiesHolder = (LinearLayout) findViewById(R.id.personCompaniesHolder);
        companiesHolder.removeAllViews();
        for(final RelationshipToFirm relationship : person.getRelationships()) {
            final FirmShort firm = relationship.getFirm();
            final View companyItem = layoutInflater.inflate(R.layout.company_item, null);
            ((TextView) companyItem.findViewById(R.id.companyName)).setText(
                    firm.getName());
            ((TextView) companyItem.findViewById(R.id.personTitle)).setText(
                    relationship.getTitle());
            if(firm.getImage() != null) {
                client.loadImage(firm.getImage().getSmallAsset(),
                        (ImageView) companyItem.findViewById(R.id.companyImage));
            }
            companyItem.setOnClickListener(new ActivityLauncherListener(
                    this, firm.getType_of_entity(), firm.getPermalink()));

            companiesHolder.addView(companyItem);

            // Limit the size of the key people list.
            if(companiesHolder.getChildCount() >= 5) {
                break;
            }
        }
        companiesHolder.setVisibility(companiesHolder.getChildCount() > 0 ? LinearLayout.VISIBLE : LinearLayout.GONE);
        findViewById(R.id.personCompaniesLabel).setVisibility(
                companiesHolder.getChildCount() > 0 ? TextView.VISIBLE : TextView.GONE);

        findViewById(R.id.personContents).setVisibility(View.VISIBLE);
        onRefreshCompleted();
        RefreshMessage.hideRefreshFailed(this);
    }

}