package com.example.mobile.model;

import com.google.gson.annotations.SerializedName;

public class cart {
    @SerializedName("id")
    private String cartID;
    @SerializedName("detail")
    private String detail;

    public cart() {
    }

    public cart(String cartID, String detail) {
        this.cartID = cartID;
        this.detail = detail;
    }

    public String getCartID() {
        return cartID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
