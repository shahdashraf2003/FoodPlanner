package com.example.foodplanner.prsentation.favorite.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.data.meal.model.loacl.LocalMeal;

import java.util.List;

public interface FavPresenter {
    LiveData<List<LocalMeal>> getFavMeals();
    void deleteFavMeal(LocalMeal meal);
}
