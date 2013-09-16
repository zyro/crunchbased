package com.github.zyro.crunchbased.entity;

public class Investor {

    private CompanyShort company;
    private FinancialOrganizationShort financial_org;
    private PersonShort person;

    public CompanyShort getCompany() {
        return company;
    }

    public void setCompany(CompanyShort company) {
        this.company = company;
    }

    public FinancialOrganizationShort getFinancial_org() {
        return financial_org;
    }

    public void setFinancial_org(FinancialOrganizationShort financial_org) {
        this.financial_org = financial_org;
    }

    public PersonShort getPerson() {
        return person;
    }

    public void setPerson(PersonShort person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Investor{" +
                "company=" + company +
                ", financial_org=" + financial_org +
                ", person=" + person +
                '}';
    }

}