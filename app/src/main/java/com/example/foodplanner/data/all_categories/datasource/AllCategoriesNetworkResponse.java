package com.example.foodplanner.data.all_categories.datasource;

import com.example.foodplanner.data.all_categories.model.AllCategories;

import java.util.List;

public interface AllCategoriesNetworkResponse {
    void onSuccess (List<AllCategories> categoriesFilter);
    void onError (String errorMsg);
    void onLoading();
}
