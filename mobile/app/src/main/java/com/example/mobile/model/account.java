package com.example.mobile.model;

public class account {
    private String phonenumber;
    private String password;
    private address address;

    public account() {
    }

    public account(String phonenumber, String password) {
        this.phonenumber = phonenumber;
        this.password = password;
    }

    public account(String phonenumber, String password, com.example.mobile.model.address address) {
        this.phonenumber = phonenumber;
        this.password = password;
        this.address = address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public com.example.mobile.model.address getAddress() {
        return address;
    }

    public void setAddress(com.example.mobile.model.address address) {
        this.address = address;
    }
}
