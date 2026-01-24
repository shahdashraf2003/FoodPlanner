package com.example.foodplanner.data.category.datasource;

import com.example.foodplanner.data.category.model.Category;

import java.util.List;

public interface CategoryNetworkResponse {
    void onSuccess (List<Category> categories);
    void onError (String errorMsg);
    void onLoading();
}
