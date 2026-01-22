package com.example.foodplanner.data.datasource.category;

import android.util.Log;

import com.example.foodplanner.data.category.model.Category;
import com.example.foodplanner.data.category.model.CategoryResponse;
import com.example.foodplanner.data.datasource.meal.MealNetworkResponse;
import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.data.meal.model.MealResponse;
import com.example.foodplanner.network.Network;
import com.example.foodplanner.network.Services;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRemoteDataSource {
    private final Services categoryService;

    public CategoryRemoteDataSource() {
        categoryService = Network.getInstance().services;
    }

    public void getAllCategories(CategoryNetworkResponse callback){
        categoryService.getAllCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Category> categories = response.body().getCategories();
                    if (categories != null) {
                        callback.onSuccess(categories);
                    } else {
                        callback.onError("No categories found");
                    }
                } else {
                    callback.onError("Response empty or failed");
                    Log.e("CategoryRemoteDataSource", "Response failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Log.e("CategoryRemoteDataSource", "API call failed", t);
                callback.onError(t.getMessage());
            }
        });
    }
}
