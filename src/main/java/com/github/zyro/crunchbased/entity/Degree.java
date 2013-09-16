package com.github.zyro.crunchbased.entity;

public class Degree {

    private String degree_type;
    private String subject;
    private String institution;
    private Integer graduated_year;
    private Integer graduated_month;
    private Integer graduated_day;

    public String getDegree_type() {
        return degree_type;
    }

    public void setDegree_type(String degree_type) {
        this.degree_type = degree_type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public Integer getGraduated_year() {
        return graduated_year;
    }

    public void setGraduated_year(Integer graduated_year) {
        this.graduated_year = graduated_year;
    }

    public Integer getGraduated_month() {
        return graduated_month;
    }

    public void setGraduated_month(Integer graduated_month) {
        this.graduated_month = graduated_month;
    }

    public Integer getGraduated_day() {
        return graduated_day;
    }

    public void setGraduated_day(Integer graduated_day) {
        this.graduated_day = graduated_day;
    }

    @Override
    public String toString() {
        return "Degree{" +
                "degree_type='" + degree_type + '\'' +
                ", subject='" + subject + '\'' +
                ", institution='" + institution + '\'' +
                ", graduated_year=" + graduated_year +
                ", graduated_month=" + graduated_month +
                ", graduated_day=" + graduated_day +
                '}';
    }

}