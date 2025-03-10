package com.example.mobile.model;

import com.google.gson.annotations.SerializedName;

public class product {
    @SerializedName("id")
    private String productID;
    @SerializedName("name")
    private String productName;
    @SerializedName("price")
    private float productPrice;
    @SerializedName("description")
    private String description;
    @SerializedName("url_image_product")
    private String imageLink;

    public product() {
    }

    public product(String productID, String productName, float productPrice, String description, String imageLink) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.description = description;
        this.imageLink = imageLink;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String discription) {
        this.description = discription;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    @Override
    public String toString() {
        return getProductID() + "-" + getProductName() + "-" + getProductPrice() + "-" + getDescription() + "-" + getImageLink();
    }
}
