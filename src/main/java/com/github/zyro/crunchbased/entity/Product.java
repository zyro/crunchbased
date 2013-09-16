package com.github.zyro.crunchbased.entity;

import java.util.List;

public class Product {

    private String name;
    private String permalink;
    private String crunchbase_url;
    private String homepage_url;
    private String blog_url;
    private String blog_feed_url;
    private String twitter_username;
    private String stage_code;
    private String deadpooled_url;
    private String invite_share_url;
    private String tag_list;
    private String alias_list;
    private Integer deadpooled_year;
    private Integer deadpooled_month;
    private Integer deadpooled_day;
    private Integer launched_year;
    private Integer launched_month;
    private Integer launched_day;
    private String created_at;
    private String updated_at;
    private String overview;
    private Image image;
    private CompanyShort company;
    private List<Milestone> milestones;
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

    public String getStage_code() {
        return stage_code;
    }

    public void setStage_code(String stage_code) {
        this.stage_code = stage_code;
    }

    public String getDeadpooled_url() {
        return deadpooled_url;
    }

    public void setDeadpooled_url(String deadpooled_url) {
        this.deadpooled_url = deadpooled_url;
    }

    public String getInvite_share_url() {
        return invite_share_url;
    }

    public void setInvite_share_url(String invite_share_url) {
        this.invite_share_url = invite_share_url;
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

    public Integer getDeadpooled_year() {
        return deadpooled_year;
    }

    public void setDeadpooled_year(Integer deadpooled_year) {
        this.deadpooled_year = deadpooled_year;
    }

    public Integer getDeadpooled_month() {
        return deadpooled_month;
    }

    public void setDeadpooled_month(Integer deadpooled_month) {
        this.deadpooled_month = deadpooled_month;
    }

    public Integer getDeadpooled_day() {
        return deadpooled_day;
    }

    public void setDeadpooled_day(Integer deadpooled_day) {
        this.deadpooled_day = deadpooled_day;
    }

    public Integer getLaunched_year() {
        return launched_year;
    }

    public void setLaunched_year(Integer launched_year) {
        this.launched_year = launched_year;
    }

    public Integer getLaunched_month() {
        return launched_month;
    }

    public void setLaunched_month(Integer launched_month) {
        this.launched_month = launched_month;
    }

    public Integer getLaunched_day() {
        return launched_day;
    }

    public void setLaunched_day(Integer launched_day) {
        this.launched_day = launched_day;
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

    public CompanyShort getCompany() {
        return company;
    }

    public void setCompany(CompanyShort company) {
        this.company = company;
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

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", permalink='" + permalink + '\'' +
                ", crunchbase_url='" + crunchbase_url + '\'' +
                ", homepage_url='" + homepage_url + '\'' +
                ", blog_url='" + blog_url + '\'' +
                ", blog_feed_url='" + blog_feed_url + '\'' +
                ", twitter_username='" + twitter_username + '\'' +
                ", stage_code='" + stage_code + '\'' +
                ", deadpooled_url='" + deadpooled_url + '\'' +
                ", invite_share_url='" + invite_share_url + '\'' +
                ", tag_list='" + tag_list + '\'' +
                ", alias_list='" + alias_list + '\'' +
                ", deadpooled_year=" + deadpooled_year +
                ", deadpooled_month=" + deadpooled_month +
                ", deadpooled_day=" + deadpooled_day +
                ", launched_year=" + launched_year +
                ", launched_month=" + launched_month +
                ", launched_day=" + launched_day +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", overview='" + overview + '\'' +
                ", image=" + image +
                ", company=" + company +
                ", milestones=" + milestones +
                ", video_embeds=" + video_embeds +
                ", external_links=" + external_links +
                '}';
    }

}