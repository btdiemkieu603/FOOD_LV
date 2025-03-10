package com.example.mobile.model;

public class ward {
    private String idDistrict;
    private String idCommune;
    private String name;

    public ward() {
    }

    public ward(String idDistrict, String idCommune, String name) {
        this.idDistrict = idDistrict;
        this.idCommune = idCommune;
        this.name = name;
    }

    public String getIdDistrict() {
        return idDistrict;
    }

    public void setIdDistrict(String idDistrict) {
        this.idDistrict = idDistrict;
    }

    public String getIdCommune() {
        return idCommune;
    }

    public void setIdCommune(String idCommune) {
        this.idCommune = idCommune;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
