
package com.example.foodplanner.data.mealsfilterby.datasource;

import com.example.foodplanner.data.mealsfilterby.model.MealFilterByResponse;
import com.example.foodplanner.network.Network;
import com.example.foodplanner.network.Services;

import io.reactivex.rxjava3.core.Single;

public class MealFilterByDataSource {

    private Services mealService;
    public static String TAG = "MealFilterByDataSource";

    public MealFilterByDataSource() {
        mealService = Network.getInstance().services;
    }

    public Single<MealFilterByResponse> filterByIngredient(String ingredient) {
       return mealService.filterByIngredient(ingredient);
    }

    public Single<MealFilterByResponse> filterByCategory(String category) {
        return  mealService.filterByCategory(category);
    }

    public Single<MealFilterByResponse> filterByArea(String area) {
        return mealService.filterByArea(area);

    }
}
