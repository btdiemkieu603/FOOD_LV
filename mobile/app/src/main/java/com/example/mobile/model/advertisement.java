package com.example.mobile.model;

public class advertisement {
    private int resourceID;

    public advertisement(int resource) {
        this.resourceID = resource;
    }

    public int getResource() {
        return resourceID;
    }

    public void setResource(int resource) {
        this.resourceID = resource;
    }
}
