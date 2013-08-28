package com.github.zyro.crunchbase.entity;

public class VideoEmbed {

    private String embed_code;
    private String description;

    public String getEmbed_code() {
        return embed_code;
    }

    public void setEmbed_code(String embed_code) {
        this.embed_code = embed_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "VideoEmbed{" +
                "embed_code='" + embed_code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}