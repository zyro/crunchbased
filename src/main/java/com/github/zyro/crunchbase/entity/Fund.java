package com.github.zyro.crunchbase.entity;

import java.math.BigDecimal;

public class Fund implements Comparable<Fund> {

    private String name;
    private Integer funded_year;
    private Integer funded_month;
    private Integer funded_day;
    private BigDecimal raised_amount;
    private String raised_currency_code;
    private String source_url;
    private String source_description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFunded_year() {
        return funded_year;
    }

    public void setFunded_year(Integer funded_year) {
        this.funded_year = funded_year;
    }

    public Integer getFunded_month() {
        return funded_month;
    }

    public void setFunded_month(Integer funded_month) {
        this.funded_month = funded_month;
    }

    public Integer getFunded_day() {
        return funded_day;
    }

    public void setFunded_day(Integer funded_day) {
        this.funded_day = funded_day;
    }

    public BigDecimal getRaised_amount() {
        return raised_amount;
    }

    public void setRaised_amount(BigDecimal raised_amount) {
        this.raised_amount = raised_amount;
    }

    public String getRaised_currency_code() {
        return raised_currency_code;
    }

    public void setRaised_currency_code(String raised_currency_code) {
        this.raised_currency_code = raised_currency_code;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public String getSource_description() {
        return source_description;
    }

    public void setSource_description(String source_description) {
        this.source_description = source_description;
    }

    @Override
    public String toString() {
        return "Fund{" +
                "name='" + name + '\'' +
                ", funded_year=" + funded_year +
                ", funded_month=" + funded_month +
                ", funded_day=" + funded_day +
                ", raised_amount=" + raised_amount +
                ", raised_currency_code='" + raised_currency_code + '\'' +
                ", source_url='" + source_url + '\'' +
                ", source_description='" + source_description + '\'' +
                '}';
    }

    @Override
    public int compareTo(final Fund another) {
        if(another != null) {
            final int year = funded_year.compareTo(another.funded_year);
            if(year != 0) {
                return year;
            }
            final int month = funded_month.compareTo(another.funded_month);
            if(month != 0) {
                return month;
            }
            return funded_day.compareTo(another.funded_day);
        }
        return 0;
    }

}