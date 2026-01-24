
package com.example.foodplanner.data.mealsfilterby.datasource;
import android.util.Log;

import com.example.foodplanner.data.mealsfilterby.model.MealFilterBy;
import com.example.foodplanner.data.mealsfilterby.model.MealFilterByResponse;
import com.example.foodplanner.network.Network;
import com.example.foodplanner.network.Services;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealFilterByDataSource {

    private Services mealService;
    public static String TAG = "MealFilterByDataSource";

    public MealFilterByDataSource() {
        mealService = Network.getInstance().services;
    }

    public void filterByIngredient(String ingredient, MealFilterByNetworkResponse callback) {
        mealService.filterByIngredient(ingredient).enqueue(new Callback<MealFilterByResponse>() {
            @Override
            public void onResponse(Call<MealFilterByResponse> call, Response<MealFilterByResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MealFilterBy> meals = response.body().getMealsFilterBy();
                    callback.onSuccess(meals);
                } else {
                    callback.onError("Response empty or failed");

                }
            }

            @Override
            public void onFailure(Call<MealFilterByResponse> call, Throwable t) {
                Log.d(TAG, "API call failed: " + t.getMessage());
                callback.onError(t.getMessage());
            }


        });
    }

    public void filterByCategory(String category, MealFilterByNetworkResponse callback) {
        mealService.filterByCategory(category).enqueue(new Callback<MealFilterByResponse>() {
            @Override
            public void onResponse(Call<MealFilterByResponse> call, Response<MealFilterByResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MealFilterBy> meals = response.body().getMealsFilterBy();
                    callback.onSuccess(meals);
                } else {
                    callback.onError("Response empty or failed");
                }
            }

            @Override
            public void onFailure(Call<MealFilterByResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }


        });
    }

    public void filterByArea(String area, MealFilterByNetworkResponse callback) {
        mealService.filterByArea(area).enqueue(new Callback<MealFilterByResponse>() {
            @Override
            public void onResponse(Call<MealFilterByResponse> call, Response<MealFilterByResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MealFilterBy> meals = response.body().getMealsFilterBy();
                    callback.onSuccess(meals);
                } else {
                    callback.onError("Response empty or failed");
                }
            }

            @Override
            public void onFailure(Call<MealFilterByResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
