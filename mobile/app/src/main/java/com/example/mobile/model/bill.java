package com.example.mobile.model;

public class bill {
    private int id;
    private String issuedate;
    private String totalprice;

    public bill() {
    }

    public bill(int id, String issuedate, String totalprice) {
        this.id = id;
        this.issuedate = issuedate;
        this.totalprice = totalprice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIssuedate() {
        return issuedate;
    }

    public void setIssuedate(String issuedate) {
        this.issuedate = issuedate;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }
}
