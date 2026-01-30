package com.example.foodplanner.prsentation.favorite.presenter;

import com.example.foodplanner.data.meal.model.Meal;

public interface FavPresenter {
    void getFavMeals();
    void deleteFavMeal(Meal meal);
}
