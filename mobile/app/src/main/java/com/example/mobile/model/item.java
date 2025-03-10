package com.example.mobile.model;

public class item {
    private String id;
    private String quantity;
    private String price;
    private product product;
    private boolean isReviewed;

    public item() {
    }

    public item(String id, String quantity, String price, com.example.mobile.model.product product, boolean isReviewed) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.product = product;
        this.isReviewed = isReviewed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public com.example.mobile.model.product getProduct() {
        return product;
    }

    public void setProduct(com.example.mobile.model.product product) {
        this.product = product;
    }

    public boolean isReviewed() {
        return isReviewed;
    }

    public void setReviewed(boolean reviewed) {
        isReviewed = reviewed;
    }
}
