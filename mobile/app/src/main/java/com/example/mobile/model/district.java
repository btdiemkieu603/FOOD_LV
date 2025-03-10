package com.example.mobile.model;

public class district {
    private String idProvince;
    private String idDistrict;
    private String name;

    public district() {
    }

    public district(String idProvince, String idDistrict, String name) {
        this.idProvince = idProvince;
        this.idDistrict = idDistrict;
        this.name = name;
    }

    public String getIdProvince() {
        return idProvince;
    }

    public void setIdProvince(String idProvince) {
        this.idProvince = idProvince;
    }

    public String getIdDistrict() {
        return idDistrict;
    }

    public void setIdDistrict(String idDistrict) {
        this.idDistrict = idDistrict;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
