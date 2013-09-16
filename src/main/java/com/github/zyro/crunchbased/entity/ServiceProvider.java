package com.github.zyro.crunchbased.entity;

import java.util.List;

public class ServiceProvider {

    private String name;
    private String permalink;
    private String crunchbase_url;
    private String homepage_url;
    private String phone_number;
    private String email_address;
    private String tag_list;
    private String alias_list;
    private String created_at;
    private String updated_at;
    private String overview;
    private Image image;
    private List<Office> offices;
    private List<RelationshipToFirm> providerships;
    private List<ExternalLink> external_links;

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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getTag_list() {
        return tag_list;
    }

    public void setTag_list(String tag_list) {
        this.tag_list = tag_list;
    }

    public String getAlias_list() {
        return alias_list;
    }

    public void setAlias_list(String alias_list) {
        this.alias_list = alias_list;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
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

    public List<RelationshipToFirm> getProviderships() {
        return providerships;
    }

    public void setProviderships(List<RelationshipToFirm> providerships) {
        this.providerships = providerships;
    }

    public List<ExternalLink> getExternal_links() {
        return external_links;
    }

    public void setExternal_links(List<ExternalLink> external_links) {
        this.external_links = external_links;
    }

    @Override
    public String toString() {
        return "ServiceProvider{" +
                "name='" + name + '\'' +
                ", permalink='" + permalink + '\'' +
                ", crunchbase_url='" + crunchbase_url + '\'' +
                ", homepage_url='" + homepage_url + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", email_address='" + email_address + '\'' +
                ", tag_list='" + tag_list + '\'' +
                ", alias_list='" + alias_list + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", overview='" + overview + '\'' +
                ", image=" + image +
                ", offices=" + offices +
                ", providerships=" + providerships +
                ", external_links=" + external_links +
                '}';
    }

}