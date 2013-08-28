package com.github.zyro.crunchbase.entity;

import java.util.List;

public class Company {

    private String name;
    private String permalink;
    private String crunchbase_url;
    private String homepage_url;
    private String blog_url;
    private String blog_feed_url;
    private String twitter_username;
    private String category_code;
    private Integer number_of_employees;
    private Integer founded_year;
    private Integer founded_month;
    private Integer founded_day;
    private Integer deadpooled_year;
    private Integer deadpooled_month;
    private Integer deadpooled_day;
    private String deadpooled_url;
    private String tag_list;
    private String alias_list;
    private String email_address;
    private String phone_number;
    private String description;
    private String created_at;
    private String updated_at;
    private String overview;
    private Image image;
    private List<ProductShort> products;
    private List<RelationshipToPerson> relationships;
    private List<Competitor> competitions;
    private List<Providership> providerships;
    private String total_money_raised;
    private List<FundingRound> funding_rounds;
    private List<Investment> investments;
    private Object acquisition; // TODO: figure out correct type
    private List<Acquisition> acquisitions;
    private List<Office> offices;
    private List<Milestone> milestones;
    private Ipo ipo;
    private List<VideoEmbed> video_embeds;
    private List<Image> screenshots;
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

    public String getCategory_code() {
        return category_code;
    }

    public void setCategory_code(String category_code) {
        this.category_code = category_code;
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

    public String getDeadpooled_url() {
        return deadpooled_url;
    }

    public void setDeadpooled_url(String deadpooled_url) {
        this.deadpooled_url = deadpooled_url;
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

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
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

    public List<ProductShort> getProducts() {
        return products;
    }

    public void setProducts(List<ProductShort> products) {
        this.products = products;
    }

    public List<RelationshipToPerson> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<RelationshipToPerson> relationships) {
        this.relationships = relationships;
    }

    public List<Competitor> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(List<Competitor> competitions) {
        this.competitions = competitions;
    }

    public List<Providership> getProviderships() {
        return providerships;
    }

    public void setProviderships(List<Providership> providerships) {
        this.providerships = providerships;
    }

    public String getTotal_money_raised() {
        return total_money_raised;
    }

    public void setTotal_money_raised(String total_money_raised) {
        this.total_money_raised = total_money_raised;
    }

    public List<FundingRound> getFunding_rounds() {
        return funding_rounds;
    }

    public void setFunding_rounds(List<FundingRound> funding_rounds) {
        this.funding_rounds = funding_rounds;
    }

    public List<Investment> getInvestments() {
        return investments;
    }

    public void setInvestments(List<Investment> investments) {
        this.investments = investments;
    }

    public Object getAcquisition() {
        return acquisition;
    }

    public void setAcquisition(Object acquisition) {
        this.acquisition = acquisition;
    }

    public List<Acquisition> getAcquisitions() {
        return acquisitions;
    }

    public void setAcquisitions(List<Acquisition> acquisitions) {
        this.acquisitions = acquisitions;
    }

    public List<Office> getOffices() {
        return offices;
    }

    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }

    public List<Milestone> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<Milestone> milestones) {
        this.milestones = milestones;
    }

    public Ipo getIpo() {
        return ipo;
    }

    public void setIpo(Ipo ipo) {
        this.ipo = ipo;
    }

    public List<VideoEmbed> getVideo_embeds() {
        return video_embeds;
    }

    public void setVideo_embeds(List<VideoEmbed> video_embeds) {
        this.video_embeds = video_embeds;
    }

    public List<Image> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(List<Image> screenshots) {
        this.screenshots = screenshots;
    }

    public List<ExternalLink> getExternal_links() {
        return external_links;
    }

    public void setExternal_links(List<ExternalLink> external_links) {
        this.external_links = external_links;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", permalink='" + permalink + '\'' +
                ", crunchbase_url='" + crunchbase_url + '\'' +
                ", homepage_url='" + homepage_url + '\'' +
                ", blog_url='" + blog_url + '\'' +
                ", blog_feed_url='" + blog_feed_url + '\'' +
                ", twitter_username='" + twitter_username + '\'' +
                ", category_code='" + category_code + '\'' +
                ", number_of_employees=" + number_of_employees +
                ", founded_year=" + founded_year +
                ", founded_month=" + founded_month +
                ", founded_day=" + founded_day +
                ", deadpooled_year=" + deadpooled_year +
                ", deadpooled_month=" + deadpooled_month +
                ", deadpooled_day=" + deadpooled_day +
                ", deadpooled_url='" + deadpooled_url + '\'' +
                ", tag_list='" + tag_list + '\'' +
                ", alias_list='" + alias_list + '\'' +
                ", email_address='" + email_address + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", description='" + description + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", overview='" + overview + '\'' +
                ", image=" + image +
                ", products=" + products +
                ", relationships=" + relationships +
                ", competitions=" + competitions +
                ", providerships=" + providerships +
                ", total_money_raised='" + total_money_raised + '\'' +
                ", funding_rounds=" + funding_rounds +
                ", investments=" + investments +
                ", acquisition=" + acquisition +
                ", acquisitions=" + acquisitions +
                ", offices=" + offices +
                ", milestones=" + milestones +
                ", ipo=" + ipo +
                ", video_embeds=" + video_embeds +
                ", screenshots=" + screenshots +
                ", external_links=" + external_links +
                '}';
    }

}