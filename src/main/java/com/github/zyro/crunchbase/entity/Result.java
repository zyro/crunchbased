package com.github.zyro.crunchbase.entity;

import java.util.List;

public class Result {

    private String name;
    private String category_code;
    private String description;
    private String permalink;
    private String crunchbase_url;
    private String homepage_url;
    private String namespace;
    private String overview;
    private Image image;
    private List<Office> offices;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory_code() {
        return category_code;
    }

    public void setCategory_code(String category_code) {
        this.category_code = category_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getCrunchbase_url() {
        return crunchbase_url;
    }

    public void setCrunchbase_url(String crunchbase_url) {
        this.crunchbase_url = crunchbase_url;
    }

    public String getHomepage_url() {
        return homepage_url;
    }

    public void setHomepage_url(String homepage_url) {
        this.homepage_url = homepage_url;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<Office> getOffices() {
        return offices;
    }

    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }

    @Override
    public String toString() {
        return "Result{" +
                "name='" + name + '\'' +
                ", category_code='" + category_code + '\'' +
                ", description='" + description + '\'' +
                ", permalink='" + permalink + '\'' +
                ", crunchbase_url='" + crunchbase_url + '\'' +
                ", homepage_url='" + homepage_url + '\'' +
                ", namespace='" + namespace + '\'' +
                ", overview='" + overview + '\'' +
                ", image=" + image +
                ", offices=" + offices +
                '}';
    }

}