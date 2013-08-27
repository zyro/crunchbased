package com.github.zyro.crunchbase.service;

import android.content.Context;
import android.widget.ImageView;
import com.github.zyro.crunchbase.R;
import com.github.zyro.crunchbase.util.HomeData;
import com.google.common.io.CharStreams;
import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;
import com.googlecode.androidannotations.api.Scope;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/** Functions to retrieve data from main site. */
@EBean(scope = Scope.Singleton)
@NoArgsConstructor
public class WebClient {

    /** Context used to look up strings. */
    @RootContext
    protected Context context;

    /** Access to application preferences. */
    @Pref
    protected Preferences_ preferences;

    /** Initialise with correct settings. */
    @AfterInject
    protected void initState() {
        final ImageLoaderConfiguration config =
                new ImageLoaderConfiguration.Builder(context)
                        .discCacheSize(20 * 1024 * 1024)
                        .build();
        ImageLoader.getInstance().init(config);
    }

    /** Get primary home page data elements. */
    public HomeData getHomeData() throws ClientException {
        try {
            // Open a connection and pull data.
            final HttpURLConnection connection = (HttpURLConnection) new URL(
                    "http://www.crunchbase.com/").openConnection();
            final InputStreamReader in = new InputStreamReader(
                    new BufferedInputStream(connection.getInputStream()));
            final String response = CharStreams.toString(in);
            in.close();
            connection.disconnect();

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
                item.setName(elem.getElementsByTag("a").text().trim().replace(
                        "\\u7684CrunchBase\\u7b80\\u4ecb", ""));

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

            return data;
        }
        catch(final IOException e) {
            throw new ClientException(e);
        }
    }

    /**
     * Trigger an image fetching process with a required asset and target view
     * it is intended to be displayed in. Disabling image loading through the
     * preferences effectively makes this a no-op.
     */
    public void loadImage(final String asset, final ImageView view) {
        if(!preferences.loadImages().get()) {
            return;
        }

        final DisplayImageOptions options =
                new DisplayImageOptions.Builder()
                        .showStubImage(R.drawable.content_picture)
                        .showImageForEmptyUri(R.drawable.content_picture)
                        .showImageOnFail(R.drawable.content_picture)
                        .displayer(new FadeInBitmapDisplayer(250))
                        .cacheOnDisc(preferences.cacheImages().get())
                        .build();
        ImageLoader.getInstance().displayImage(
                "http://www.crunchbase.com/" + asset, view, options);
    }

}