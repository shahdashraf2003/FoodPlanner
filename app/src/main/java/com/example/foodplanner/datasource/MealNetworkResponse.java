package com.example.foodplanner.datasource;

import com.example.foodplanner.model.Meal;

import java.util.List;

public interface MealNetworkResponse {
    void onSuccess (List<Meal> meals);
    void onError (String errorMsg);
    void onLoading();
}
