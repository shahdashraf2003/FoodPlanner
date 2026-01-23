package com.example.foodplanner.data.datasource.mealsfilterby;

import com.example.foodplanner.data.models.mealfilterby.MealFilterBy;

import java.util.List;

public interface MealFilterByResponse {

    void onSuccess (List<MealFilterBy> mealsFilterBy);
    void onError (String errorMsg);
    void onLoading();
}