package com.github.zyro.crunchbase.entity;

import lombok.Data;
import java.util.List;

@Data
public class Image {

    private List<List<Object>> available_sizes;
    private String attribution;

    public Integer getSmallSizeX() {
        List<Object> size = available_sizes.get(0);
        List<Object> dimensions = (List<Object>) size.get(0);
        return (Integer) dimensions.get(0);
    }

    public Integer getSmallSizeY() {
        List<Object> size = available_sizes.get(0);
        List<Object> dimensions = (List<Object>) size.get(0);
        return (Integer) dimensions.get(1);
    }

    public String getSmallAsset() {
        List<Object> size = available_sizes.get(0);
        return prependSlashIfMissing((String) size.get(1));
    }

    public Integer getMediumSizeX() {
        List<Object> size = available_sizes.get(1);
        List<Object> dimensions = (List<Object>) size.get(0);
        return (Integer) dimensions.get(0);
    }

    public Integer getMediumSizeY() {
        List<Object> size = available_sizes.get(1);
        List<Object> dimensions = (List<Object>) size.get(0);
        return (Integer) dimensions.get(1);
    }

    public String getMediumAsset() {
        List<Object> size = available_sizes.get(1);
        return prependSlashIfMissing((String) size.get(1));
    }

    public Integer getLargeSizeX() {
        List<Object> size = available_sizes.get(2);
        List<Object> dimensions = (List<Object>) size.get(0);
        return (Integer) dimensions.get(0);
    }

    public Integer getLargeSizeY() {
        List<Object> size = available_sizes.get(2);
        List<Object> dimensions = (List<Object>) size.get(0);
        return (Integer) dimensions.get(1);
    }

    public String getLargeAsset() {
        List<Object> size = available_sizes.get(2);
        return prependSlashIfMissing((String) size.get(1));
    }

    protected String prependSlashIfMissing(final String string) {
        return string.startsWith("/") ? string : "/" + string;
    }

}