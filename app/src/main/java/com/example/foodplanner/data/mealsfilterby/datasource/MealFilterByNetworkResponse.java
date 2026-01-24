package com.example.foodplanner.data.mealsfilterby.datasource;

import com.example.foodplanner.data.mealsfilterby.model.MealFilterBy;

import java.util.List;

public interface MealFilterByNetworkResponse {

    void onSuccess (List<MealFilterBy> mealsFilterBy);
    void onError (String errorMsg);
    void onLoading();
}