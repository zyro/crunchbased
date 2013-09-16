package com.github.zyro.crunchbased.entity;

import java.util.List;

public class Search {

    private Integer total;
    private Integer page;
    private String crunchbase_url;
    private List<Result> results;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getCrunchbase_url() {
        return crunchbase_url;
    }

    public void setCrunchbase_url(String crunchbase_url) {
        this.crunchbase_url = crunchbase_url;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "Search{" +
                "total=" + total +
                ", page=" + page +
                ", crunchbase_url='" + crunchbase_url + '\'' +
                ", results=" + results +
                '}';
    }

}