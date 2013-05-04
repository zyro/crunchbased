package com.github.zyro.crunchbase.entity;

import lombok.Data;
import java.math.BigDecimal;

@Data
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

}