package com.github.zyro.crunchbase.service;

import android.content.Context;
import android.widget.ImageView;
import com.github.zyro.crunchbase.R;
import com.github.zyro.crunchbase.entity.Company;
import com.github.zyro.crunchbase.entity.FinancialOrganization;
import com.github.zyro.crunchbase.entity.Person;
import com.github.zyro.crunchbase.entity.Product;
import com.github.zyro.crunchbase.entity.Search;
import com.github.zyro.crunchbase.util.HomeData;
import com.google.gson.reflect.TypeToken;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.api.Scope;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static java.net.URLEncoder.encode;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@EBean(scope = Scope.Singleton)
public class CrunchbaseClient {

    /** Application root context. */
    @RootContext
    protected Context context;

    /** Crunchbase API key. */
    protected static final String API_KEY = "api_key=9za7pzrvfch6quf3vgwp2dja";

    /**
     * Get primary home page data to be later parsed into a HomeData instance.
     * Page scraping isn't pretty but the API does not provide any lookup
     * functions that would constitute a decent application landing space.
     *
     * @param callback The callback to pass the data back to, or to notify of a
     *                 failure during the loading or mapping process.
     */
    public void getHomeData(final FutureCallback<String> callback) {
        Ion.with(context, "http://www.crunchbase.com/").asString()
                .setCallback(callback);
    }

    /**
     * Trigger an image fetching process with a required asset and target view
     * it is intended to be displayed in. Disabling image loading through the
     * preferences effectively makes this a no-op.
     *
     * @param asset The asset sub-URL to load.
     * @param view The ImageView to display the loaded image in.
     */
    public void loadImage(final String asset, final ImageView view) {
        //if(!preferences.loadImages().get()) {
        //    return;
        //}

        Ion.with(view).error(R.drawable.no_image_placeholder)
                .load("http://www.crunchbase.com/" + asset);
    }

    /**
     * Perform a company API call and return the data to the given callback in
     * the form of a Company entity instance.
     *
     * @param permalink The permalink identifier of the company.
     * @param callback The callback to pass the data back to, or to notify of a
     *                 failure during the loading or mapping process.
     */
    public void getCompany(final String permalink,
                           final FutureCallback<Company> callback) {
        try {
            Ion.with(context, "http://api.crunchbase.com/v/1/company/" +
                    encode(permalink.toLowerCase(), "UTF-8") + ".js?" + API_KEY)
                .as(new TypeToken<Company>() {})
                .setCallback(callback);
        }
        catch(final UnsupportedEncodingException e) {
            callback.onCompleted(e, null);
        }
    }

    /**
     * Perform a person API call and return the data to the given callback in
     * the form of a Person entity instance.
     *
     * @param permalink The permalink identifier of the person.
     * @param callback The callback to pass the data back to, or to notify of a
     *                 failure during the loading or mapping process.
     */
    public void getPerson(final String permalink,
                          final FutureCallback<Person> callback) {
        try {
            Ion.with(context, "http://api.crunchbase.com/v/1/person/" +
                    encode(permalink.toLowerCase(), "UTF-8") + ".js?" + API_KEY)
                .as(new TypeToken<Person>(){})
                .setCallback(callback);
        }
        catch(final UnsupportedEncodingException e) {
            callback.onCompleted(e, null);
        }
    }

    /**
     * Perform a financial organization API call and return the data to the
     * given callback in the form of a FinancialOrganization entity instance.
     *
     * @param permalink The permalink identifier of the financial organization.
     * @param callback The callback to pass the data back to, or to notify of a
     *                 failure during the loading or mapping process.
     */
    public void getFinancialOrganization(final String permalink,
                         final FutureCallback<FinancialOrganization> callback) {
        try {
            Ion.with(context,
                    "http://api.crunchbase.com/v/1/financial-organization/" +
                    encode(permalink.toLowerCase(), "UTF-8") + ".js?" + API_KEY)
                    .as(new TypeToken<FinancialOrganization>(){})
                    .setCallback(callback);
        }
        catch(final UnsupportedEncodingException e) {
            callback.onCompleted(e, null);
        }
    }

    /**
     * Perform a product API call and return the data to the given callback in
     * the form of a Product entity instance.
     *
     * @param permalink The permalink identifier of the product.
     * @param callback The callback to pass the data back to, or to notify of a
     *                 failure during the loading or mapping process.
     */
    public void getProduct(final String permalink,
                           final FutureCallback<Product> callback) {
        try {
            Ion.with(context,
                    "http://api.crunchbase.com/v/1/product/" +
                    encode(permalink.toLowerCase(), "UTF-8") + ".js?" + API_KEY)
                    .as(new TypeToken<Product>(){})
                    .setCallback(callback);
        }
        catch(final UnsupportedEncodingException e) {
            callback.onCompleted(e, null);
        }
    }

    public void getServiceProvider(final String permalink,
                                   final FutureCallback<Object> callback) {
        try {
            Ion.with(context,
                    "http://api.crunchbase.com/v/1/service-provider/" +
                    encode(permalink.toLowerCase(), "UTF-8") + ".js?" + API_KEY)
                    .as(new TypeToken<Object>(){})
                    .setCallback(callback);
        }
        catch(final UnsupportedEncodingException e) {
            callback.onCompleted(e, null);
        }
    }

    /**
     * Perform a search API call with the given parameters and optional filters
     * and return the result to the callback in the form of a Search entity
     * instance.
     *
     * @param query The query string to search for.
     * @param page The result page number to request. May fail if higher than
     *             the number of available pages - use with care.
     * @param entity The entity type to limit the search to.
     * @param field The field within the entity to limit the search to.
     * @param callback The callback to pass the data back to, or to notify of a
     *                 failure during the loading or mapping process.
     */
    public void getSearchResults(final String query, final int page,
                                 final String entity, final String field,
                                 final FutureCallback<Search> callback) {
        try {
            String url = "http://api.crunchbase.com/v/1/search.js?query=" +
                    encode(query.toLowerCase(), "UTF-8");
            if(page > 1) {
                url += "&page=" + page;
            }
            if(isNotBlank(entity)) {
                url += "&entity=" + encode(entity.toLowerCase(), "UTF-8");
            }
            if(isNotBlank(field)) {
                url += "&field=" + encode(field.toLowerCase(), "UTF-8");
            }

            Ion.with(context, url + "&" + API_KEY)
                    .as(new TypeToken<Search>(){})
                    .setCallback(callback);
        }
        catch(final UnsupportedEncodingException e) {
            callback.onCompleted(e, null);
        }
    }

}