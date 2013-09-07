package com.github.zyro.crunchbase.entity;

import java.util.List;

public class FinancialOrganization {

    private String name;
    private String permalink;
    private String crunchbase_url;
    private String homepage_url;
    private String blog_url;
    private String blog_feed_url;
    private String twitter_username;
    private String phone_number;
    private String description;
    private String email_address;
    private Integer number_of_employees;
    private Integer founded_year;
    private Integer founded_month;
    private Integer founded_day;
    private String tag_list;
    private String alias_list;
    private String created_at;
    private String updated_at;
    private String overview;
    private Image image;
    private List<Office> offices;
    private List<RelationshipToPerson> relationships;
    private List<Investment> investments;
    private List<Milestone> milestones;
    private List<Providership> providerships;
    private List<Fund> funds;
    private List<VideoEmbed> video_embeds;
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

    public String getBlog_url() {
        return blog_url;
    }

    public void setBlog_url(String blog_url) {
        this.blog_url = blog_url;
    }

    public String getBlog_feed_url() {
        return blog_feed_url;
    }

    public void setBlog_feed_url(String blog_feed_url) {
        this.blog_feed_url = blog_feed_url;
    }

    public String getTwitter_username() {
        return twitter_username;
    }

    public void setTwitter_username(String twitter_username) {
        this.twitter_username = twitter_username;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public Integer getNumber_of_employees() {
        return number_of_employees;
    }

    public void setNumber_of_employees(Integer number_of_employees) {
        this.number_of_employees = number_of_employees;
    }

    public Integer getFounded_year() {
        return founded_year;
    }

    public void setFounded_year(Integer founded_year) {
        this.founded_year = founded_year;
    }

    public Integer getFounded_month() {
        return founded_month;
    }

    public void setFounded_month(Integer founded_month) {
        this.founded_month = founded_month;
    }

    public Integer getFounded_day() {
        return founded_day;
    }

    public void setFounded_day(Integer founded_day) {
        this.founded_day = founded_day;
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

    public List<RelationshipToPerson> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<RelationshipToPerson> relationships) {
        this.relationships = relationships;
    }

    public List<Investment> getInvestments() {
        return investments;
    }

    public void setInvestments(List<Investment> investments) {
        this.investments = investments;
    }

    public List<Milestone> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<Milestone> milestones) {
        this.milestones = milestones;
    }

    public List<Providership> getProviderships() {
        return providerships;
    }

    public void setProviderships(List<Providership> providerships) {
        this.providerships = providerships;
    }

    public List<Fund> getFunds() {
        return funds;
    }

    public void setFunds(List<Fund> funds) {
        this.funds = funds;
    }

    public List<VideoEmbed> getVideo_embeds() {
        return video_embeds;
    }

    public void setVideo_embeds(List<VideoEmbed> video_embeds) {
        this.video_embeds = video_embeds;
    }

    public List<ExternalLink> getExternal_links() {
        return external_links;
    }

    public void setExternal_links(List<ExternalLink> external_links) {
        this.external_links = external_links;
    }

    @Override
    public String toString() {
        return "FinancialOrganization{" +
                "name='" + name + '\'' +
                ", permalink='" + permalink + '\'' +
                ", crunchbase_url='" + crunchbase_url + '\'' +
                ", homepage_url='" + homepage_url + '\'' +
                ", blog_url='" + blog_url + '\'' +
                ", blog_feed_url='" + blog_feed_url + '\'' +
                ", twitter_username='" + twitter_username + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", description='" + description + '\'' +
                ", email_address='" + email_address + '\'' +
                ", number_of_employees=" + number_of_employees +
                ", founded_year=" + founded_year +
                ", founded_month=" + founded_month +
                ", founded_day=" + founded_day +
                ", tag_list='" + tag_list + '\'' +
                ", alias_list='" + alias_list + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", overview='" + overview + '\'' +
                ", image=" + image +
                ", offices=" + offices +
                ", relationships=" + relationships +
                ", investments=" + investments +
                ", milestones=" + milestones +
                ", providerships=" + providerships +
                ", funds=" + funds +
                ", video_embeds=" + video_embeds +
                ", external_links=" + external_links +
                '}';
    }

}