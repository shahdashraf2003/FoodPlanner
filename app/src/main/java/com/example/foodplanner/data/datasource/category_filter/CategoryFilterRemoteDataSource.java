package com.example.foodplanner.data.datasource.category_filter;

import android.util.Log;

import com.example.foodplanner.data.datasource.category.CategoryNetworkResponse;
import com.example.foodplanner.data.models.category.Category;
import com.example.foodplanner.data.models.category.CategoryResponse;
import com.example.foodplanner.data.models.category_filter.CategoryFilter;
import com.example.foodplanner.data.models.category_filter.CategoryFilterResponse;
import com.example.foodplanner.network.Network;
import com.example.foodplanner.network.Services;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFilterRemoteDataSource {
    private final Services categoryFilterService;

    public CategoryFilterRemoteDataSource() {
        categoryFilterService = Network.getInstance().services;
    }

    public void getAllCategoriesFilter(CategoryFilterNetworkResponse callback){
        categoryFilterService.getAllCategoriesList().enqueue(new Callback<CategoryFilterResponse>() {
            @Override
            public void onResponse(Call<CategoryFilterResponse> call, Response<CategoryFilterResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<CategoryFilter> categoriesFilter = response.body().getCategoriesFilter();
                    if (categoriesFilter != null) {
                        callback.onSuccess(categoriesFilter);
                    } else {
                        callback.onError("No categoriesFilter found");
                    }
                } else {
                    callback.onError("Response empty or failed");
                    Log.e("CategoryFilterRemoteDataSource", "Response failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<CategoryFilterResponse> call, Throwable t) {
                Log.e("CategoryFilterRemoteDataSource", "API call failed", t);
                callback.onError(t.getMessage());
            }
        });
    }
}
