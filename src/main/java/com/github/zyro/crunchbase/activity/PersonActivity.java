package com.github.zyro.crunchbase.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.github.zyro.crunchbase.R;
import com.github.zyro.crunchbase.entity.*;
import com.github.zyro.crunchbase.service.ClientException;
import com.github.zyro.crunchbase.util.FormatUtils;
import com.github.zyro.crunchbase.util.SwipeBackListener;
import com.googlecode.androidannotations.annotations.*;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.*;

@EActivity(R.layout.person)
public class PersonActivity extends BaseActivity {

    /** The permalink of the person being displayed. */
    protected String permalink;

    @ViewById(R.id.personEmpty)
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
        findViewById(R.id.personContents).setOnTouchListener(
                new SwipeBackListener(this));
        refreshPersonDetails();
    }

    @Background
    public void refreshPersonDetails() {
        refreshPersonDetailsStarted();
        try {
            final Person person = apiClient.getPerson(permalink);
            refreshPersonDetailsDone(person);
        }
        catch(final ClientException e) {
            refreshPersonDetailsFailed();
        }
    }

    @UiThread
    public void refreshPersonDetailsStarted() {
        invalidateOptionsMenu();
        menu.findItem(R.id.refreshButton).setVisible(false);
        setProgressBarIndeterminateVisibility(true);

        empty.setText(R.string.refreshing);
    }

    @UiThread
    public void refreshPersonDetailsDone(final Person person) {
        // Header

        if(person.getImage() != null) {
            webClient.loadImage(person.getImage().getLargeAsset(),
                    (ImageView) findViewById(R.id.personImage));
        }

        ((TextView) findViewById(R.id.personName)).setText(
                person.getFirst_name() + " " + person.getLast_name());

        ((TextView) findViewById(R.id.personBorn)).setText(
                getString(R.string.person_born) +
                FormatUtils.extractDateBorn(person, getString(R.string.unknown)));

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
                webClient.loadImage(firm.getImage().getSmallAsset(),
                        (ImageView) companyItem.findViewById(R.id.companyImage));
            }
            if(firm.getType_of_entity().equals("company")) {
                companyItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        final Intent intent = new Intent(PersonActivity.this, CompanyActivity_.class);
                        intent.setData(Uri.parse("http://www.crunchbase.com/company/" +
                                firm.getPermalink()));
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
                    }
                });
            }
            else if(firm.getType_of_entity().equals("financial_org")) {
                companyItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        Toast.makeText(PersonActivity.this, relationship.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            companiesHolder.addView(companyItem);

            // Limit the size of the key people list.
            if(companiesHolder.getChildCount() >= 5) {
                break;
            }
        }
        companiesHolder.setVisibility(companiesHolder.getChildCount() > 0 ? LinearLayout.VISIBLE : LinearLayout.GONE);
        findViewById(R.id.personCompaniesLabel).setVisibility(
                companiesHolder.getChildCount() > 0 ? TextView.VISIBLE : TextView.GONE);

        setProgressBarIndeterminateVisibility(false);
        invalidateOptionsMenu();

        empty.setVisibility(View.GONE);
        findViewById(R.id.personContents).setVisibility(View.VISIBLE);
    }

    @UiThread
    public void refreshPersonDetailsFailed() {
        invalidateOptionsMenu();
        menu.findItem(R.id.refreshButton).setVisible(false);
        setProgressBarIndeterminateVisibility(true);

        empty.setText(R.string.no_items);
    }

    @OptionsItem(R.id.refreshButton)
    public void refreshButton() {
        refreshPersonDetails();
    }

    @OptionsItem(android.R.id.home)
    public void homeButton() {
        super.onBackPressed();
    }

}