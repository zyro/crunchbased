package com.github.zyro.crunchbase.service;

import com.github.zyro.crunchbase.util.TrendingItem;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.api.Scope;
import org.apache.http.HttpHost;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
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

    public List<TrendingItem> getTrending() {
        final List<TrendingItem> trendingItems = new ArrayList<TrendingItem>();

        try {
            final HttpGet request = new HttpGet("/");
            final ResponseHandler<String> handler = new BasicResponseHandler();
            final String response = client.execute(target, request, handler);

            final Document document = Jsoup.parse(response);
            for(final Element element : document
                    .getElementById("trending-now").getElementsByTag("li")) {
                final TrendingItem trendingItem = new TrendingItem();

                final String[] link = element.getElementsByTag("a").attr("href")
                        .replace("http://www.crunchbase.com/", "").split("/");
                trendingItem.setNamespace(link[0]);
                trendingItem.setPermalink(link[1]);
                trendingItem.setPoints(element.getElementsByTag("img").size());
                trendingItem.setName(element.getElementsByTag("a").text().trim());

                trendingItems.add(trendingItem);
            }
        }
        catch(IOException e) {} // TODO: proper handling

        return trendingItems;
    }

}