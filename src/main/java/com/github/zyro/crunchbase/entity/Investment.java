package com.github.zyro.crunchbase.entity;

public class Investment {

    private FundingRoundShort funding_round;

    public FundingRoundShort getFunding_round() {
        return funding_round;
    }

    public void setFunding_round(FundingRoundShort funding_round) {
        this.funding_round = funding_round;
    }

    @Override
    public String toString() {
        return "Investment{" +
                "funding_round=" + funding_round +
                '}';
    }

}