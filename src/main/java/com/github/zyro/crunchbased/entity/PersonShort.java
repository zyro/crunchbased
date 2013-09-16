package com.github.zyro.crunchbased.entity;

public class PersonShort {

    private String first_name;
    private String last_name;
    private String permalink;
    private Image image;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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
        return "PersonShort{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", permalink='" + permalink + '\'' +
                ", image=" + image +
                '}';
    }

}