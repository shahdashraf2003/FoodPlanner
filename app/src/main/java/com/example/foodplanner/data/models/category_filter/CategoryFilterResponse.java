package com.example.foodplanner.data.models.category_filter;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryFilterResponse {
    @SerializedName("meals")
    List<CategoryFilter> categories;

    public List<CategoryFilter> getCategoriesFilter() {
        return categories;
    }
}
