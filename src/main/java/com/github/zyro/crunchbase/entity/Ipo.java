package com.github.zyro.crunchbase.entity;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class Ipo {

    private BigDecimal valuation_amount;
    private String valuation_currency_code;
    private Integer pub_year;
    private Integer pub_month;
    private Integer pub_day;
    private String stock_symbol;

}