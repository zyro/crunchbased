package com.github.zyro.crunchbase.service;

import android.content.Context;
import android.widget.ImageView;
import com.github.zyro.crunchbase.R;
import com.github.zyro.crunchbase.entity.Company;
import com.github.zyro.crunchbase.entity.FinancialOrganization;
import com.github.zyro.crunchbase.entity.Person;
import com.github.zyro.crunchbase.entity.Product;
import com.github.zyro.crunchbase.entity.Search;
import com.github.zyro.crunchbase.entity.ServiceProvider;
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
     * Get primary home page data as a HomeData instance. Page scraping isn't
     * pretty but the API does not provide any lookup functions that would
     * constitute a decent application landing space.
     *
     * @param callback The callback to pass the data back to, or to notify of a
     *                 failure during the loading or mapping process.
     */
    public void getHomeData(final FutureCallback<HomeData> callback) {
        try {
            // Open a connection and pull data. This is a blocking operation.
            final String response = Ion.with(context,
                    "http://www.crunchbase.com/").asString().get();

            final HomeData data = new HomeData();

            // Parse HTML response.
            final Document document = Jsoup.parse(response);

            // Extract Trending items.
            final List<HomeData.Trending> trendingItems =
                    new ArrayList<HomeData.Trending>();
            for(final Element elem : document.getElementById(
                    "trending-now").getElementsByTag("li")) {
                final HomeData.Trending item = new HomeData.Trending();

                final String[] link = elem.getElementsByTag("a").first()
                        .attr("href").replace("http://www.crunchbase.com/", "")
                        .split("/");
                item.setNamespace(link[link.length - 2]);
                item.setPermalink(link[link.length - 1].replaceAll("[?].*", ""));
                item.setPoints(elem.getElementsByTag("img").size());
                item.setName(StringEscapeUtils.unescapeJava(
                        elem.getElementsByTag("a").text().trim()));
                if(isBlank(item.getName())) {
                    item.setName(context.getString(R.string.unknown));
                }

                trendingItems.add(item);
            }
            data.setTrending(trendingItems);

            // Extract Recent items.
            final List<HomeData.Recent> recentItems =
                    new ArrayList<HomeData.Recent>();
            for(final Element elem : document.getElementById(
                    "content-newlyfunded").getElementsByTag("li")) {
                final HomeData.Recent item = new HomeData.Recent();

                item.setPermalink(elem.getElementsByTag("a").attr("href")
                        .replace("/company/", ""));
                item.setName(elem.getElementsByTag("a").text().trim());
                item.setSubtext(elem.getElementsByTag("strong").size() > 1 ?
                        elem.getElementsByTag("strong").last().text().trim() :
                        elem.getElementsByTag("span").size() > 0 ?
                                elem.getElementsByTag("span").last().text().trim() :
                                context.getString(R.string.unknown));
                item.setFunds(elem.getElementsByClass("horizbar").text().trim());

                recentItems.add(item);
            }
            data.setRecent(recentItems);

            // Extract Biggest (Top Funded This Year) items.
            final List<HomeData.Biggest> biggestItems =
                    new ArrayList<HomeData.Biggest>();
            for(final Element elem : document.getElementById(
                    "content-biggestfunded").getElementsByTag("li")) {
                final HomeData.Biggest item = new HomeData.Biggest();

                item.setPermalink(elem.getElementsByTag("a").attr("href")
                        .replace("/company/", ""));
                item.setName(elem.getElementsByTag("a").text().trim());
                item.setSubtext(elem.getElementsByTag("strong").size() > 1 ?
                        elem.getElementsByTag("strong").last().text().trim() :
                        elem.getElementsByTag("span").size() > 0 ?
                                elem.getElementsByTag("span").last().text().trim() :
                                context.getString(R.string.unknown));
                item.setFunds(elem.getElementsByClass("horizbar").text().trim());

                biggestItems.add(item);
            }
            data.setBiggest(biggestItems);

            callback.onCompleted(null, data);
        }
        catch(final Exception e) {
            callback.onCompleted(e, null);
        }
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

    /**
     * Perform a service provider API call and return the data to the given
     * callback in the form of a Product entity instance.
     *
     * @param permalink The permalink identifier of the service provider.
     * @param callback The callback to pass the data back to, or to notify of a
     *                 failure during the loading or mapping process.
     */
    public void getServiceProvider(final String permalink,
                               final FutureCallback<ServiceProvider> callback) {
        try {
            Ion.with(context,
                    "http://api.crunchbase.com/v/1/service-provider/" +
                    encode(permalink.toLowerCase(), "UTF-8") + ".js?" + API_KEY)
                    .as(new TypeToken<ServiceProvider>(){})
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