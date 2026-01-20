package com.example.foodplanner;


import com.example.foodplanner.model.Meal;

public interface HomeView {

    // Random
    void showRandomLoading();
    void showRandomMeal(Meal meal);


    // Categories
    void showCategories(List<Category> categories);


}
