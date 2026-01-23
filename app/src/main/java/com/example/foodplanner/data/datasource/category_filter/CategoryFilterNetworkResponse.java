package com.example.foodplanner.data.datasource.category_filter;

import com.example.foodplanner.data.models.category_filter.CategoryFilter;
import com.example.foodplanner.data.models.ingredient.Ingredient;

import java.util.List;

public interface CategoryFilterNetworkResponse {
    void onSuccess (List<CategoryFilter> categoriesFilter);
    void onError (String errorMsg);
    void onLoading();
}
