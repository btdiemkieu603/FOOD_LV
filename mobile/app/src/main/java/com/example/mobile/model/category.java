package com.example.mobile.model;

import com.google.gson.annotations.SerializedName;

public class category {
    @SerializedName("id")
    private String categoryID;
    @SerializedName("title")
    private String category;
    @SerializedName("url_image_category")
    private String avatarLink;

    public category() {
    }

    public category(String categoryID, String category, String avatarLink) {
        this.categoryID = categoryID;
        this.category = category;
        this.avatarLink = avatarLink;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }

    @Override
    public String toString() {
        return "category{" +
                "categoryID='" + categoryID + '\'' +
                ", category='" + category + '\'' +
                ", avatarLink='" + avatarLink + '\'' +
                '}';
    }
}
