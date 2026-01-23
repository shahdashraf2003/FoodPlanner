package com.example.foodplanner.data.models.category_filter;

import com.google.gson.annotations.SerializedName;

public class CategoryFilter {
    @SerializedName("strCategory")
    private String strCategory;

    public CategoryFilter(String strCategory) {
        this.strCategory = strCategory;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }
}
