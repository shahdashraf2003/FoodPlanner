package com.example.foodplanner.prsentation.meal_details.presenter;

import com.example.foodplanner.data.meal.MealRepo;
import com.example.foodplanner.data.meal.model.loacl.LocalMeal;

public interface MealDetailsPresenter {
    void getMealDetailsById(String mealId);
    void insertMealToFav(LocalMeal meal);

    MealRepo getMealRepo();
    void addMealToCalendar(LocalMeal meal, String date);


}
