package com.example.foodplanner.data.meal.datasource.remote;

import com.example.foodplanner.data.meal.model.MealResponse;
import com.example.foodplanner.network.Network;
import com.example.foodplanner.network.Services;

import io.reactivex.rxjava3.core.Single;

public class MealRemoteDataSource {
    private Services mealService;
    public static String TAG = "MealRemoteDataSource";

    public MealRemoteDataSource() {

        mealService = Network.getInstance().services;
    }

    public Single<MealResponse> getRandomMeal() {
        return mealService.getRandomMeal();

    }



    public Single<MealResponse> getDetailsOfMeal(String mealId) {

        return mealService.getMealDetailsById(mealId);

    }


    public Single<MealResponse> getSearchedMeal(String mealName)
    {
        return mealService.searchMeal(mealName);
    }


}
