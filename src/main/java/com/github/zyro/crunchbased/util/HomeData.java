package com.github.zyro.crunchbased.util;

import java.util.List;

/** Entity used to store home activity data. */
public class HomeData {

    private List<Trending> trending;
    private List<Recent> recent;
    private List<Biggest> biggest;

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