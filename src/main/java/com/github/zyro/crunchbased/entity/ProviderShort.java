package com.github.zyro.crunchbased.entity;

public class ProviderShort {

    private String name;
    private String permalink;
    private Image image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ProviderShort{" +
                "name='" + name + '\'' +
                ", permalink='" + permalink + '\'' +
                ", image=" + image +
                '}';
    }

}