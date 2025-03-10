package com.example.mobile.model;

public class postReview {
    private int numberofstar;
    private String content;
    private String datereview;
    public postReview(int numberofstar, String content, String datereview) {
        this.numberofstar = numberofstar;
        this.content = content;
        this.datereview = datereview;
    }

    public postReview() {
    }

    public int getNumberofstar() {
        return numberofstar;
    }

    public void setNumberofstar(int numberofstar) {
        this.numberofstar = numberofstar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDatereview() {
        return datereview;
    }

    public void setDatereview(String datereview) {
        this.datereview = datereview;
    }
}
