package com.github.zyro.crunchbase.entity;

import lombok.Data;

@Data
public class RelationshipToPerson {

    private Boolean is_past;
    private String title;
    private PersonShort person;

}