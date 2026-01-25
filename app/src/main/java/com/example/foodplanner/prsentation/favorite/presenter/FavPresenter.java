package com.example.foodplanner.prsentation.favorite.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.data.meal.model.Meal;

import java.util.List;

public interface FavPresenter {
    LiveData<List<Meal>> getFavMeals();
    void deleteFavMeal(Meal meal);
}
