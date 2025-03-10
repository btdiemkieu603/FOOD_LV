package com.example.mobile.model;

public class province {
    private String idProvince;
    private String name;

    public province() {
    }

    public province(String idProvince, String name) {
        this.idProvince = idProvince;
        this.name = name;
    }

    public String getIdProvince() {
        return idProvince;
    }

    public void setIdProvince(String idProvince) {
        this.idProvince = idProvince;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
