package com.example.mobile.model;

public class offlineProduct {
    private product product;
    private int quantity;

    public offlineProduct() {
    }

    public offlineProduct(com.example.mobile.model.product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public com.example.mobile.model.product getProduct() {
        return product;
    }

    public void setProduct(com.example.mobile.model.product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return product.toString() + "-" + quantity;
    }
}
