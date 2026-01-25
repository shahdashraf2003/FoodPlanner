package com.example.foodplanner.prsentation.meal_details.presenter;

import com.example.foodplanner.data.meal.model.Meal;

public interface MealDetailsPresenter {
    void getMealDetailsById(String mealId);
    void insertMealToFav(Meal meal);


}
