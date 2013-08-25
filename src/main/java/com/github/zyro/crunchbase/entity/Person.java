package com.github.zyro.crunchbase.entity;

import lombok.Data;

import java.util.List;

@Data
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

}