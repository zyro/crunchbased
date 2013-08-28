package com.github.zyro.crunchbase.entity;

import java.math.BigDecimal;
import java.util.List;

public class FundingRound {

    private String round_code;
    private String source_url;
    private String source_description;
    private BigDecimal raised_amount;
    private String raised_currency_code;
    private Integer funded_year;
    private Integer funded_month;
    private Integer funded_day;
    private List<Investor> company;

    public String getRound_code() {
        return round_code;
    }

    public void setRound_code(String round_code) {
        this.round_code = round_code;
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

    public List<Investor> getCompany() {
        return company;
    }

    public void setCompany(List<Investor> company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "FundingRound{" +
                "round_code='" + round_code + '\'' +
                ", source_url='" + source_url + '\'' +
                ", source_description='" + source_description + '\'' +
                ", raised_amount=" + raised_amount +
                ", raised_currency_code='" + raised_currency_code + '\'' +
                ", funded_year=" + funded_year +
                ", funded_month=" + funded_month +
                ", funded_day=" + funded_day +
                ", company=" + company +
                '}';
    }

}