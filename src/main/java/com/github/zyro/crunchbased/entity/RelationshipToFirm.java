package com.github.zyro.crunchbased.entity;

public class RelationshipToFirm {

    private Boolean is_past;
    private String title;
    private FirmShort firm;

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

    public FirmShort getFirm() {
        return firm;
    }

    public void setFirm(FirmShort firm) {
        this.firm = firm;
    }

    @Override
    public String toString() {
        return "RelationshipToFirm{" +
                "is_past=" + is_past +
                ", title='" + title + '\'' +
                ", firm=" + firm +
                '}';
    }

}