package com.example.foodplanner.prsentation.favorite.view;

import com.example.foodplanner.data.meal.model.Meal;

import java.util.List;

public interface FavView {
    void onMealDeleted();
    void showMessage(String message);
    void onNoMealsFounded();
    void showMeals(List<Meal> meals);

}
