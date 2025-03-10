package com.example.mobile.model;

public class discount {
    private String id;
    private String code;
    private double percent;
    private boolean isExist;
    private String startdate;
    private String enddate;
    public discount() {
    }

    public discount(String id, String code, double percent, boolean isExist, String startdate, String enddate) {
        this.id = id;
        this.code = code;
        this.percent = percent;
        this.isExist = isExist;
        this.startdate = startdate;
        this.enddate = enddate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }
}
