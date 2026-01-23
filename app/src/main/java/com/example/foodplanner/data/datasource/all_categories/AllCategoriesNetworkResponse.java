package com.example.foodplanner.data.datasource.all_categories;

import com.example.foodplanner.data.models.all_categories.AllCategories;

import java.util.List;

public interface AllCategoriesNetworkResponse {
    void onSuccess (List<AllCategories> categoriesFilter);
    void onError (String errorMsg);
    void onLoading();
}
