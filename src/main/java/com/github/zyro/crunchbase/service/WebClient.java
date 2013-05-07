package com.github.zyro.crunchbase.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import com.github.zyro.crunchbase.entity.Image;
import com.github.zyro.crunchbase.util.RecentItem;
import com.github.zyro.crunchbase.util.TrendingItem;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.api.Scope;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@EBean(scope = Scope.Singleton)
public class WebClient {

    private HttpHost target;
    private DefaultHttpClient client;

    public WebClient() {
        target = new HttpHost("www.crunchbase.com", 80, "http");
        final HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 10000);
        HttpConnectionParams.setSoTimeout(params, 10000);
        client = new DefaultHttpClient(params);
    }

    public String getPageData() {
        try {
            final HttpGet request = new HttpGet("/");
            final ResponseHandler<String> handler = new BasicResponseHandler();
            return client.execute(target, request, handler);
        }
        catch(IOException e) {} // TODO: proper handling
        return null;
    }

    public List<TrendingItem> getTrending(final String data) {
        final List<TrendingItem> trendingItems = new ArrayList<TrendingItem>();

        final Document document = Jsoup.parse(data);
        for(final Element element : document
                .getElementById("trending-now").getElementsByTag("li")) {
            final TrendingItem trendingItem = new TrendingItem();

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

    public List<RecentItem> getRecent(final String data) {
        final List<RecentItem> recentItems = new ArrayList<RecentItem>();

        final Document document = Jsoup.parse(data);
        for(final Element element : document
                .getElementById("content-newlyfunded").getElementsByTag("li")) {
            final RecentItem recentItem = new RecentItem();

            recentItem.setPermalink(element.getElementsByTag("a").attr("href")
                    .replace("/company/", ""));
            recentItem.setName(element.getElementsByTag("a").text().trim());
            recentItem.setSubtext(element.getElementsByTag("strong").size() > 1 ?
                    element.getElementsByTag("strong").last().text().trim() :
                    element.getElementsByTag("span").last().text().trim());
            recentItem.setFunds(element.getElementsByClass("horizbar").text().trim());

            recentItems.add(recentItem);
        }

        return recentItems;
    }

    public Bitmap getLargeImage(final Image image) {
        try {
            final HttpGet request = new HttpGet(image.getLargeAsset());
            final HttpResponse response = client.execute(target, request);

            final InputStream inputStream = new BufferedHttpEntity(
                    response.getEntity()).getContent();
            final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            return bitmap;
        }
        catch(IOException e) { throw new RuntimeException(e); } // TODO: proper handling
    }

}