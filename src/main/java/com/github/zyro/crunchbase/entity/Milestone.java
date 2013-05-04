package com.github.zyro.crunchbase.entity;

import lombok.Data;

@Data
public class Milestone {

    private String description;
    private Integer stoned_year;
    private Integer stoned_month;
    private Integer stoned_day;
    private String source_url;
    private String source_text;
    private String source_description;
    private String stoneable_type;
    private Object stoned_value; // TODO: figure out correct type
    private Object stoned_value_type; // TODO: figure out correct type
    private Object stoned_acquirer; // TODO: figure out correct type
    private Stoneable stoneable;

}