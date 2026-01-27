package com.example.foodplanner.prsentation.meal_details.view;

import com.example.foodplanner.data.meal.model.loacl.LocalMeal;
import com.example.foodplanner.data.meal.model.remote.Meal;

public interface MealOnClickListener {
    void addMealToFav(LocalMeal meal);

}
