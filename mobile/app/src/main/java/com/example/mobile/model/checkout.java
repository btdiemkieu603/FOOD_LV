package com.example.mobile.model;

public class checkout {
    private int id;
    private String method;

    public checkout() {
    }

    public checkout(int id, String method) {
        this.id = id;
        this.method = method;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
