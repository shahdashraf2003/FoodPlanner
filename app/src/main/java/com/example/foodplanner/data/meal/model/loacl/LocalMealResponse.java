package com.example.foodplanner.data.meal.model.loacl;


import java.util.List;

public class LocalMealResponse {
    List<LocalMeal> meals;

    public List<LocalMeal> getMeals() {
        return meals;
    }

    public void setMeals(List<LocalMeal> meals) {
        this.meals = meals;
    }
}
