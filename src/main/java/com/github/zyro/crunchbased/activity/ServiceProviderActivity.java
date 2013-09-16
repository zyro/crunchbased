package com.github.zyro.crunchbased.activity;

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

import com.github.zyro.crunchbased.R;
import com.github.zyro.crunchbased.entity.FirmShort;
import com.github.zyro.crunchbased.entity.RelationshipToFirm;
import com.github.zyro.crunchbased.entity.ServiceProvider;
import com.github.zyro.crunchbased.util.ActivityLauncherListener;
import com.github.zyro.crunchbased.util.FormatUtils;
import com.github.zyro.crunchbased.util.RefreshMessage;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.SystemService;
import com.googlecode.androidannotations.annotations.UiThread;

import java.util.List;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;

import static org.apache.commons.lang3.StringUtils.isBlank;

@EActivity(R.layout.service_provider)
public class ServiceProviderActivity extends BaseActivity<ServiceProvider> {

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

        ((PullToRefreshLayout) findViewById(R.id.serviceProviderPtr))
                .setPullToRefreshAttacher(attacher, this);

        //findViewById(R.id.companyContents).setOnTouchListener(
        //        new SwipeBackListener(this));
        refreshButton();
    }

    @Override
    @Background
    public void refresh() {
        refreshStarted();
        client.getServiceProvider(permalink, this);
    }

    @UiThread
    public void refreshDone(final ServiceProvider serviceProvider) {
        // Header

        if(serviceProvider.getImage() != null) {
            client.loadImage(serviceProvider.getImage().getLargeAsset(),
                    (ImageView) findViewById(R.id.serviceProviderImage));
        }

        ((TextView) findViewById(R.id.serviceProviderName)).setText(
                serviceProvider.getName());

        final TextView serviceProviderUrl =
                (TextView) findViewById(R.id.serviceProviderUrl);
        serviceProviderUrl.setText(serviceProvider.getHomepage_url());
        serviceProviderUrl.setMovementMethod(LinkMovementMethod.getInstance());
        Linkify.addLinks(serviceProviderUrl, Linkify.WEB_URLS);

        // Presence

        final Button serviceProviderCrunchbaseUrl =
                (Button) findViewById(R.id.serviceProviderCrunchbaseUrl);
        serviceProviderCrunchbaseUrl.setVisibility(
                isBlank(serviceProvider.getCrunchbase_url()) ? Button.GONE : Button.VISIBLE);
        if(serviceProviderCrunchbaseUrl.getVisibility() == Button.VISIBLE) {
            serviceProviderCrunchbaseUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(serviceProvider.getCrunchbase_url())));
                }
            });
        }

        final Button serviceProviderEmail = (Button) findViewById(R.id.serviceProviderEmail);
        serviceProviderEmail.setVisibility(
                isBlank(serviceProvider.getEmail_address()) ? Button.GONE : Button.VISIBLE);
        if(serviceProviderEmail.getVisibility() == Button.VISIBLE) {
            serviceProviderEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    startActivity(new Intent(Intent.ACTION_SENDTO,
                            Uri.fromParts("mailto", serviceProvider.getEmail_address(), null)));
                }
            });
        }

        // Overview

        final TextView serviceProviderOverview =
                (TextView) findViewById(R.id.serviceProviderOverview);
        if(isBlank(serviceProvider.getOverview())) {
            serviceProviderOverview.setVisibility(TextView.GONE);
            findViewById(R.id.serviceProviderOverviewLabel).setVisibility(TextView.GONE);
        }
        else {
            serviceProviderOverview.setText(
                    FormatUtils.trim(FormatUtils.htmlLinkify(serviceProvider.getOverview(), this)));
            serviceProviderOverview.setMovementMethod(LinkMovementMethod.getInstance());
            serviceProviderOverview.setVisibility(TextView.VISIBLE);
            findViewById(R.id.serviceProviderOverviewLabel).setVisibility(TextView.VISIBLE);
        }

        // Providerships

        final LinearLayout providershipsHolder =
                (LinearLayout) findViewById(R.id.serviceProviderProvidershipsHolder);
        providershipsHolder.removeAllViews();
        for(final RelationshipToFirm relationship : serviceProvider.getProviderships()) {
            if(relationship.getIs_past() != null && relationship.getIs_past()) {
                continue;
            }

            final FirmShort firm = relationship.getFirm();
            final View firmItem = layoutInflater.inflate(R.layout.providership_item, null);

            ((TextView) firmItem.findViewById(R.id.companyName)).setText(firm.getName());
            ((TextView) firmItem.findViewById(R.id.providershipCapacity)).setText(
                    isBlank(relationship.getTitle()) ? getString(R.string.unknown) : relationship.getTitle());
            if(firm.getImage() != null) {
                client.loadImage(firm.getImage().getSmallAsset(),
                        (ImageView) firmItem.findViewById(R.id.companyImage));
            }
            firmItem.setOnClickListener(new ActivityLauncherListener(
                    this, firm.getType_of_entity(), firm.getPermalink()));

            providershipsHolder.addView(firmItem);

            // Limit the size of the key providerships list.
            if(providershipsHolder.getChildCount() >= 10) {
                break;
            }
        }
        providershipsHolder.setVisibility(
                providershipsHolder.getChildCount() > 0 ? LinearLayout.VISIBLE : LinearLayout.GONE);
        findViewById(R.id.serviceProviderProvidershipsLabel).setVisibility(
                providershipsHolder.getChildCount() > 0 ? TextView.VISIBLE : TextView.GONE);

        findViewById(R.id.serviceProviderContents).setVisibility(View.VISIBLE);
        onRefreshCompleted();
        RefreshMessage.hideRefreshFailed(this);
    }

}