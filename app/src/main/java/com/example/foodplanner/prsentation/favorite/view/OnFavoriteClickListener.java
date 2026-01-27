package com.example.foodplanner.prsentation.favorite.view;

import com.example.foodplanner.data.meal.model.loacl.LocalMeal;
import com.example.foodplanner.data.meal.model.remote.Meal;

public interface OnFavoriteClickListener {
    void onMealClick(LocalMeal meal);
}
