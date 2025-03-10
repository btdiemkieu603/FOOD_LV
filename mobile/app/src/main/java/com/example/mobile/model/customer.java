package com.example.mobile.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class customer implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("phonenumber")
    private String phoneNumber;
    @SerializedName("password")
    private String password;
    @SerializedName("firstname")
    private String firstName;
    @SerializedName("lastname")
    private String lastName;
    @SerializedName("email")
    private String email;
    @SerializedName("cart")
    private cart cart;
    @SerializedName("address")
    private address address;
    public customer() {    }

    public customer(String phoneNumber, String password, String firstName, String lastName, String email) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public customer(String id, String phoneNumber, String password, String firstName, String lastName, String email) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public customer(String phoneNumber, String password, String firstName, String lastName, String email, com.example.mobile.model.cart cart) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cart = cart;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public com.example.mobile.model.cart getCart() {
        return cart;
    }

    public void setCart(com.example.mobile.model.cart cart) {
        this.cart = cart;
    }

    public com.example.mobile.model.address getAddress() {
        return address;
    }

    public void setAddress(com.example.mobile.model.address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "customer{" +
                "id='" + id + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", cart=" + cart +
                '}';
    }
}
