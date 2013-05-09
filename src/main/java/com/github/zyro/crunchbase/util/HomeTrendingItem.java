package com.github.zyro.crunchbase.util;

import lombok.Data;

@Data
public class HomeTrendingItem {

    private String name;
    private String namespace;
    private String permalink;
    private Integer points;

}