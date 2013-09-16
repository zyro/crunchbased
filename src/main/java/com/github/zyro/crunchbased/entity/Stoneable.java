package com.github.zyro.crunchbased.entity;

public class Stoneable {

    private String name;
    private String permalink;

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

    @Override
    public String toString() {
        return "Stoneable{" +
                "name='" + name + '\'' +
                ", permalink='" + permalink + '\'' +
                '}';
    }

}