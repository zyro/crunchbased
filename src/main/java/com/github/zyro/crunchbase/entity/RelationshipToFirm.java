package com.github.zyro.crunchbase.entity;

import lombok.Data;

@Data
public class RelationshipToFirm {

    private Boolean is_past;
    private String title;
    private FirmShort firm;

}