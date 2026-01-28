package com.example.foodplanner.prsentation.filtered_meals.view;

import com.example.foodplanner.data.mealsfilterby.model.MealFilterBy;

import java.util.List;

public interface FilteredMealsView {
    void onFilteredMealsByCategoryError(String errMsg);
    void onFilteredMealsByCategoryLoading();
    void onFilteredMealsByCategorySuccess(List<MealFilterBy> meals);


    void onFilteredMealsByIngredientError(String errMsg);
    void onFilteredMealsByIngredientLoading();
    void onFilteredMealsByIngredientSuccess(List<MealFilterBy> meals);


    void onFilteredMealsByCountryError(String errMsg);
    void onFilteredMealsByCountryLoading();
    void onFilteredMealsByCountrySuccess(List<MealFilterBy> meals);


}
