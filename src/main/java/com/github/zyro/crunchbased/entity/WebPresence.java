package com.github.zyro.crunchbased.entity;

public class WebPresence {

    private String external_url;
    private String title;

    public String getExternal_url() {
        return external_url;
    }

    public void setExternal_url(String external_url) {
        this.external_url = external_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "WebPresence{" +
                "external_url='" + external_url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

}