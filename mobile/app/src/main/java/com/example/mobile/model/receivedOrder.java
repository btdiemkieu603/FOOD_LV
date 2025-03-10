package com.example.mobile.model;

import java.util.List;

public class receivedOrder {
     private int id;
     private String orderdate;
     private String totalprice;
     private account account;
     private orderStatus orderStatus;
     private bill bill;
     private checkout checkout;
     private List<item> items;
     private discount discount;

    public receivedOrder() {
    }

    public receivedOrder(int id, String orderdate, String totalprice, com.example.mobile.model.account account, com.example.mobile.model.orderStatus orderStatus, com.example.mobile.model.bill bill, com.example.mobile.model.checkout checkout, List<item> items, com.example.mobile.model.discount discount) {
        this.id = id;
        this.orderdate = orderdate;
        this.totalprice = totalprice;
        this.account = account;
        this.orderStatus = orderStatus;
        this.bill = bill;
        this.checkout = checkout;
        this.items = items;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public com.example.mobile.model.account getAccount() {
        return account;
    }

    public void setAccount(com.example.mobile.model.account account) {
        this.account = account;
    }

    public com.example.mobile.model.orderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(com.example.mobile.model.orderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public com.example.mobile.model.bill getBill() {
        return bill;
    }

    public void setBill(com.example.mobile.model.bill bill) {
        this.bill = bill;
    }

    public com.example.mobile.model.checkout getCheckout() {
        return checkout;
    }

    public void setCheckout(com.example.mobile.model.checkout checkout) {
        this.checkout = checkout;
    }

    public List<item> getItems() {
        return items;
    }

    public void setItems(List<item> items) {
        this.items = items;
    }

    public receivedOrder(com.example.mobile.model.discount discount) {
        this.discount = discount;
    }
}
