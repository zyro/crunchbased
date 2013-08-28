package com.github.zyro.crunchbase.entity;

import java.math.BigDecimal;

public class Acquisition {

    private BigDecimal price_amount;
    private String price_currency_code;
    private String term_code;
    private String source_url;
    private String source_description;
    private Integer acquired_year;
    private Integer acquired_month;
    private Integer acquired_day;
    private CompanyShort company;

    public BigDecimal getPrice_amount() {
        return price_amount;
    }

    public void setPrice_amount(BigDecimal price_amount) {
        this.price_amount = price_amount;
    }

    public String getPrice_currency_code() {
        return price_currency_code;
    }

    public void setPrice_currency_code(String price_currency_code) {
        this.price_currency_code = price_currency_code;
    }

    public String getTerm_code() {
        return term_code;
    }

    public void setTerm_code(String term_code) {
        this.term_code = term_code;
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

    public Integer getAcquired_year() {
        return acquired_year;
    }

    public void setAcquired_year(Integer acquired_year) {
        this.acquired_year = acquired_year;
    }

    public Integer getAcquired_month() {
        return acquired_month;
    }

    public void setAcquired_month(Integer acquired_month) {
        this.acquired_month = acquired_month;
    }

    public Integer getAcquired_day() {
        return acquired_day;
    }

    public void setAcquired_day(Integer acquired_day) {
        this.acquired_day = acquired_day;
    }

    public CompanyShort getCompany() {
        return company;
    }

    public void setCompany(CompanyShort company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Acquisition{" +
                "price_amount=" + price_amount +
                ", price_currency_code='" + price_currency_code + '\'' +
                ", term_code='" + term_code + '\'' +
                ", source_url='" + source_url + '\'' +
                ", source_description='" + source_description + '\'' +
                ", acquired_year=" + acquired_year +
                ", acquired_month=" + acquired_month +
                ", acquired_day=" + acquired_day +
                ", company=" + company +
                '}';
    }

}