package com.example.foodplanner.data.all_categories.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllCategoriesResponse {
    @SerializedName("meals")
    List<AllCategories> categories;

    public List<AllCategories> getCategoriesFilter() {
        return categories;
    }
}
