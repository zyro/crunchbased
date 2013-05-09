package com.github.zyro.crunchbase.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.github.zyro.crunchbase.entity.Image;
import com.github.zyro.crunchbase.util.HomeRecentItem;
import com.github.zyro.crunchbase.util.HomeTrendingItem;
import com.google.common.io.CharStreams;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.api.Scope;
import lombok.NoArgsConstructor;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@EBean(scope = Scope.Singleton)
@NoArgsConstructor
public class WebClient {

    public List<HomeTrendingItem> getTrending() {
        final List<HomeTrendingItem> trendingItems = new ArrayList<HomeTrendingItem>();

        final String data = getPageData();

        final Document document = Jsoup.parse(data);
        for(final Element element : document
                .getElementById("trending-now").getElementsByTag("li")) {
            final HomeTrendingItem trendingItem = new HomeTrendingItem();

            final String[] link = element.getElementsByTag("a").attr("href")
                    .replace("http://www.crunchbase.com/", "").split("/");
            trendingItem.setNamespace(link[0]);
            trendingItem.setPermalink(link[1].replaceAll("[?].*", ""));
            trendingItem.setPoints(element.getElementsByTag("img").size());
            trendingItem.setName(element.getElementsByTag("a").text().trim());

            trendingItems.add(trendingItem);
        }

        return trendingItems;
    }

    public List<HomeRecentItem> getRecent() {
        final List<HomeRecentItem> recentItems = new ArrayList<HomeRecentItem>();

        final String data = getPageData();

        final Document document = Jsoup.parse(data);
        for(final Element element : document
                .getElementById("content-newlyfunded").getElementsByTag("li")) {
            final HomeRecentItem recentItem = new HomeRecentItem();

            recentItem.setPermalink(element.getElementsByTag("a").attr("href")
                    .replace("/company/", ""));
            recentItem.setName(element.getElementsByTag("a").text().trim());
            recentItem.setSubtext(element.getElementsByTag("strong").size() > 1 ?
                    element.getElementsByTag("strong").last().text().trim() :
                    element.getElementsByTag("span").size() > 0 ?
                            element.getElementsByTag("span").last().text().trim() :
                            "<< unknown, shite data or format >>");
            recentItem.setFunds(element.getElementsByClass("horizbar").text().trim());

            recentItems.add(recentItem);
        }

        return recentItems;
    }

    public Bitmap getLargeImage(final Image image) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL("http://www.crunchbase.com/" + image.getLargeAsset()).openConnection();
            InputStream in = new BufferedInputStream(conn.getInputStream());
            final Bitmap bitmap = BitmapFactory.decodeStream(in);
            in.close();
            conn.disconnect();
            return bitmap;
        }
        catch(IOException e) { throw new RuntimeException(e); } // TODO: proper handling
    }

    protected String getPageData() {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL("http://www.crunchbase.com/").openConnection();
            InputStreamReader in = new InputStreamReader(new BufferedInputStream(conn.getInputStream()));
            String response = CharStreams.toString(in);
            in.close();
            conn.disconnect();
            return response;
        }
        catch(IOException e) {} // TODO: proper handling
        return null;
    }

}