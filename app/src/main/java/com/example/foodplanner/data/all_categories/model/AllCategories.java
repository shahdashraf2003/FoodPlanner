package com.example.foodplanner.data.all_categories.model;

import com.google.gson.annotations.SerializedName;

public class AllCategories {
    @SerializedName("strCategory")
    private String strCategory;

    public AllCategories(String strCategory) {
        this.strCategory = strCategory;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }
}
