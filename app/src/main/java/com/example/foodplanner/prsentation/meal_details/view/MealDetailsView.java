package com.example.foodplanner.prsentation.meal_details.view;

import com.example.foodplanner.data.meal.model.remote.Meal;

import java.util.List;

public interface MealDetailsView {
    void onMealDetailsFetchError(String errMsg);
    void onMealDetailsFetchLoading();
    void onMealDetailsFetchSuccess(List<Meal> meals);
    void showMessage(String message);

}
