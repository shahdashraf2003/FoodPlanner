package com.example.foodplanner.data.meal.datasource;

import com.example.foodplanner.data.meal.model.Meal;

import java.util.List;

public interface MealNetworkResponse {
    void onSuccess (List<Meal> meals);
    void onError (String errorMsg);
    void onLoading();
}
