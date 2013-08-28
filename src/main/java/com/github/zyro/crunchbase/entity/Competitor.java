package com.github.zyro.crunchbase.entity;

public class Competitor {

    private CompanyShort competitor;

    public CompanyShort getCompetitor() {
        return competitor;
    }

    public void setCompetitor(CompanyShort competitor) {
        this.competitor = competitor;
    }

    @Override
    public String toString() {
        return "Competitor{" +
                "competitor=" + competitor +
                '}';
    }

}