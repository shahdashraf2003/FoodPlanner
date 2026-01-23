package com.example.foodplanner.data.datasource.category;

import com.example.foodplanner.data.models.category.Category;

import java.util.List;

public interface CategoryNetworkResponse {
    void onSuccess (List<Category> categories);
    void onError (String errorMsg);
    void onLoading();
}
