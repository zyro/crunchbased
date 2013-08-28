package com.github.zyro.crunchbase.entity;

import java.util.List;

public class Person {

    private String first_name;
    private String last_name;
    private String permalink;
    private String crunchbase_url;
    private String homepage_url;
    private String birthplace;
    private String twitter_username;
    private String blog_url;
    private String blog_feed_url;
    private String affiliation_name;
    private Integer born_year;
    private Integer born_month;
    private Integer born_day;
    private String tag_list;
    private String alias_list;
    private String created_at;
    private String updated_at;
    private String overview;
    private Image image;
    private List<Degree> degrees;
    private List<RelationshipToFirm> relationships;
    private List<Investment> investments;
    private List<Milestone> milestones;
    private List<VideoEmbed> video_embeds;
    private List<ExternalLink> external_links;
    private List<WebPresence> web_presences;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getTwitter_username() {
        return twitter_username;
    }

    public void setTwitter_username(String twitter_username) {
        this.twitter_username = twitter_username;
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

    public String getAffiliation_name() {
        return affiliation_name;
    }

    public void setAffiliation_name(String affiliation_name) {
        this.affiliation_name = affiliation_name;
    }

    public Integer getBorn_year() {
        return born_year;
    }

    public void setBorn_year(Integer born_year) {
        this.born_year = born_year;
    }

    public Integer getBorn_month() {
        return born_month;
    }

    public void setBorn_month(Integer born_month) {
        this.born_month = born_month;
    }

    public Integer getBorn_day() {
        return born_day;
    }

    public void setBorn_day(Integer born_day) {
        this.born_day = born_day;
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

    public List<Degree> getDegrees() {
        return degrees;
    }

    public void setDegrees(List<Degree> degrees) {
        this.degrees = degrees;
    }

    public List<RelationshipToFirm> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<RelationshipToFirm> relationships) {
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

    public List<WebPresence> getWeb_presences() {
        return web_presences;
    }

    public void setWeb_presences(List<WebPresence> web_presences) {
        this.web_presences = web_presences;
    }

    @Override
    public String toString() {
        return "Person{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", permalink='" + permalink + '\'' +
                ", crunchbase_url='" + crunchbase_url + '\'' +
                ", homepage_url='" + homepage_url + '\'' +
                ", birthplace='" + birthplace + '\'' +
                ", twitter_username='" + twitter_username + '\'' +
                ", blog_url='" + blog_url + '\'' +
                ", blog_feed_url='" + blog_feed_url + '\'' +
                ", affiliation_name='" + affiliation_name + '\'' +
                ", born_year=" + born_year +
                ", born_month=" + born_month +
                ", born_day=" + born_day +
                ", tag_list='" + tag_list + '\'' +
                ", alias_list='" + alias_list + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", overview='" + overview + '\'' +
                ", image=" + image +
                ", degrees=" + degrees +
                ", relationships=" + relationships +
                ", investments=" + investments +
                ", milestones=" + milestones +
                ", video_embeds=" + video_embeds +
                ", external_links=" + external_links +
                ", web_presences=" + web_presences +
                '}';
    }

}