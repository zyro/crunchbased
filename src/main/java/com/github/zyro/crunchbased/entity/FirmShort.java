package com.github.zyro.crunchbased.entity;

public class FirmShort {

    private String name;
    private String permalink;
    private String type_of_entity;
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

    public String getType_of_entity() {
        return type_of_entity;
    }

    public void setType_of_entity(String type_of_entity) {
        this.type_of_entity = type_of_entity;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "FirmShort{" +
                "name='" + name + '\'' +
                ", permalink='" + permalink + '\'' +
                ", type_of_entity='" + type_of_entity + '\'' +
                ", image=" + image +
                '}';
    }

}