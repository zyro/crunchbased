package com.github.zyro.crunchbase.entity;

import java.math.BigDecimal;

public class Office {

    private String description;
    private String address1;
    private String address2;
    private String zip_code;
    private String city;
    private String state_code;
    private String country_code;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState_code() {
        return state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Office{" +
                "description='" + description + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", zip_code='" + zip_code + '\'' +
                ", city='" + city + '\'' +
                ", state_code='" + state_code + '\'' +
                ", country_code='" + country_code + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

}