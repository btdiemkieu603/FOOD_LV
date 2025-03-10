package com.example.mobile.model;

import com.google.gson.annotations.SerializedName;

public class review {

    @SerializedName("id")
    private String reviewID;
    @SerializedName("numberofstar")
    private int numOfStar;
    @SerializedName("content")
    private String content;
    @SerializedName("datereview")
    private String dateReview;
    @SerializedName("account")
    private customer customer;
    @SerializedName("product")
    private product product;

    public review() {
    }

    public review(String reviewID, int numOfStar, String content, String dateReview, com.example.mobile.model.customer customer, com.example.mobile.model.product product) {
        this.reviewID = reviewID;
        this.numOfStar = numOfStar;
        this.content = content;
        this.dateReview = dateReview;
        this.customer = customer;
        this.product = product;
    }

    public String getReviewID() {
        return reviewID;
    }

    public void setReviewID(String reviewID) {
        this.reviewID = reviewID;
    }

    public int getNumOfStar() {
        return numOfStar;
    }

    public void setNumOfStar(int numOfStar) {
        this.numOfStar = numOfStar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateReview() {
        return dateReview;
    }

    public void setDateReview(String dateReview) {
        this.dateReview = dateReview;
    }

    public com.example.mobile.model.customer getCustomer() {
        return customer;
    }

    public void setCustomer(com.example.mobile.model.customer customer) {
        this.customer = customer;
    }

    public com.example.mobile.model.product getProduct() {
        return product;
    }

    public void setProduct(com.example.mobile.model.product product) {
        this.product = product;
    }
}
