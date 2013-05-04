package com.github.zyro.crunchbase.entity;

import lombok.Data;

@Data
public class Providership {

    private String title;
    private Boolean is_past;
    private ProviderShort provider;

}