package com.github.zyro.crunchbase.entity;

import lombok.Data;

@Data
public class Degree {

    private String degree_type;
    private String subject;
    private String institution;
    private Integer graduated_year;
    private Integer graduated_month;
    private Integer graduated_day;

}