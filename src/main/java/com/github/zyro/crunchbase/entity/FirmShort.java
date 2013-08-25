package com.github.zyro.crunchbase.entity;

import lombok.Data;

@Data
public class FirmShort {

    private String name;
    private String permalink;
    private String type_of_entity;
    private Image image;

}