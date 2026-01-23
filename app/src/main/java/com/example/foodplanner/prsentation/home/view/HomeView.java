package com.example.foodplanner.prsentation.home.view;


import com.example.foodplanner.data.models.category.Category;
import com.example.foodplanner.data.models.meal.Meal;

import java.util.List;

public interface HomeView {

    void onRandomMealFetchError(String errMsg);
    void onRandomMealFetchLoading();
    void onRandomMealFetchSuccess(List<Meal> meals);


    // Categories
    void onAllCategoriesFetchError(String errMsg);
    void onAllCategoriesFetchLoading();
    void onAllCategoriesFetchSuccess(List<Category> categories);

}
