package com.github.zyro.crunchbase.util;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

/** Entity used to store home activity data. */
public class HomeData {

    private List<Trending> trending;
    private List<Recent> recent;
    private List<Biggest> biggest;

    public HomeData(final String rawData, final String unknownField) {
        // Parse HTML response.
        final Document document = Jsoup.parse(rawData);

        // Extract Trending items.
        trending = new ArrayList<Trending>();
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
                item.setName(unknownField);
            }

            trending.add(item);
        }

        // Extract Recent items.
        recent = new ArrayList<HomeData.Recent>();
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
                            unknownField);
            item.setFunds(elem.getElementsByClass("horizbar").text().trim());

            recent.add(item);
        }

        // Extract Biggest (Top Funded This Year) items.
        biggest = new ArrayList<HomeData.Biggest>();
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
                            unknownField);
            item.setFunds(elem.getElementsByClass("horizbar").text().trim());

            biggest.add(item);
        }
    }

    public List<Trending> getTrending() {
        return trending;
    }

    public void setTrending(List<Trending> trending) {
        this.trending = trending;
    }

    public List<Recent> getRecent() {
        return recent;
    }

    public void setRecent(List<Recent> recent) {
        this.recent = recent;
    }

    public List<Biggest> getBiggest() {
        return biggest;
    }

    public void setBiggest(List<Biggest> biggest) {
        this.biggest = biggest;
    }

    /** Trending screen information entity. */
    public static class Trending {

        private String name;
        private String namespace;
        private String permalink;
        private Integer points;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNamespace() {
            return namespace;
        }

        public void setNamespace(String namespace) {
            this.namespace = namespace;
        }

        public String getPermalink() {
            return permalink;
        }

        public void setPermalink(String permalink) {
            this.permalink = permalink;
        }

        public Integer getPoints() {
            return points;
        }

        public void setPoints(Integer points) {
            this.points = points;
        }

        @Override
        public String toString() {
            return "Trending{" +
                    "name='" + name + '\'' +
                    ", namespace='" + namespace + '\'' +
                    ", permalink='" + permalink + '\'' +
                    ", points=" + points +
                    '}';
        }

    }

    /** Recent screen information entity. */
    public static class Recent {

        private String name;
        private String permalink;
        private String subtext;
        private String funds;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPermalink() {
            return permalink;
        }

        public void setPermalink(String permalink) {
            this.permalink = permalink;
        }

        public String getSubtext() {
            return subtext;
        }

        public void setSubtext(String subtext) {
            this.subtext = subtext;
        }

        public String getFunds() {
            return funds;
        }

        public void setFunds(String funds) {
            this.funds = funds;
        }

        @Override
        public String toString() {
            return "Recent{" +
                    "name='" + name + '\'' +
                    ", permalink='" + permalink + '\'' +
                    ", subtext='" + subtext + '\'' +
                    ", funds='" + funds + '\'' +
                    '}';
        }

    }

    /** Biggest to date screen information entity. */
    public static class Biggest {

        private String name;
        private String permalink;
        private String subtext;
        private String funds;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPermalink() {
            return permalink;
        }

        public void setPermalink(String permalink) {
            this.permalink = permalink;
        }

        public String getSubtext() {
            return subtext;
        }

        public void setSubtext(String subtext) {
            this.subtext = subtext;
        }

        public String getFunds() {
            return funds;
        }

        public void setFunds(String funds) {
            this.funds = funds;
        }

        @Override
        public String toString() {
            return "Biggest{" +
                    "name='" + name + '\'' +
                    ", permalink='" + permalink + '\'' +
                    ", subtext='" + subtext + '\'' +
                    ", funds='" + funds + '\'' +
                    '}';
        }

    }

}