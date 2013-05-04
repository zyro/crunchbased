package com.github.zyro.crunchbase.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
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

}