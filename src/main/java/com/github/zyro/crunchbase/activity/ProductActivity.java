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
import android.widget.TextView;

import com.github.zyro.crunchbase.R;
import com.github.zyro.crunchbase.entity.CompanyShort;
import com.github.zyro.crunchbase.entity.Product;
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

import java.util.List;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;

import static org.apache.commons.lang3.StringUtils.isBlank;

@EActivity(R.layout.product)
public class ProductActivity extends BaseActivity implements FutureCallback<Product> {

    @SystemService
    protected LayoutInflater layoutInflater;

    /** The permalink of the product being displayed. */
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

        ((PullToRefreshLayout) findViewById(R.id.productPtr))
                .setPullToRefreshAttacher(attacher, this);

        //findViewById(R.id.companyContents).setOnTouchListener(
        //        new SwipeBackListener(this));
        refreshButton();
    }

    @Override
    @Background
    public void refresh() {
        refreshStarted();
        client.getProduct(permalink, this);
    }

    @UiThread
    public void refreshStarted() {
        RefreshMessage.hideRefreshFailed(this);
    }

    @UiThread
    public void refreshDone(final Product product) {
        // Header

        if(product.getImage() != null) {
            client.loadImage(product.getImage().getLargeAsset(),
                    (ImageView) findViewById(R.id.productImage));
        }

        ((TextView) findViewById(R.id.productName)).setText(product.getName());

        final TextView productUrl = (TextView) findViewById(R.id.productUrl);
        productUrl.setText(product.getHomepage_url());
        productUrl.setMovementMethod(LinkMovementMethod.getInstance());
        Linkify.addLinks(productUrl, Linkify.WEB_URLS);

        ((TextView) findViewById(R.id.productLaunched)).setText(
                getString(R.string.product_launched) +
                FormatUtils.formatDate(product.getLaunched_day(),
                        product.getLaunched_month(), product.getLaunched_year(),
                        getString(R.string.unknown)));

        ((TextView) findViewById(R.id.productStage)).setText(
                getString(R.string.product_stage) +
                        WordUtils.capitalize(product.getStage_code()));

        final TextView productShareUrl = (TextView) findViewById(R.id.productShareUrl);
        productShareUrl.setText(getString(R.string.product_share) +
                (isBlank(product.getInvite_share_url()) ?
                        getString(R.string.unknown) : product.getInvite_share_url()));
        productShareUrl.setMovementMethod(LinkMovementMethod.getInstance());
        Linkify.addLinks(productShareUrl, Linkify.WEB_URLS);

        // Presence

        final Button productCrunchbaseUrl = (Button) findViewById(R.id.productCrunchbaseUrl);
        productCrunchbaseUrl.setVisibility(isBlank(product.getCrunchbase_url()) ? Button.GONE : Button.VISIBLE);
        if(productCrunchbaseUrl.getVisibility() == Button.VISIBLE) {
            productCrunchbaseUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(product.getCrunchbase_url())));
                }
            });
        }

        final Button productBlogUrl = (Button) findViewById(R.id.productBlogUrl);
        productBlogUrl.setVisibility(isBlank(product.getBlog_url()) ? Button.GONE : Button.VISIBLE);
        if(productBlogUrl.getVisibility() == Button.VISIBLE) {
            productBlogUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(product.getBlog_url())));
                }
            });
        }

        final Button productTwitter = (Button) findViewById(R.id.productTwitter);
        productTwitter.setVisibility(isBlank(product.getTwitter_username()) ? Button.GONE : Button.VISIBLE);
        if(productTwitter.getVisibility() == Button.VISIBLE) {
            productTwitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://twitter.com/" + product.getTwitter_username())));
                }
            });
        }

        // Overview

        final TextView productOverview = (TextView) findViewById(R.id.productOverview);
        if(isBlank(product.getOverview())) {
            productOverview.setVisibility(TextView.GONE);
            findViewById(R.id.productOverviewLabel).setVisibility(TextView.GONE);
        }
        else {
            productOverview.setText(FormatUtils.trim(FormatUtils.htmlLinkify(product.getOverview(), this)));
            productOverview.setMovementMethod(LinkMovementMethod.getInstance());
            productOverview.setVisibility(TextView.VISIBLE);
            findViewById(R.id.productOverviewLabel).setVisibility(TextView.VISIBLE);
        }

        // Made By

        if(product.getCompany() != null) {
            final CompanyShort companyShort = product.getCompany();
            if(companyShort.getImage() != null) {
                client.loadImage(companyShort.getImage().getSmallAsset(),
                        (ImageView) findViewById(R.id.productCompanyImage));
            }
            ((TextView) findViewById(R.id.productCompanyName)).setText(
                    companyShort.getName());
            findViewById(R.id.productCompanyHolder).setOnClickListener(
                    new ActivityLauncherListener(this, "company", companyShort.getPermalink()));
            findViewById(R.id.productCompanyLabel).setVisibility(View.VISIBLE);
            findViewById(R.id.productCompanyHolder).setVisibility(View.VISIBLE);
        }
        else {
            findViewById(R.id.productCompanyLabel).setVisibility(View.GONE);
            findViewById(R.id.productCompanyHolder).setVisibility(View.GONE);
        }

        findViewById(R.id.productContents).setVisibility(View.VISIBLE);
        onRefreshCompleted();
        RefreshMessage.hideRefreshFailed(this);
    }

    @UiThread
    public void refreshFailed() {
        onRefreshCompleted();
        RefreshMessage.showRefreshFailed(this);
    }

    @Override
    public void onCompleted(final Exception e, final Product product) {
        if(e == null) {
            refreshDone(product);
        }
        else {
            refreshFailed();
        }
    }

}