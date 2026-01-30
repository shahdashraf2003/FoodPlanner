package com.example.foodplanner.prsentation.home.view;


import com.example.foodplanner.data.category.model.Category;
import com.example.foodplanner.data.meal.model.Meal;

import java.util.List;

public interface HomeView {

    void onRandomMealFetchError(String errMsg);
    void onRandomMealFetchLoading();
    void onRandomMealFetchSuccess(Meal meal);


    // Categories
    void onAllCategoriesFetchError(String errMsg);
    void onAllCategoriesFetchLoading();
    void onAllCategoriesFetchSuccess(List<Category> categories);

}
