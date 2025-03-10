package com.example.mobile.model;

public class order {
    private String orderdate;
    private String code;

    public order() {
    }

    public order(String orderdate, String code) {
        this.orderdate = orderdate;
        this.code = code;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}