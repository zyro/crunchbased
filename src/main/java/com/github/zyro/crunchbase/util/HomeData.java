package com.github.zyro.crunchbase.util;

import lombok.Data;

import java.util.List;

/** Entity used to store home data. */
@Data
public class HomeData {

    private List<Trending> trending;
    private List<Recent> recent;
    private List<Biggest> biggest;

    /** Trending screen information entity. */
    @Data
    public static class Trending {

        private String name;
        private String namespace;
        private String permalink;
        private Integer points;

    }

    /** Recent screen information entity. */
    @Data
    public static class Recent {

        private String name;
        private String permalink;
        private String subtext;
        private String funds;

    }

    /** Biggest to date screen information entity. */
    @Data
    public static class Biggest {

        private String name;
        private String permalink;
        private String subtext;
        private String funds;

    }

}