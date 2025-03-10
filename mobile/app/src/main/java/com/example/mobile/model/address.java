package com.example.mobile.model;

public class address {

    private int id;
    private String street;
    private String ward;
    private String district;
    private String province;

    public address() {
    }

    public address(int id, String street, String ward, String district, String province) {
        this.id = id;
        this.street = street;
        this.ward = ward;
        this.district = district;
        this.province = province;
    }

    public String getAddressDetail() {
        return street + ", " + ward + ", " + district + ", " + province;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
