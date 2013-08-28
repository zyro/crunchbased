package com.github.zyro.crunchbase.entity;

public class RelationshipToPerson {

    private Boolean is_past;
    private String title;
    private PersonShort person;

    public Boolean getIs_past() {
        return is_past;
    }

    public void setIs_past(Boolean is_past) {
        this.is_past = is_past;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PersonShort getPerson() {
        return person;
    }

    public void setPerson(PersonShort person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "RelationshipToPerson{" +
                "is_past=" + is_past +
                ", title='" + title + '\'' +
                ", person=" + person +
                '}';
    }

}