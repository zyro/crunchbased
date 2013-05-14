package com.github.zyro.crunchbase.util;

import lombok.Data;

import java.util.List;

@Data
public class HomeData {

    private List<Trending> trending;
    private List<Recent> recent;
    private List<Biggest> biggest;

    @Data
    public static class Trending {

        private String name;
        private String namespace;
        private String permalink;
        private Integer points;

    }

    @Data
    public static class Recent {

        private String name;
        private String permalink;
        private String subtext;
        private String funds;

    }

    @Data
    public static class Biggest {

        private String name;
        private String permalink;
        private String subtext;
        private String funds;

    }
}