package com.github.zyro.crunchbase.entity;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class Office {

    private String description;
    private String address1;
    private String address2;
    private String zip_code;
    private String city;
    private String state_code;
    private String country_code;
    private BigDecimal latitude;
    private BigDecimal longitude;

}