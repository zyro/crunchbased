package com.github.zyro.crunchbase.entity;

import lombok.Data;

@Data
public class PersonShort {

    private String first_name;
    private String last_name;
    private String permalink;
    private Image image;

}