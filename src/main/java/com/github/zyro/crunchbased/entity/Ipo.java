package com.github.zyro.crunchbased.entity;

import java.math.BigDecimal;

public class Ipo {

    private BigDecimal valuation_amount;
    private String valuation_currency_code;
    private Integer pub_year;
    private Integer pub_month;
    private Integer pub_day;
    private String stock_symbol;

    public BigDecimal getValuation_amount() {
        return valuation_amount;
    }

    public void setValuation_amount(BigDecimal valuation_amount) {
        this.valuation_amount = valuation_amount;
    }

    public String getValuation_currency_code() {
        return valuation_currency_code;
    }

    public void setValuation_currency_code(String valuation_currency_code) {
        this.valuation_currency_code = valuation_currency_code;
    }

    public Integer getPub_year() {
        return pub_year;
    }

    public void setPub_year(Integer pub_year) {
        this.pub_year = pub_year;
    }

    public Integer getPub_month() {
        return pub_month;
    }

    public void setPub_month(Integer pub_month) {
        this.pub_month = pub_month;
    }

    public Integer getPub_day() {
        return pub_day;
    }

    public void setPub_day(Integer pub_day) {
        this.pub_day = pub_day;
    }

    public String getStock_symbol() {
        return stock_symbol;
    }

    public void setStock_symbol(String stock_symbol) {
        this.stock_symbol = stock_symbol;
    }

    @Override
    public String toString() {
        return "Ipo{" +
                "valuation_amount=" + valuation_amount +
                ", valuation_currency_code='" + valuation_currency_code + '\'' +
                ", pub_year=" + pub_year +
                ", pub_month=" + pub_month +
                ", pub_day=" + pub_day +
                ", stock_symbol='" + stock_symbol + '\'' +
                '}';
    }

}