package com.example.foodplanner.data.datasource.meal;

import android.util.Log;

import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.data.models.meal.MealResponse;
import com.example.foodplanner.network.Services;
import com.example.foodplanner.network.Network;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealRemoteDataSource {
    private Services mealService;
    public static String TAG = "HomeFra";

    public MealRemoteDataSource() {

        mealService = Network.getInstance().services;
    }

    public void getRandomMeal(MealNetworkResponse callback){
        mealService.getRandomMeal().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Meal> meal = response.body().getMeals();
                    Log.d(TAG, "Meals received: " + meal.size());
                    callback.onSuccess(meal);
                } else {
                    Log.d(TAG, "Response failed or empty");
                    callback.onError("Response empty or failed");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                Log.d(TAG, "API call failed: " + t.getMessage());
                callback.onError(t.getMessage());
            }
        });
    }

    public void getDetailsOfMeal(MealNetworkResponse callback,String mealId){
        mealService.getMealDetailsById(mealId).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Meal> meal = response.body().getMeals();
                    Log.d(TAG, "Meals received: " + meal.size());
                    callback.onSuccess(meal);
                } else {
                    Log.d(TAG, "Response failed or empty");
                    callback.onError("Response empty or failed");
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                Log.d(TAG, "API call failed: " + t.getMessage());
                callback.onError(t.getMessage());
            }
        });
    }


}
