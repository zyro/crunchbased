package com.github.zyro.crunchbase.entity;

import lombok.Data;

@Data
public class Investor {

    private CompanyShort company;
    private FinancialOrganizationShort financial_org;
    private PersonShort person;

}