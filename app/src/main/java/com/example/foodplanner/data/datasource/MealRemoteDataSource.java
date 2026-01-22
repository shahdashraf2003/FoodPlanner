package com.example.foodplanner.data.datasource;

import android.util.Log;

import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.data.meal.model.MealResponse;
import com.example.foodplanner.network.MealService;
import com.example.foodplanner.network.Network;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealRemoteDataSource {
    private MealService mealService;
    public static String TAG = "HomeFra";

    public MealRemoteDataSource() {
       mealService = Network.getInstance().mealService;
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


}
