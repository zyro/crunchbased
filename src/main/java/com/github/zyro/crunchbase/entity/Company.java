package com.github.zyro.crunchbase.entity;

import lombok.Data;

import java.util.List;

@Data
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

}