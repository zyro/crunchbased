package com.github.zyro.crunchbased.entity;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStoned_year() {
        return stoned_year;
    }

    public void setStoned_year(Integer stoned_year) {
        this.stoned_year = stoned_year;
    }

    public Integer getStoned_month() {
        return stoned_month;
    }

    public void setStoned_month(Integer stoned_month) {
        this.stoned_month = stoned_month;
    }

    public Integer getStoned_day() {
        return stoned_day;
    }

    public void setStoned_day(Integer stoned_day) {
        this.stoned_day = stoned_day;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public String getSource_text() {
        return source_text;
    }

    public void setSource_text(String source_text) {
        this.source_text = source_text;
    }

    public String getSource_description() {
        return source_description;
    }

    public void setSource_description(String source_description) {
        this.source_description = source_description;
    }

    public String getStoneable_type() {
        return stoneable_type;
    }

    public void setStoneable_type(String stoneable_type) {
        this.stoneable_type = stoneable_type;
    }

    public Object getStoned_value() {
        return stoned_value;
    }

    public void setStoned_value(Object stoned_value) {
        this.stoned_value = stoned_value;
    }

    public Object getStoned_value_type() {
        return stoned_value_type;
    }

    public void setStoned_value_type(Object stoned_value_type) {
        this.stoned_value_type = stoned_value_type;
    }

    public Object getStoned_acquirer() {
        return stoned_acquirer;
    }

    public void setStoned_acquirer(Object stoned_acquirer) {
        this.stoned_acquirer = stoned_acquirer;
    }

    public Stoneable getStoneable() {
        return stoneable;
    }

    public void setStoneable(Stoneable stoneable) {
        this.stoneable = stoneable;
    }

    @Override
    public String toString() {
        return "Milestone{" +
                "description='" + description + '\'' +
                ", stoned_year=" + stoned_year +
                ", stoned_month=" + stoned_month +
                ", stoned_day=" + stoned_day +
                ", source_url='" + source_url + '\'' +
                ", source_text='" + source_text + '\'' +
                ", source_description='" + source_description + '\'' +
                ", stoneable_type='" + stoneable_type + '\'' +
                ", stoned_value=" + stoned_value +
                ", stoned_value_type=" + stoned_value_type +
                ", stoned_acquirer=" + stoned_acquirer +
                ", stoneable=" + stoneable +
                '}';
    }

}