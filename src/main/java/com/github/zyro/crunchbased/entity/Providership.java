package com.github.zyro.crunchbased.entity;

public class Providership {

    private String title;
    private Boolean is_past;
    private ProviderShort provider;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getIs_past() {
        return is_past;
    }

    public void setIs_past(Boolean is_past) {
        this.is_past = is_past;
    }

    public ProviderShort getProvider() {
        return provider;
    }

    public void setProvider(ProviderShort provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        return "Providership{" +
                "title='" + title + '\'' +
                ", is_past=" + is_past +
                ", provider=" + provider +
                '}';
    }

}