package com.github.zyro.crunchbase.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.github.zyro.crunchbase.entity.Image;
import com.github.zyro.crunchbase.util.HomeData;
import com.google.common.io.CharStreams;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.api.Scope;
import lombok.NoArgsConstructor;
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

    public HomeData getHomeData() {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(
                    "http://www.crunchbase.com/").openConnection();
            InputStreamReader in = new InputStreamReader(new BufferedInputStream(conn.getInputStream()));
            String response = CharStreams.toString(in);
            in.close();
            conn.disconnect();

            final HomeData data = new HomeData();

            final Document document = Jsoup.parse(response);

            final List<HomeData.Trending> trendingItems =
                    new ArrayList<HomeData.Trending>();
            for(final Element element : document
                    .getElementById("trending-now").getElementsByTag("li")) {
                final HomeData.Trending trendingItem = new HomeData.Trending();

                final String[] link = element.getElementsByTag("a").attr("href")
                        .replace("http://www.crunchbase.com/", "").split("/");
                trendingItem.setNamespace(link[0]);
                trendingItem.setPermalink(link[1].replaceAll("[?].*", ""));
                trendingItem.setPoints(element.getElementsByTag("img").size());
                trendingItem.setName(element.getElementsByTag("a").text().trim());

                trendingItems.add(trendingItem);
            }
            data.setTrending(trendingItems);

            final List<HomeData.Recent> recentItems =
                    new ArrayList<HomeData.Recent>();
            for(final Element element : document
                    .getElementById("content-newlyfunded").getElementsByTag("li")) {
                final HomeData.Recent recentItem = new HomeData.Recent();

                recentItem.setPermalink(element.getElementsByTag("a").attr("href")
                        .replace("/company/", ""));
                recentItem.setName(element.getElementsByTag("a").text().trim());
                recentItem.setSubtext(element.getElementsByTag("strong").size() > 1 ?
                        element.getElementsByTag("strong").last().text().trim() :
                        element.getElementsByTag("span").size() > 0 ?
                                element.getElementsByTag("span").last().text().trim() :
                                "<<< unknown >>>");
                recentItem.setFunds(element.getElementsByClass("horizbar").text().trim());

                recentItems.add(recentItem);
            }
            data.setRecent(recentItems);

            final List<HomeData.Biggest> biggestItems =
                    new ArrayList<HomeData.Biggest>();
            for(final Element element : document
                    .getElementById("content-biggestfunded").getElementsByTag("li")) {
                final HomeData.Biggest biggestItem = new HomeData.Biggest();

                biggestItem.setPermalink(element.getElementsByTag("a").attr("href")
                        .replace("/company/", ""));
                biggestItem.setName(element.getElementsByTag("a").text().trim());
                biggestItem.setSubtext(element.getElementsByTag("strong").size() > 1 ?
                        element.getElementsByTag("strong").last().text().trim() :
                        element.getElementsByTag("span").size() > 0 ?
                                element.getElementsByTag("span").last().text().trim() :
                                "<<< unknown >>>");
                biggestItem.setFunds(element.getElementsByClass("horizbar").text().trim());

                biggestItems.add(biggestItem);
            }
            data.setBiggest(biggestItems);

            return data;
        }
        catch(IOException e) { throw new RuntimeException(e); } // TODO: proper handling
    }

    public Bitmap getLargeImage(final Image image) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(
                    "http://www.crunchbase.com/" + image.getLargeAsset()).openConnection();
            InputStream in = new BufferedInputStream(conn.getInputStream());
            final Bitmap bitmap = BitmapFactory.decodeStream(in);
            in.close();
            conn.disconnect();
            return bitmap;
        }
        catch(IOException e) { throw new RuntimeException(e); } // TODO: proper handling
    }

}