package com.example.foodplanner.data.datasource.meal;

import com.example.foodplanner.data.models.meal.Meal;

import java.util.List;

public interface MealNetworkResponse {
    void onSuccess (List<Meal> meals);
    void onError (String errorMsg);
    void onLoading();
}
